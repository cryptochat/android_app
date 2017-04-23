package app.cryptochat.com.cryptochat.Manager;

import android.util.Base64;

import org.whispersystems.curve25519.Curve25519;
import org.whispersystems.curve25519.Curve25519KeyPair;

import java.util.HashMap;

import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.CryptoProviderData;
import app.cryptochat.com.cryptochat.Common.ProviderCryptoData.ProviderPreferences;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.Tools.Constants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.util.Base64.URL_SAFE;
import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusError;
import static app.cryptochat.com.cryptochat.Manager.TransportStatus.TransportStatusSuccess;

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
    private String SHARED_KEY = "SHARED_KEY";
    private String IDENTIFER = "IDENTIFER";//identifier
    private boolean _isExistKeyPair;
    private CryptoKeyPairModel _cryptoKeyPairModel;
    private CryptoProviderData _cryptoProviderData;

    public CryptoManager(){
        _cryptoProviderData = new ProviderPreferences();
        _cryptoKeyPairModel = _recoveryKeyPair();
        _isExistKeyPair = (_cryptoKeyPairModel != null);
    }



    public void keyExchange(Consumer<TransportStatus> status) {
        // Формирование публичного ключа
        generateKeyPair();
        CryptoKeyPairModel cryptoKeyPairModel = getCryptoKeyPairModel();
        String publicKeyBase64 = Base64.encodeToString(getCryptoKeyPairModel().getPublicKey(), Base64.NO_WRAP | Base64.URL_SAFE);
        RequestInterface requestInterface = APIManager.INSTANCE.getRequestInterface();
        HashMap<String, String> responsePublicKey = new HashMap<>();

        requestInterface.fetchPublicKey()
                .subscribeOn(Schedulers.io())
                .doOnNext(s -> { // Сохранение данных прошлого запроса
                    responsePublicKey.put("identifier",s.get("identifier"));
                    responsePublicKey.put("public_key",s.get("public_key"));
                })
                .concatMap(s -> requestInterface.sendPublicKey(s.get("identifier"), publicKeyBase64))
                .doOnNext(s -> {// Формирования shared ключа
                    byte[] publicKeyServer = Base64.decode(responsePublicKey.get("public_key"), URL_SAFE);
                    byte[] sharedKey =  Curve25519.getInstance(Curve25519.BEST).calculateAgreement(publicKeyServer, cryptoKeyPairModel.getPrivateKey());
                    cryptoKeyPairModel.setSharedKey(sharedKey);
                    cryptoKeyPairModel.set_identifier(responsePublicKey.get("identifier"));
                    _saveKey(cryptoKeyPairModel);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    status.accept(TransportStatusSuccess);
                }, (Throwable e) -> {
                    status.accept(TransportStatus.TransportStatusDefault.getStatus(e));
                });
    }

    /**
     *  Проверка на существование публичного и приватного ключа, которые сгенирировали ранее
     * @return boolean
     */
    public boolean is_isExistKeyPair(){
        return _isExistKeyPair;
    }


    /**
     * Получение последней сгенерируемой пары ключей, если пары нет, то возврашается null
     * @return CryptoKeyPairModel
     */
    public CryptoKeyPairModel getCryptoKeyPairModel(){
        return _cryptoKeyPairModel;
    }

    public void encrypt(HashMap<String, String> hashMap){
        if(Constants.isEncrypt){
            return;
        }
        //TODO: шифрование
    }

    public void decrypt(HashMap<String, String> hashMap){
        if(Constants.isEncrypt){
            return;
        }
        //TODO: шифрование
    }


    private void generateKeyPair(){
        _cryptoKeyPairModel =  _generateKeyPair();
        _saveKey(_cryptoKeyPairModel);
    }

    private CryptoKeyPairModel _recoveryKeyPair() {
        String keyPublic = _cryptoProviderData.get(PUBLIC_KEY, null);
        String keyPrivate = _cryptoProviderData.get(PRIVATE_KEY, null);
        String keyShared = _cryptoProviderData.get(SHARED_KEY, null);
        String identifer = _cryptoProviderData.get(IDENTIFER, null);

        CryptoKeyPairModel cryptoKeyPairModel = null;
        if(keyPrivate != null && keyPublic != null){
            cryptoKeyPairModel = new CryptoKeyPairModel(Base64.decode(keyPublic, URL_SAFE),
                    Base64.decode(keyPrivate, URL_SAFE));
            cryptoKeyPairModel.set_identifier(identifer);
            if(keyShared != null) {
                cryptoKeyPairModel.setSharedKey(Base64.decode(keyShared, URL_SAFE));
            }
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
        String keyPublic = Base64.encodeToString(getCryptoKeyPairModel().getPublicKey(), Base64.NO_WRAP | Base64.URL_SAFE);
        String keyPrivate = Base64.encodeToString(getCryptoKeyPairModel().getPrivateKey(), Base64.NO_WRAP | Base64.URL_SAFE);
        _cryptoProviderData.save(PUBLIC_KEY, keyPublic);
        _cryptoProviderData.save(PRIVATE_KEY, keyPrivate);

        if(keyPairModel.get_identifier() != null) {
            _cryptoProviderData.save(IDENTIFER, keyPairModel.get_identifier());
        }


        if(keyPairModel.getSharedKey() != null){
            String keyShared = Base64.encodeToString(getCryptoKeyPairModel().getSharedKey(), Base64.NO_WRAP | Base64.URL_SAFE);
            _cryptoProviderData.save(SHARED_KEY, keyShared);
        }

    }




}
