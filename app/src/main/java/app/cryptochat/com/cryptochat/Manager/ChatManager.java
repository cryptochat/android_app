package app.cryptochat.com.cryptochat.Manager;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Tools.Consumer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatManager {
    private CryptoManager cryptoManager = new CryptoManager();
    private RealmDataManager _realmDataManager = new RealmDataManager();

    public void getChatList(String token, Consumer<ArrayList<ChatModel>,TransportStatus> response) {
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _getChatList(token, model.get_identifier(), response);
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
                        Double createdAt = (Double) chats.get("created_at");

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
