package app.cryptochat.com.cryptochat.Manager;

import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.Models.UserModel;

/**
 * Created by amudarisova on 25.04.17.
 */

public class TransitionManager {
    private RealmDataManager _realmDataManager = new RealmDataManager();

    public void saveMyUser(MyUserModel myUserModel) {
        _realmDataManager.createMyUser(myUserModel.getUUID(),
                                     myUserModel.getEmail(),
                                     myUserModel.getUserName(),
                                     myUserModel.getFirstName(),
                                     myUserModel.getLastName(),
                                     myUserModel.getToken());
    }

    public void deleteMyUserById(String userUUID) {
        _realmDataManager.deleteMyUserById(userUUID);
    }

    public void saveUser(UserModel userModel) {
        _realmDataManager.createUser(userModel.getId(),
                                   userModel.getUserName(),
                                   userModel.getFirstName(),
                                   userModel.getLastName());
    }
}
