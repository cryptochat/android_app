package app.cryptochat.com.cryptochat.Manager;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MessageModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Tools.Consumer;
import app.cryptochat.com.cryptochat.Tools.Logger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatManager {
    private CryptoManager cryptoManager = new CryptoManager();
    private RealmDataManager _realmDataManager = new RealmDataManager();
    WebSocket ws;

    ChatManager(){
//        try {
//            String wsURL = C
//            ws = new WebSocketFactory().createSocket("ws://localhost/endpoint");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void getChatList(String token, Consumer<ArrayList<ChatModel>,TransportStatus> response) {
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _getChatList(token, model.get_identifier(), response);
    }

    public void getHistoryUser(String token, int userID, int limit, int offset, Consumer<ArrayList<MessageModel>,TransportStatus> response){
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _getHistoryUser(token, userID, limit, offset, model.get_identifier(), response);
    }

    private void _getHistoryUser(String token, int userID, int limit, int offset, String identifier, Consumer<ArrayList<MessageModel>,TransportStatus> response){
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


        private void _getChatList(String token, String identifier, Consumer<ArrayList<ChatModel>,TransportStatus> response) {
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);

        // Шифруем данные
        cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);
        ArrayList<ChatModel> chatModelList = new ArrayList<>();
        requestInterface.fetchChatList(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {
                    cryptoManager.decrypt(cryptoModel.getCipherMessage());
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



}
