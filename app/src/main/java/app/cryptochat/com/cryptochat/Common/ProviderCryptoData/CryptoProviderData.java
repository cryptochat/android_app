package app.cryptochat.com.cryptochat.Common.ProviderCryptoData;

/**
 * Created by romankov on 01.04.17.
 */

public interface CryptoProviderData {
    public void save(String key, String value);
    public String get(String key, String def);
    public void remove(String key, String value);
}
