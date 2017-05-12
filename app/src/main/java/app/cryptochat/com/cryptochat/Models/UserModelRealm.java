package app.cryptochat.com.cryptochat.Models;

import io.realm.RealmObject;

/**
 * Created by amudarisova on 25.04.17.
 */

public class UserModelRealm extends RealmObject {

    private String uuid;
    private String email;
    private String username;
    private String first_name;
    private String last_name;
    private String token;

    public UserModelRealm() {
    }

    UserModelRealm(String uuid, String email, String username, String first_name, String last_name, String token) {
        this.uuid = uuid;
        this.email = email;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.token = token;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
