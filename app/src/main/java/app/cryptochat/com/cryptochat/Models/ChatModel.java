package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by amudarisova on 10.05.17.
 */

public class ChatModel {

    @SerializedName("last_message")
    private String lastMessage;
    @SerializedName("is_read")
    private boolean isRead;
    @SerializedName("from_me")
    private boolean fromMe;
    @SerializedName("created_at")
    private double createdAt;
    private UserModel userModel;

//    public ChatModel(ChatModelRealm chatModelRealm) {
//        this.lastMessage = chatModelRealm.getLastMessage();
//        this.isRead = chatModelRealm.isRead();
//        this.fromMe = chatModelRealm.isFromMe();
//        this.createdAt = chatModelRealm.getCreatedAt();
//    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public double getCreatedAt() {
        return createdAt;
    }

    public String getDate(double createdAt) {
        long timeStamp = (long) createdAt * 1000;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedTime = sdf.format(timeStamp);

        return formattedTime;
    }

    public void setCreatedAt(double createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
