package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

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
    private boolean isOnline;
//    private Avatar avatar;

    public UserModel(UserModelRealm userModelRealm) {
        this.id = userModelRealm.getId();
        this.userName = userModelRealm.getUserName();
        this.firstName = userModelRealm.getFirstName();
        this.lastName = userModelRealm.getLastName();
        this.isOnline = userModelRealm.getIsOnline();
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

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

//    public Avatar getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(Avatar avatar) {
//        this.avatar = avatar;
//    }

    public class Avatar {
        private String url;

        public Avatar(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}

