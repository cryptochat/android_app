package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

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
    private Double createdAt;
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

    public Double getCreatedAt() {
        return createdAt;
    }

    public Date getDate(long createdAt) {
        Date time = new Date(createdAt * 1000);
        return time;
    }

    public void setCreatedAt(Double createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
