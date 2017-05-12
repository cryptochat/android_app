package app.cryptochat.com.cryptochat.Manager;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusSuccess;

public class AuthManager {
    private CryptoManager cryptoManager = new CryptoManager();
    private RealmDataManager _realmDataManager = new RealmDataManager();

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

    public MyUserModel getMyUser(){
        return _realmDataManager.getMyUserModel();
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
                    MyUserModel myUserModel = new Gson().fromJson(cryptoModel.getCipherMessage().get("user").toString(), MyUserModel.class);
                    _saveUser(myUserModel);
                    hundlerResponse.accept(TransportStatusSuccess);
        }, (Throwable e) -> {
                    hundlerResponse.accept(TransportStatus.TransportStatusDefault.getStatus(e));
        });
    }

    private void _saveUser(MyUserModel myUserModel) {
        _realmDataManager.createMyUser(myUserModel.getUUID(),
                myUserModel.getEmail(),
                myUserModel.getUserName(),
                myUserModel.getFirstName(),
                myUserModel.getLastName(),
                myUserModel.getToken());
    }

    private void _deleteUser(String userUUID) {

    }

}
