package app.cryptochat.com.cryptochat.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.CryptoProviderData;
import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.ProviderPreferences;

/**
 * Created by amudarisova on 15.05.17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private CryptoProviderData _cryptoProviderData;
    private String PHONE_TOKEN = "PHONE_TOKEN";

    @Override
    public void onTokenRefresh() {
        String phoneToken = FirebaseInstanceId.getInstance().getToken();
        _cryptoProviderData = new ProviderPreferences();
        _cryptoProviderData.save(PHONE_TOKEN, phoneToken);
    }
}
