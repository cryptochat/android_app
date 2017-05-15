package app.cryptochat.com.cryptochat.Manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketState;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MessageModel;
import app.cryptochat.com.cryptochat.Models.MessageResponse;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Tools.Constants;
import app.cryptochat.com.cryptochat.Tools.ConsumerReponse;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amudarisova on 14.05.17.
 */

enum MessageType{
    MessageModelNone,
    MessageModelCame
}

interface ChatManagerListener{
    public void changeState();
    public void cameMessage(MessageModel messageModel);
}

public class ChatManager {
    public static final ChatManager INSTANCE = new ChatManager();
    private CryptoManager _cryptoManager;
    private RealmDataManager _realmDataManager;
    private WebSocket ws;
    private String _token;
    private String _identifier;
    HashSet<ChatManagerListener> _chatManagerListeners;
    ChatManagerListener _chatManagerListener;
    ExecutorService executorServiceWS;

    ChatManager(){
        _cryptoManager = new CryptoManager();
        _realmDataManager = new RealmDataManager();
        _chatManagerListeners = new HashSet<>();
    }


    public void setAuth(String token, String identifier){
        _token = token;
        _identifier = identifier;
    }

    public void connectChat(){
        _connectWS(_token, _identifier);
    }

    public void sendMessageToUser(MessageModel message){
        ws.sendText(message.getText());
    }

    public void getChatList(String token, ConsumerReponse<ArrayList<ChatModel>,TransportStatus> response) {
        CryptoKeyPairModel model = _cryptoManager.getCryptoKeyPairModel();
        _getChatList(token, model.get_identifier(), response);
    }

    public void getHistoryUser(String token, int userID, int limit, int offset, ConsumerReponse<ArrayList<MessageModel>,TransportStatus> response){
        CryptoKeyPairModel model = _cryptoManager.getCryptoKeyPairModel();
        _getHistoryUser(token, userID, limit, offset, model.get_identifier(), response);
    }

    private void _getHistoryUser(String token, int userID, int limit, int offset, String identifier, ConsumerReponse<ArrayList<MessageModel>,TransportStatus> response){
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        // Шифруем данные
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("token", token);
            jsonObject.put("interlocutor_id", userID);
            jsonObject.put("limit", limit);
            jsonObject.put("offset", offset);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //TODO: Переделать шифрование

        requestInterface.fetchHistory(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {

                    JsonArray jsonArray = cryptoModel.getAsJsonObject("cipher_message").getAsJsonArray("messages");
                    ArrayList<MessageModel> messageModels = new ArrayList<MessageModel>();

                    //for(int i = 0; i < jsonArray.size(); i++){
                    for(JsonElement messageBuf : jsonArray){
                        JsonObject message = messageBuf.getAsJsonObject();
                        UserModel userModel = new Gson().fromJson(message.get("user").toString(), UserModel.class);
                        MessageModel messageModel = new Gson().fromJson(message.get("message").toString(), MessageModel.class);
                        messageModel.setUserModel(userModel);
                        messageModels.add(messageModel);
                    }

                    response.accept(messageModels, TransportStatus.TransportStatusSuccess);
                },(Throwable e) -> {
                    response.accept(null ,TransportStatus.TransportStatusDefault.getStatus(e));
                });

    }


        private void _getChatList(String token, String identifier, ConsumerReponse<ArrayList<ChatModel>,TransportStatus> response) {
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);

        // Шифруем данные
        _cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);
        ArrayList<ChatModel> chatModelList = new ArrayList<>();
        requestInterface.fetchChatList(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {
                    _cryptoManager.decrypt(cryptoModel.getCipherMessage());
                    List<LinkedTreeMap> chatList = (List<LinkedTreeMap>) cryptoModel.getCipherMessage().get("chats");

                    for(LinkedTreeMap chats : chatList) {
                        String lastMessage = (String) chats.get("last_message");
                        boolean isRead = (boolean) chats.get("is_read");
                        boolean fromMe = (boolean) chats.get("from_me");
                        double createdAt = (double) chats.get("created_at");

                        HashMap map = new HashMap();
                        map.put("last_message", lastMessage);
                        map.put("is_read", isRead);
                        map.put("from_me", fromMe);
                        map.put("created_at", createdAt);

                        String json = new Gson().toJson(map);

                        UserModel userModel = new Gson().fromJson(chats.get("interlocutor").toString(), UserModel.class);
                        ChatModel chatModel = new Gson().fromJson(json, ChatModel.class);
                        chatModel.setUserModel(userModel);
                        chatModelList.add(chatModel);
                    }
                    // Сюда передать массив ChatModel, внутри которых должна быть UserModel
                    response.accept(chatModelList, TransportStatus.TransportStatusSuccess);
                },(Throwable e) -> {
                    response.accept(chatModelList ,TransportStatus.TransportStatusDefault.getStatus(e));
                });
    }


    private void addListnerWS(WebSocket ws){
        ws.addListener(new WebSocketAdapter(){
            @Override
            public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception
            {
                sendSubscribe();
            }

            @Override
            public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception
            {
            }

            @Override
            public void onTextMessage(WebSocket websocket, String message) throws Exception {

                MessageType type = messageType(message);
                switch (type){
                    case MessageModelCame:
                        _comeMessage(message);
                        break;
                }

            }

            @Override
            public void onDisconnected(WebSocket websocket,
                                       WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame,
                                       boolean closedByServer) throws Exception
            {

            }

            @Override
            public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception
            {
                String s = new String();
            }

            @Override
            public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                super.onError(websocket, cause);

            }
        });
    }


    private void _connectWS(String token, String identifier){

        if(executorServiceWS != null){
            return;
        }

        executorServiceWS = Executors.newSingleThreadExecutor();
        executorServiceWS.submit(()->{
            try {
                String wsURL = Constants.URL_SW + "?identifier=" + identifier + "&" + "token="+token;
                WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(10000);
                ws = factory.createSocket(wsURL);
                addListnerWS(ws);
                ws.connect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WebSocketException e) {
                e.printStackTrace();
            }
        });
    }

    private void sendSubscribe(){
        ws.sendText("{\n" +
                "    \"command\": \"subscribe\",\n" +
                "    \"identifier\": \"{\\\"channel\\\":\\\"WsChatChannel\\\"}\"\n" +
                "}");
    }

    private void sendMessage(String message, int userID){

        JsonObject jsonData = new JsonObject();
        jsonData.addProperty("recipient_id", userID);
        jsonData.addProperty("message", message);
        jsonData.addProperty("action", "send_message");

        JsonObject jsonIdentifier = new JsonObject();
        jsonIdentifier.addProperty("channel", "WsChatChannel");

        JsonObject jsonCipherMessage = new JsonObject();
        jsonCipherMessage.add("data", jsonData);


        JsonObject json = new JsonObject();
        json.addProperty("command",  "message");
        json.add("identifier", jsonIdentifier);
        json.add("data", jsonCipherMessage);

        String textMessage = json.toString();
        ws.sendText(textMessage);
    }


    private MessageType messageType(String json){
        MessageType type = MessageType.MessageModelNone;

       MessageResponse messageResponse = new Gson().fromJson(json, MessageResponse.class);

        return type;
    }

    private MessageModel messageModel(String json){
        //JsonObject jsonObject = new JsonObject(json);
        //MessageModel messageModel = new Gson().fromJson(json, MessageModel.class);
        return null;
    }

    private void _comeMessage(String json){
        MessageModel message = null;
        _chatManagerListener.cameMessage(message);
    }




}
