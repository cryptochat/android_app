package app.cryptochat.com.cryptochat.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by amudarisova on 08.05.17.
 */

public class CipherMessageResponse {

    @SerializedName("cipher_message")
    @Expose
    private CipherMessageModel cipherMessage;

    public CipherMessageModel getCipherMessage() {
        return cipherMessage;
    }

    public class CipherMessageModel {

        @SerializedName("chats")
        @Expose
        private ArrayList<ChatResponse> chats = null;

        public ArrayList<ChatResponse> getChats() {
            return chats;
        }
    }

    public class ChatResponse {

        @SerializedName("interlocutor")
        @Expose
        private Interlocutor interlocutor;

        @SerializedName("last_message")
        @Expose
        private String lastMessage;
        @SerializedName("is_read")
        @Expose
        private Boolean isRead;
        @SerializedName("from_me")
        @Expose
        private Boolean fromMe;

        public Interlocutor getInterlocutor() {
            return interlocutor;
        }

        public String getLastMessage() {
            return lastMessage;
        }

        public Boolean getIsRead() {
            return isRead;
        }

        public Boolean getFromMe() {
            return fromMe;
        }

    }

    public class Interlocutor {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

    }

}
