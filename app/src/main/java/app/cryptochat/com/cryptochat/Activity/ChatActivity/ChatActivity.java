package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.Activity.ChatListActivity.ChatListAdapter;
import app.cryptochat.com.cryptochat.Activity.ChatListActivity.ChatListViewModel;
import app.cryptochat.com.cryptochat.R;

public class ChatActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<ChatViewModel> chatModels = new ArrayList<>();
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button sendButton = (Button) findViewById(R.id.)

        listView = (ListView) findViewById(R.id.listView);
        listView.setRotation(180);
        chatAdapter = new ChatAdapter(this, chatModels);
        listView.setAdapter(chatAdapter);

        createMockData();
    }

    private void createMockData(){
        for(int i = 0; i < 20; i++){
            ChatViewModel chatViewModel = new ChatViewModel();
            chatModels.add(chatViewModel);
        }
    }
}
