package app.cryptochat.com.cryptochat.Activity.SearchUserActivity;

import app.cryptochat.com.cryptochat.Models.UserModel;

/**
 * Created by romankov on 13.05.17.
 */

public class UserViewModel {
    private String urlAvatar;
    private String fullName;

    UserViewModel(UserModel userModel){
        urlAvatar = userModel.getAvatar().getUrl();
        fullName = userModel.getFirstName() + " " + userModel.getLastName();
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public String getFullName() {
        return fullName;
    }
}
