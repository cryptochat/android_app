package app.cryptochat.com.cryptochat.Models;

import java.util.HashMap;

/**
 * Created by romankov on 17.04.17.
 */

public class СryptoModel {
    private String cipher_message;
    private String identifier;

    public String getCipherMessage() {
        return cipher_message;
    }

    public void setCipherMessage(String cipher_message) {
        this.cipher_message = cipher_message;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
