package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import java.util.Random;

import app.cryptochat.com.cryptochat.Models.ChatModel;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatViewModel {

    private boolean fromMe;
    private String userName;
    private boolean isOnline;
    private String messageText;
    private String createdAt;

    public ChatViewModel() {
        this.fromMe = getRandomBoolean();
        this.userName = "Marisa";
        this.isOnline = false;
        this.messageText = "Привет Вася, как твои дела? Понятно";
        this.createdAt = "20:15";
    }

    public boolean getFromMe() {
        return fromMe;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
