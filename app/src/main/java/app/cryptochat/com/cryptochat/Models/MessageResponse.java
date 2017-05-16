package app.cryptochat.com.cryptochat.Models;

import java.util.HashMap;

/**
 * Created by romankov on 16.05.17.
 */




public class MessageResponse {
    public Message message;
    MessageResponse(){
        this.message = new Message();
    }

}

class Message extends Object{
    public Header header;
    Message(){
        this.header = new Header();
    }
}

class Header extends Object{
    public String method_name;
}


