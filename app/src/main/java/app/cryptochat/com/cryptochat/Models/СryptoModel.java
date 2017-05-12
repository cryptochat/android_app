package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by romankov on 17.04.17.
 */

public class СryptoModel {
    @SerializedName("cipher_message")
    private HashMap cipherMessage;
    private String identifier;

    public HashMap getCipherMessage() {
        return cipherMessage;
    }

    public void setCipherMessage(HashMap cipher_message) {
        this.cipherMessage = cipher_message;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
