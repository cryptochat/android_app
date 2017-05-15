package app.cryptochat.com.cryptochat.Activity.SearchUserActivity;

import app.cryptochat.com.cryptochat.Models.UserModel;

/**
 * Created by romankov on 13.05.17.
 */

public class UserViewModel {
    private String urlAvatar;
    private String fullName;
    private String userName;
    private int userId;

    UserViewModel(UserModel userModel){
        urlAvatar = userModel.getAvatar().getUrl();
        fullName = userModel.getFirstName() + " " + userModel.getLastName();
        userName = userModel.getUserName();
        userId = userModel.getId();
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserId() {
        return userId;
    }
}
