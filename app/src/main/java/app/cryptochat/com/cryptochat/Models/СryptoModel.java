package app.cryptochat.com.cryptochat.Models;

import java.util.HashMap;

/**
 * Created by romankov on 17.04.17.
 */

public class СryptoModel {
    private HashMap<String, String> data;
    private String identifier;

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
