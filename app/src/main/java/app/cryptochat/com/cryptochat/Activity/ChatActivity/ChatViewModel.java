package app.cryptochat.com.cryptochat.Activity.ChatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.TimeZone;

import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.MessageModel;

/**
 * Created by amudarisova on 14.05.17.
 */

public class ChatViewModel {

    private boolean fromMe;
    private String userName;
    private boolean isOnline;
    private String messageText;
    private String createdAt;


    public ChatViewModel(MessageModel messageModel) {
        this.fromMe = messageModel.getFromMe();
        this.userName = messageModel.getUserModel().getUserName();
        this.isOnline = messageModel.getUserModel().getIsOnline();
        this.messageText = messageModel.getText();
        this.createdAt = messageModel.getDate(messageModel.getCreatedAt());
    }

    public ChatViewModel(String message, boolean fromMe, String createdAt) {
        this.messageText = message;
        this.fromMe = fromMe;
        this.createdAt = createdAt;
    }

    public boolean getFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
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

    public String convertToDate(String time) throws ParseException {
        long timestamp = Long.parseLong(time) * 1000;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        String datePublished = sdf.format(timestamp);

        return datePublished;
    }

}
