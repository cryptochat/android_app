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
import app.cryptochat.com.cryptochat.Manager.UserManager;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.R;

public class ChatListActivity extends AppCompatActivity {
    private CryptoManager cryptoManager = new CryptoManager();
    private RealmDataManager _realmDataManager = new RealmDataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        AuthManager authManager = new AuthManager();
        MyUserModel myUserModel = authManager.getMyUser();

//        getChatList(myUserModel.getToken());
        getSearchUser(myUserModel.getToken(), "Mar");

    }

    private void getChatList(String token){
        ChatManager chatManager = new ChatManager();
        chatManager.getChatList(token, (l, t) -> {

        });
    }

    private void getSearchUser(String token, String queryText){
        UserManager userManager = new UserManager();
        userManager.searchUser(token, queryText, (l, t) -> {

        });
    }














}
