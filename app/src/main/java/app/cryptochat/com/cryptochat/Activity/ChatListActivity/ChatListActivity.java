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
import app.cryptochat.com.cryptochat.Manager.UserManager;
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
    UserManager userManager = new UserManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        AuthManager authManager = new AuthManager();
        MyUserModel myUser = authManager.getMyUser();

        userManager.searchUser(myUser.getToken(), "Mar", (s, t) -> {

        });

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

    private void createMockData(){
        for(int i = 0; i < 20; i++){
//            ChatListViewModel chatListViewModel = new ChatListViewModel();
//            userModels.add(chatListViewModel);
        }
    }
}
