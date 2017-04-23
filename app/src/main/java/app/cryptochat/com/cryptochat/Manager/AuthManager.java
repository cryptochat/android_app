package app.cryptochat.com.cryptochat.Manager;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;


import java.net.ConnectException;
import java.util.HashMap;

import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Models.СryptoModel;
import app.cryptochat.com.cryptochat.Tools.Logger;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusError;
import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusNotInternet;
import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusSuccess;

public class AuthManager {
    private CryptoManager cryptoManager = new CryptoManager();

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
                    cryptoManager.decrypt(cryptoModel.getData());
                    UserModel userModel = new Gson().fromJson(cryptoModel.getData().toString(), UserModel.class);
                    saveUser(userModel);
                    hundlerResponse.accept(TransportStatusSuccess);
        }, (Throwable e) -> {
                    hundlerResponse.accept(TransportStatus.TransportStatusDefault.getStatus(e));
        });
    }

        private void saveUser(UserModel userModel){
        //TODO:
    }
}
