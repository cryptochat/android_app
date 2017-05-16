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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import app.cryptochat.com.cryptochat.Activity.ChatActivity.ChatActivity;
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
    ArrayList<ChatModel> chatModels = new ArrayList<>();
    ChatListAdapter chatListAdapter;
    SearchView searchView;
    ChatManager chatManager = ChatManager.INSTANCE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        AuthManager authManager = new AuthManager();
        MyUserModel myUser = authManager.getMyUser();
        TextView textViewInformation = (TextView) findViewById(R.id.textViewInfomation);
        chatManager.getChatList(myUser.getToken(), (s, t) -> {
            if(t == TransportStatus.TransportStatusSuccess){
                chatModels = s;
                for(ChatModel chatModel : s){
                    ChatListViewModel chatListViewModel = new ChatListViewModel(chatModel);
                    userModels.add(chatListViewModel);
                }
                chatListAdapter.notifyDataSetChanged();
                if(s.size() == 0){
                    textViewInformation.setText("У Вас пока нет чатов");
                }else {
                    textViewInformation.setText("");
                }
            }else if (t == TransportStatus.TransportStatusNotInternet){
                Toast.makeText(this, "Проверьте интернет соединение", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Возникла ошибка", Toast.LENGTH_SHORT).show();
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createMockData();
        listView = (ListView) findViewById(R.id.listView);
        chatListAdapter = new ChatListAdapter(this, userModels);
        listView.setAdapter(chatListAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> startChatForUser(
                chatModels.get(position).getUserModel().getId(),
                chatModels.get(position).getUserModel().getUserName(),
                chatModels.get(position).getUserModel().getAvatar().getUrl()));
    }


    private void startChatForUser(int userId, String userName, String avatarUrl){
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("userName", userName);
        intent.putExtra("avatarUrl", avatarUrl);
        startActivity(intent);
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
