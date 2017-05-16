package app.cryptochat.com.cryptochat.Models;

import java.util.HashMap;

import retrofit2.http.Body;

/**
 * Created by romankov on 16.05.17.
 */




public class MessageResponse {
     Header header;
     Body body;

    public Header getHeader(){
        return header;
    }

    public Body getBody(){
        return body;
    }


    public static class Header{
        public String method_name;

        public String getMethod_name(){
            return method_name;
        }
    }

    public static class Body{
        String text;
        HashMap sender;

        public HashMap getSender(){
            return sender;
        }

        public String getText(){
            return text;
        }
    }

}




