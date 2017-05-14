package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by amudarisova on 09.05.17.
 */

public class UserModel {

    private int id;
    @SerializedName("username")
    private String userName;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("is_online")
    private String isOnline;

    public UserModel(UserModelRealm userModelRealm) {
        this.id = userModelRealm.getId();
        this.userName = userModelRealm.getUserName();
        this.firstName = userModelRealm.getFirstName();
        this.lastName = userModelRealm.getLastName();
        this.isOnline = userModelRealm.getLastName();
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }
}

