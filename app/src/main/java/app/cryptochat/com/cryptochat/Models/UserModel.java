package app.cryptochat.com.cryptochat.Models;

import java.util.HashMap;

/**
 * Created by amudarisova on 09.05.17.
 */

public class UserModel {

    private int id;
    private String username;
    private String first_name;
    private String last_name;

    public UserModel(int id, String username, String first_name, String last_name) {
        this.id = id;
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return username;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
}

