package app.cryptochat.com.cryptochat.Manager;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Tools.Consumer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by amudarisova on 14.05.17.
 */

public class UserManager {
    private CryptoManager cryptoManager = new CryptoManager();

    public void searchUser(String token, String searchText, Consumer<ArrayList, TransportStatus> response) {
        CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
        _searchUser(token, model.get_identifier(), searchText, response);
    }

    private void _searchUser(String token, String identifier, String searchText, Consumer<ArrayList, TransportStatus> response) {
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("token", token);
        hashMap.put("query", searchText);

        // Шифруем данные
        cryptoManager.encrypt(hashMap);

        JSONObject jsonObject = new JSONObject(hashMap);
        final ArrayList<UserModel> userList = new ArrayList<>();
        requestInterface.searchUser(identifier, jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cryptoModel -> {
                    cryptoManager.decrypt(cryptoModel.getCipherMessage());

                    List<LinkedTreeMap> users = (List<LinkedTreeMap>) cryptoModel.getCipherMessage().get("users");
                    UserModel userModel = new Gson().fromJson(users.toString(), UserModel.class);
                    userList.add(userModel);

                    response.accept(userList, TransportStatus.TransportStatusSuccess);
                },(Throwable e) -> {
                    response.accept(userList, TransportStatus.TransportStatusDefault.getStatus(e));
                });
    }

}
