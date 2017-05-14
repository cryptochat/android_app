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
import app.cryptochat.com.cryptochat.Manager.ChatManager;
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

        getChatList(myUserModel.getToken());

//        searchUser(myUserModel.getToken(), "Mar");

    }

    private void getChatList(String token){
        ChatManager chatManager = new ChatManager();
        chatManager.getChatList(token, (l, t) -> {

        });
    }

    public void searchUser(String token, String searchText) {
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _searchUser(token, model.get_identifier(), searchText, (ArrayList list, TransportStatus status) -> {

        });
    }

    private void _searchUser(String token, String identifier, String searchText, Consumer<ArrayList, TransportStatus> response) {
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);
        hashMap.put("query", searchText);

        // Шифруем данные
        cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);

        requestInterface.searchUser(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {
                    cryptoManager.decrypt(cryptoModel.getCipherMessage());
                    ArrayList<UserModel> userList = (ArrayList<UserModel>) cryptoModel.getCipherMessage().get("users");

                    response.accept(userList, TransportStatus.TransportStatusSuccess);
                },(Throwable e) -> {
                    response.accept(null, TransportStatus.TransportStatusDefault.getStatus(e));
                });
    }









}
