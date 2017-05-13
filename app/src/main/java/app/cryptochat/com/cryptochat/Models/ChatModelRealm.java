package app.cryptochat.com.cryptochat.Models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by amudarisova on 13.05.17.
 */

public class ChatModelRealm extends RealmObject{

    private String lastMessage;
    private boolean isRead;
    private boolean fromMe;
    private int userId;

    public ChatModelRealm() {
    }

    ChatModelRealm(String lastMessage, boolean isRead, boolean fromMe, int userId) {
        this.lastMessage = lastMessage;
        this.isRead = isRead;
        this.fromMe = fromMe;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
