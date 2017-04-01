package app.cryptochat.com.cryptochat.Common.ProviderCryptoData;

import app.cryptochat.com.cryptochat.Common.CryptoPreferences;

/**
 * Created by romankov on 01.04.17.
 */

public class ProviderPreferences implements CryptoProviderData {

    @Override
    public void save(String key, String value) {
        CryptoPreferences.save(key, value);
    }

    @Override
    public String get(String key, String value) {
        return CryptoPreferences.get(key, value);
    }

    @Override
    public void remove(String key, String value) {
        CryptoPreferences.remove(key);
    }
}
