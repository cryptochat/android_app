package app.cryptochat.com.cryptochat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.cryptochat.com.cryptochat.Manager.APIManager;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Manager.RequestInterface;
import app.cryptochat.com.cryptochat.Manager.TransitionManager;
import app.cryptochat.com.cryptochat.Manager.TransportStatus;
import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.CipherMessageResponse;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.R;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusSuccess;

public class ChatListActivity extends AppCompatActivity {
    private CryptoManager cryptoManager = new CryptoManager();
    private UserModel userModel;
    private TransitionManager transitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        transitionManager = new TransitionManager();

        getChatList("tm013dClMglZbsgSoXGU5QbCTVo0xjFKVEouzxX6_RY");
    }

    public void getChatList(String token) {
        Consumer<TransportStatus> hundlerResponse = null;
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _getChatList(token, "76c93ee0-20e3-4340-ab7b-ef1c2371dcda", hundlerResponse);
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
                });
    }









}
