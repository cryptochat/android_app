package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by romankov on 14.05.17.
 */

public class MessageModel {

    private UserModel userModel;

    @SerializedName("text")
    private String text;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
