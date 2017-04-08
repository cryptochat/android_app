package app.cryptochat.com.cryptochat.Common;

import android.app.Application;

/**
 * Created by romankov on 01.04.17.
 */

public class CryptoChatApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CryptoPreferences.init(this);
    }
}
