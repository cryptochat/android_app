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
    private long createdAt;
    private int userId;

    public ChatModelRealm() {
    }

    ChatModelRealm(String lastMessage, boolean isRead, boolean fromMe, long createdAt, int userId) {
        this.lastMessage = lastMessage;
        this.isRead = isRead;
        this.fromMe = fromMe;
        this.userId = userId;
        this.createdAt = createdAt;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
