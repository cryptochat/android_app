package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.SerializedName;

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
    private UserModel userModel;

    public ChatModel(ChatModelRealm chatModelRealm) {
        this.lastMessage = chatModelRealm.getLastMessage();
        this.isRead = chatModelRealm.isRead();
        this.fromMe = chatModelRealm.isFromMe();
    }

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

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
