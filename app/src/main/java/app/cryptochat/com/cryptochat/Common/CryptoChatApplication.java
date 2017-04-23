package app.cryptochat.com.cryptochat.Common;

import android.app.Application;
import android.support.v4.BuildConfig;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import io.fabric.sdk.android.Fabric;

/**
 * Created by romankov on 01.04.17.
 */

public class CryptoChatApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();

        // Инициализируем Fabric с выключенным crashlytics.
        Fabric.with(this, crashlyticsKit);
        CryptoPreferences.init(this);
    }
}
