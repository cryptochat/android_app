package app.cryptochat.com.cryptochat.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.json.JSONException;
import org.json.JSONObject;

import app.cryptochat.com.cryptochat.Activity.ChatListActivity.ChatListActivity;
import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.ChatManager;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Manager.TransportStatus;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

public class LoginActivity extends AppCompatActivity {
    private EditText _email, _password;
    private Button _btnLogin;

    private Flowable<CharSequence> _emailChangeObservable;
    private Flowable<CharSequence> _passwordChangeObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        String json = "{\n" +
//                "    \"identifier\": {\n" +
//                "        \"channel\": \"WsChatChannel\"\n" +
//                "    },\n" +
//                "    \"message\": {\n" +
//                "        \"header\": {\n" +
//                "            \"method_name\": \"incoming_message\"\n" +
//                "        },\n" +
//                "        \"body\": {\n" +
//                "            \"created_at\": 1469984626,\n" +
//                "            \"text\": \"Your message to recipient\",\n" +
//                "            \"sender\": {\n" +
//                "                \"id\": 7,\n" +
//                "                \"first_name\": \"exampleFirstName\",\n" +
//                "                \"last_name\": \"exampleLastName\",\n" +
//                "                \"username\": \"exampleUsername\",\n" +
//                "                \"avatar\": {\n" +
//                "                    \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/1547cfa9919ecd59ca143be176037082.jpeg\",\n" +
//                "                    \"small\": {\n" +
//                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/small_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
//                "                    },\n" +
//                "                    \"medium\": {\n" +
//                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/medium_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
//                "                    },\n" +
//                "                    \"big\": {\n" +
//                "                        \"url\": \"http://wishbyte.org/uploads/user/6358d754-eb30-49fd-8638-c49dfc579573/avatar/big_1547cfa9919ecd59ca143be176037082.jpeg\"\n" +
//                "                    }\n" +
//                "                }\n" +
//                "            }\n" +
//                "        }\n" +
//                "    }\n" +
//                "}";
//
//        try {
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

                CryptoManager cryptoManager = new CryptoManager();
        AuthManager authManager = new AuthManager();
        MyUserModel myUserModel = authManager.getMyUser();
        String token = authManager.getMyUser().getToken();


        ChatManager chatManager = ChatManager.INSTANCE;
        chatManager.setAuth(token, cryptoManager.getCryptoKeyPairModel().get_identifier());
        chatManager.connectChat();

       // signal.await();

        // Переходим на следующий экран, если авторизованы
//        AuthManager authManager = new AuthManager();
//        if(authManager.getMyUser() != null){
//            startChatListActivity();
//        }

        _email      = (EditText) findViewById(R.id.edit_email);
        _password   = (EditText) findViewById(R.id.edit_password);
        _btnLogin   = (Button) findViewById(R.id.button_log_in);
        _btnLogin.setEnabled(false);
        _btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authUser(_email.getText().toString(), _password.getText().toString());
            }
        });

        _emailChangeObservable =
                RxTextView.textChanges(_email).skip(1).toFlowable(BackpressureStrategy.LATEST);

        _passwordChangeObservable =
                RxTextView.textChanges(_password).skip(1).toFlowable(BackpressureStrategy.LATEST);

        _combineLatestEvents();



    }

    private void startChatListActivity(){
        Intent intent = new Intent(this, ChatListActivity.class);
        startActivity(intent);
        finish();
    }


    private void authUser(String login, String password){
        AuthManager authManager = new AuthManager();
        authManager.authUser(login, password, (s)->{
            if(s == TransportStatus.TransportStatusSuccess){
                startChatListActivity();
            }
        });
    }

    private void _combineLatestEvents() {

        Flowable.combineLatest(
                _emailChangeObservable,
                _passwordChangeObservable,
                (newEmail, newPassword) -> {
                    boolean emailValid = _validateEmail(newEmail);
                    if (!emailValid) {
                        _email.setError(getText(R.string.invalid_email));
                    }

                    boolean passValid = _validatePassword(newPassword);
                    if (!passValid) {
                        _password.setError(getText(R.string.invalid_password));
                    }

                    return emailValid && passValid;
                })
                .subscribe(_btnLogin::setEnabled);
    }

    private boolean _validateEmail(CharSequence email) {
        return  !isEmpty(email) &&
                EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean _validatePassword(CharSequence password) {
        return  !isEmpty(password) &&
                password.length() > 6;
    }

}
