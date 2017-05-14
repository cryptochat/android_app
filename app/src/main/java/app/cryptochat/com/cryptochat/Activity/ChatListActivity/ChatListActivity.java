package app.cryptochat.com.cryptochat.Activity.ChatListActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.cryptochat.com.cryptochat.Activity.SearchUserActivity.SearchUserActivity;
import app.cryptochat.com.cryptochat.Manager.APIManager;
import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.ChatManager;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Manager.RequestInterface;
import app.cryptochat.com.cryptochat.Manager.TransportStatus;
import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.Logger;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;




public class ChatListActivity extends AppCompatActivity  {
    private CryptoManager cryptoManager = new CryptoManager();
    ListView listView;
    ArrayList<ChatListViewModel> userModels = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    SearchView searchView;
    ChatManager chatManager = new ChatManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);


        AuthManager authManager = new AuthManager();
        MyUserModel myUser = authManager.getMyUser();
        chatManager.getChatList(myUser.getToken(), (s, t) -> {
            if(t == TransportStatus.TransportStatusSuccess){
                for(ChatModel chatModel : s){
                    ChatListViewModel chatListViewModel = new ChatListViewModel(chatModel);
                    userModels.add(chatListViewModel);
                }
                chatListAdapter.notifyDataSetChanged();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                return false;
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        createMockData();

        listView = (ListView) findViewById(R.id.listView);
        chatListAdapter = new ChatListAdapter(this, userModels);
        listView.setAdapter(chatListAdapter);

        getChatList("tm013dClMglZbsgSoXGU5QbCTVo0xjFKVEouzxX6_RY");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchActivity();
            }
        });
        //searchView.setSuggestionsAdapter();

        return true;
    }

    private void startSearchActivity(){
        searchView.setIconified(true);
        Intent intent = new Intent(this, SearchUserActivity.class);
        intent.putExtra("q", "");
        startActivity(intent);
        overridePendingTransition(R.anim.transition_fade_in,R.anim.transition_fade_out);
    }


    public void getChatList(String token) {
        Consumer<TransportStatus> hundlerResponse = null;
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        AuthManager authManager = new AuthManager();
        MyUserModel userModel = authManager.getMyUser();
        _getChatList(userModel.getToken(), "76c93ee0-20e3-4340-ab7b-ef1c2371dcda", hundlerResponse);
    }


    // 1. получить данные
    // 2. сохранить в realm
    // 3. всегда выдавать данные из realm



    private void _getChatList(String token, String identifier, Consumer<TransportStatus> status) {
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


//                    CipherMessageResponse.CipherMessageModel cipherMessageModel = cipherMessageResponse.getCipherMessage();
//                    ArrayList<CipherMessageResponse.ChatResponse> chatResponses = cipherMessageModel.getChats();
//
//                    ArrayList<UserModel> arrayUsers = new ArrayList<>();
//
//                    for (int i = 0; i < chatResponses.size(); i++) {
//                        CipherMessageResponse.Interlocutor interlocutor = chatResponses.get(i).getInterlocutor();
//                        userModel = new UserModel(interlocutor.getId(),
//                                                  interlocutor.getUsername(),
//                                                  interlocutor.getFirstName(),
//                                                  interlocutor.getLastName());
//                        transitionManager.saveUser(userModel);
//                    }
//                    arrayUsers.add(userModel);

//
//                    status.accept(TransportStatusSuccess);
//                }, (Throwable e) -> {
//                    status.accept(TransportStatus.TransportStatusDefault.getStatus(e));
                },(Throwable e) -> {
                    Logger.l(e.toString());
                });
    }



    private void createMockData(){
        for(int i = 0; i < 20; i++){
//            ChatListViewModel chatListViewModel = new ChatListViewModel();
//            userModels.add(chatListViewModel);
        }
    }
}
