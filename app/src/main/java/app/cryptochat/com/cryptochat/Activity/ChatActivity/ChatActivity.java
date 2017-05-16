package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.ChatManager;
import app.cryptochat.com.cryptochat.Manager.ChatManagerListener;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Manager.TransportStatus;
import app.cryptochat.com.cryptochat.Models.MessageModel;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.EndlessScrollListener;
import app.cryptochat.com.cryptochat.Tools.Tools;

public class ChatActivity extends AppCompatActivity {
    ListView listViewMessages;
    ArrayList<ChatViewModel> chatList = new ArrayList<>();
    ArrayList<MessageModel> messageList = new ArrayList<>();
    ChatAdapter chatAdapter;
    AuthManager authManager = new AuthManager();
    ChatManager chatManager = ChatManager.INSTANCE;
    ChatViewModel chatViewModel;

    private int offset = 0;
    private int limit = 15;
    private int userId;
    private String userName;
    private String avatarUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        userName = intent.getStringExtra("userName");

        wsConnect();

        MyUserModel myUser = authManager.getMyUser();
        TextView textViewInformation = (TextView) findViewById(R.id.textViewInfomation);
        chatManager.getHistoryUser(myUser.getToken(), userId, limit, offset, (s, t) -> {
            if(t == TransportStatus.TransportStatusSuccess){
                messageList = s;
                for(MessageModel messageModel : s){
                    chatViewModel = new ChatViewModel(messageModel);
                    chatList.add(chatViewModel);
                }
                chatAdapter.notifyDataSetChanged();
                if(s.size() == 0){
                    textViewInformation.setText("У Вас пока нет сообщений");
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
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(userName);
//        ImageView toolbarImage = (ImageView) findViewById(R.id.toolbarImage);
//        if(avatarUrl == null) {
//            // Image User
//            Bitmap userImage = BitmapFactory.decodeResource(getResources(), R.mipmap.image_user_default);
//            userImage = Tools.getRoundedCornerBitmap(userImage, userImage.getWidth() / 2);
//            toolbarImage.setImageBitmap(userImage);
//        }else{
//            Picasso.with(this)
//                    .load(avatarUrl)
//                    .placeholder(R.mipmap.image_user_default)
//                    .into(toolbarImage);
//        }

        EditText inputMsg = (EditText) findViewById(R.id.inputMsg);
        // Listener EditText
        inputMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button sendButton = (Button) findViewById(R.id.btnSend);
        // Отправить сообщение
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(inputMsg.getText().toString(), userId);
                inputMsg.setText("");
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(v -> finish());

        listViewMessages = (ListView) findViewById(R.id.listView);
        listViewMessages.setRotation(180);
        chatAdapter = new ChatAdapter(this, chatList);
        listViewMessages.setAdapter(chatAdapter);


        // Подгрузка
        listViewMessages.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                offset = totalItemsCount;
                chatManager.getHistoryUser(myUser.getToken(), userId, limit, offset, (s, t) -> {
                    if(t == TransportStatus.TransportStatusSuccess){
                        messageList = s;
                        for(MessageModel messageModel : s) {
                            ChatViewModel chatViewModel = new ChatViewModel(messageModel);
                            chatList.add(chatViewModel);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

    private void wsConnect() {
        CryptoManager cryptoManager = new CryptoManager();
        AuthManager authManager = new AuthManager();
        MyUserModel myUserModel = authManager.getMyUser();
        String token = authManager.getMyUser().getToken();

        chatManager.setAuth(token, cryptoManager.getCryptoKeyPairModel().get_identifier());
        chatManager.connectChat();
        chatManager.setListner(new ChatManagerListener() {
            @Override
            public void changeState() {

            }

            @Override
            public void cameMessage(MessageModel messageModel) {
                chatViewModel = new ChatViewModel(messageModel);
                chatList.add(chatList.size(), chatViewModel);

                chatAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addMessage(String message, boolean fromMe) {
        chatAdapter.add(new ChatViewModel(message, fromMe, null));
        chatAdapter.notifyDataSetChanged();
    }

    private void sendMessage(String message, int userId) {
        if (message.length() == 0) return;

        chatManager.sendMessage(message, userId);
        addMessage(message, true);

    }
}
