package app.cryptochat.com.cryptochat.Manager;

import app.cryptochat.com.cryptochat.Models.UserModel;

/**
 * Created by amudarisova on 25.04.17.
 */

public class TransitionManager {
    private RealmDataManager _realmDataManager = new RealmDataManager();

    public void saveUser(UserModel userModel) {
        _realmDataManager.createUser(userModel.getUUID(),
                                     userModel.getEmail(),
                                     userModel.getUserName(),
                                     userModel.getFirstName(),
                                     userModel.getLastName(),
                                     userModel.getToken());
    }

    public void deleteUserById(String userUUID) {
        _realmDataManager.deleteUserById(userUUID);
    }
}
