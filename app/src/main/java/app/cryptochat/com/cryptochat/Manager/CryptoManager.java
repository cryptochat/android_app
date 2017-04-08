package app.cryptochat.com.cryptochat.Manager;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;

import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.CryptoProviderData;
import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.ProviderPreferences;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;

/**
 * Created by romankov on 01.04.17.
 *
 *  Пример использования
 *
 *  CryptoManager cryptoManager = new CryptoManager();
 *  cryptoManager.generateKeyPair();
 *  CryptoKeyPairModel model = cryptoManager.getCryptoKeyPairModel();
 *  Logger.l(new String(model.getPrivateKey()));
 *  Logger.l(new String(model.getPublicKey()));
 */


public class CryptoManager {

    private String PUBLIC_KEY = "PUBLIC_KEY";
    private String PRIVATE_KEY = "PRIVATE_KEY";
    private boolean _isExistKeyPair;
    private CryptoKeyPairModel _cryptoKeyPairModel;
    private CryptoProviderData _cryptoProviderData;

    public CryptoManager(){
        _cryptoProviderData = new ProviderPreferences();
        _cryptoKeyPairModel = _recoveryKeyPair();
        _isExistKeyPair = (_cryptoKeyPairModel == null);
    }

    /**
     *  Проверка на существование публичного и приватного ключа, которые сгенирировали ранее
     * @return boolean
     */
    public boolean is_isExistKeyPair(){
        return _isExistKeyPair;
    }



    /**
     * Генерация новой пары ключей, приватный и публичный
     */
    public void generateKeyPair(){
        _cryptoKeyPairModel =  _generateKeyPair();
        _saveKey(_cryptoKeyPairModel);
    }

    /**
     * Получение последней сгенерируемой пары ключей, если пары нет, то возврашается null
     * @return CryptoKeyPairModel
     */
    public CryptoKeyPairModel getCryptoKeyPairModel(){
        return _cryptoKeyPairModel;
    }


    private CryptoKeyPairModel _recoveryKeyPair() {
        String keyPublic = _cryptoProviderData.get(PUBLIC_KEY, null);
        String keyPrivate = _cryptoProviderData.get(PRIVATE_KEY, null);
        CryptoKeyPairModel cryptoKeyPairModel = null;
        if(keyPrivate != null && keyPublic != null){
            cryptoKeyPairModel = new CryptoKeyPairModel(keyPublic.getBytes(Charset.forName("UTF-8")),
                    keyPrivate.getBytes(Charset.forName("UTF-8")));
        }
        return cryptoKeyPairModel;
    }

    private CryptoKeyPairModel _generateKeyPair(){
        Curve25519KeyPair keyPair = Curve25519.getInstance(Curve25519.BEST).generateKeyPair();
        CryptoKeyPairModel cryptoKeyPairModel = new CryptoKeyPairModel(keyPair.getPublicKey(), keyPair.getPrivateKey());
        return cryptoKeyPairModel;
    }

    private void _saveKey(CryptoKeyPairModel keyPairModel){
        // Сохранение данных
        String keyPublic = new String(_cryptoKeyPairModel.getPublicKey(), StandardCharsets.UTF_8);
        String keyPrivate = new String(_cryptoKeyPairModel.getPrivateKey(), StandardCharsets.UTF_8);
        _cryptoProviderData.save(PUBLIC_KEY, keyPublic);
        _cryptoProviderData.save(PRIVATE_KEY, keyPrivate);
    }


}
