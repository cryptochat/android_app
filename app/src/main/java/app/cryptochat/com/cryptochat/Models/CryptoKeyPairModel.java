package app.cryptochat.com.cryptochat.Models;

/**
 * Created by romankov on 01.04.17.
 */

public class CryptoKeyPairModel {

    private byte[] _publicKey;
    private byte[] _privateKey;

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
}
