package app.cryptochat.com.cryptochat.Manager;

import app.cryptochat.com.cryptochat.Models.MessageModel;

public interface ChatManagerListener{
    public void changeState();
    public void cameMessage(MessageModel messageModel);
}
