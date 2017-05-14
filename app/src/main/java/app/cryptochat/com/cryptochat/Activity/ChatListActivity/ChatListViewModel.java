package app.cryptochat.com.cryptochat.Activity.ChatListActivity;

import app.cryptochat.com.cryptochat.Models.ChatModel;

/**
 * Created by romankov on 13.05.17.
 */

public class ChatListViewModel {
    private String name;
    private String lastMessage;
    private String time;
    private boolean isOnline;
    private String urlAvatar;


    ChatListViewModel(ChatModel chatModel){
        this.name = chatModel.getUserModel().getFirstName() +" "+chatModel.getUserModel().getLastName();
        this.lastMessage = chatModel.getLastMessage();
        this.time = "20:15";
        this.isOnline = true;
        this.urlAvatar = null;
    }


    public String getName() {
        return name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getTime() {
        return time;
    }

    public boolean isOnline() {
        return isOnline;
    }


    public String getUrlAvatar() {
        return urlAvatar;
    }

}
