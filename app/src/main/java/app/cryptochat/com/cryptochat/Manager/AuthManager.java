package app.cryptochat.com.cryptochat.Manager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusSuccess;

public class AuthManager {
    private CryptoManager cryptoManager = new CryptoManager();
    private TransitionManager transitionManager = new TransitionManager();

    public void authUser(String email, String password, Consumer<TransportStatus> hundlerResponse){

        boolean isKeyPair = cryptoManager.is_isExistKeyPair();
        if(!isKeyPair){
            cryptoManager.keyExchange(s->{
                CryptoKeyPairModel model =  cryptoManager.getCryptoKeyPairModel();
                _authUser(email, password, model.get_identifier(), hundlerResponse);
            });
        }else{
            CryptoKeyPairModel model =  cryptoManager.getCryptoKeyPairModel();
            _authUser(email, password, model.get_identifier(), hundlerResponse);
        }
    }

    private void _authUser(String email, String password, String identifier, Consumer<TransportStatus> hundlerResponse){
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("email", email);
        hashMap.put("password", password);

        // Шифруем данные
        cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);

        requestInterface.authUser(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {
                    cryptoManager.decrypt(cryptoModel.getCipherMessage());
//                    MyUserModel myUserModel = new Gson().fromJson(cryptoModel.getCipherMessage().toString(), MyUserModel.class);
//                    ChatListActivity chatListActivity = new ChatListActivity();
//                    chatListActivity.getChatList("s90pz1Pa_JoRcGWZs5SIPesUMLVJpv6BuyADreLVP_0", hundlerResponse);

//                    _saveUser(myUserModel);
                    hundlerResponse.accept(TransportStatusSuccess);
        }, (Throwable e) -> {
                    hundlerResponse.accept(TransportStatus.TransportStatusDefault.getStatus(e));
        });
    }

    private void _saveUser(MyUserModel myUserModel) {
        transitionManager.saveMyUser(myUserModel);
    }

    private void _deleteUser(String userUUID) {
        transitionManager.deleteMyUserById(userUUID);
    }
}
