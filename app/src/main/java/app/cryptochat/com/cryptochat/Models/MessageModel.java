package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by romankov on 14.05.17.
 */

public class MessageModel {

    private UserModel userModel;

    @SerializedName("text")
    private String text;
    @SerializedName("created_at")
    private double createdAt;
    @SerializedName("from_me")
    private boolean fromMe;

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getCreatedAt() {
        return createdAt;
    }

    public boolean getFromMe() {
        return fromMe;
    }

    public String getDate(double createdAt) {
        long timeStamp = (long) createdAt * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedTime = sdf.format(timeStamp);

        return formattedTime;
    }
}
