package app.cryptochat.com.cryptochat.Models;

/**
 * Created by romankov on 01.04.17.
 */

public class CryptoKeyPairModel {

    private byte[] _publicKey;
    private byte[] _privateKey;
    private byte[] _sharedKey;
    private String _identifier;



    public CryptoKeyPairModel(byte[] publicKey, byte[] privateKey){
        _privateKey = privateKey;
        _publicKey = publicKey;
    }

    public byte[] getPrivateKey(){
        return _privateKey;
    }

    public byte[] getPublicKey(){
        return _publicKey;
    }

    public byte[] getSharedKey() {
        return _sharedKey;
    }

    public void setSharedKey(byte[] _sharedKey) {
        this._sharedKey = _sharedKey;
    }

    public String get_identifier() {
        return _identifier;
    }

    public void set_identifier(String _identifier) {
        this._identifier = _identifier;
    }
}
