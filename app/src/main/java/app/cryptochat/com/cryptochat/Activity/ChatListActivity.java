package app.cryptochat.com.cryptochat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.cryptochat.com.cryptochat.Manager.APIManager;
import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Manager.RealmDataManager;
import app.cryptochat.com.cryptochat.Manager.RequestInterface;
import app.cryptochat.com.cryptochat.Manager.TransportStatus;
import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.Consumer;
import app.cryptochat.com.cryptochat.Tools.Logger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatListActivity extends AppCompatActivity {
    private CryptoManager cryptoManager = new CryptoManager();
    private RealmDataManager _realmDataManager = new RealmDataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        AuthManager authManager = new AuthManager();
        MyUserModel myUserModel = authManager.getMyUser();

//        ArrayList<ChatModel> chatList = _realmDataManager.getChatList();

        getChatList(myUserModel.getToken());

    }

    public void getChatList(String token) {
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _getChatList(token, model.get_identifier(), (ArrayList list, TransportStatus status) -> {
            Logger.l("");
        });
    }


    private void _getChatList(String token, String identifier, Consumer<ArrayList,TransportStatus> response) {
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);

        // Шифруем данные
        cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);

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

                        HashMap map = new HashMap();
                        map.put("last_message", lastMessage);
                        map.put("is_read", isRead);
                        map.put("from_me", fromMe);

                        String json = new Gson().toJson(map);

                        UserModel userModel = new Gson().fromJson(chats.get("interlocutor").toString(), UserModel.class);
                       // _saveUser(userModel);

                        ChatModel chatModel = new Gson().fromJson(json, ChatModel.class);
                       // _saveChat(chatModel);
                    }
                    // Сюда передать массив ChatModel, внутри которых должна быть UserModel
                    response.accept(null, TransportStatus.TransportStatusSuccess);


                },(Throwable e) -> {
                    response.accept(null ,TransportStatus.TransportStatusDefault.getStatus(e));
                });
    }

    private void _saveChat(ChatModel chatModel) {
        _realmDataManager.createChat(
                chatModel.getLastMessage(),
                chatModel.isRead(),
                chatModel.isFromMe());
    }

    private void _saveUser(UserModel userModel) {
        _realmDataManager.createUser(
                userModel.getId(),
                userModel.getUserName(),
                userModel.getFirstName(),
                userModel.getLastName());
    }








}
