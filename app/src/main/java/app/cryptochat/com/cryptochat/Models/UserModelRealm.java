package app.cryptochat.com.cryptochat.Models;

import io.realm.RealmObject;

/**
 * Created by amudarisova on 10.05.17.
 */

public class UserModelRealm extends RealmObject {

    private int id;
    private String username;
    private String first_name;
    private String last_name;
    private boolean is_online;

    public UserModelRealm() {
    }

    UserModelRealm(int id, String username, String first_name, String last_name, boolean is_online) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.is_online = is_online;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public boolean getIsOnline() {
        return is_online;
    }

    public void setIsOnline(boolean is_online) {
        this.is_online = is_online;
    }
}
