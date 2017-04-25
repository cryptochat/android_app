package app.cryptochat.com.cryptochat.Manager;

import app.cryptochat.com.cryptochat.Models.UserModelRealm;
import app.cryptochat.com.cryptochat.Tools.Constants;
import io.realm.Realm;

/**
 * Created by amudarisova on 25.04.17.
 */

public class RealmDataManager {
    private Realm _realm = Realm.getDefaultInstance();


    /**
     *  Добавление user'a
     *
     * @param uuid
     * @param email
     * @param userName
     * @param firstName
     * @param lastName
     * @param token
     */

    public void createUser(final String uuid, final String email, final String userName,
                           final String firstName, final String lastName, final String token) {
        _realm.executeTransaction(realm -> {
            UserModelRealm userModelRealm = realm.createObject(UserModelRealm.class);
            userModelRealm.setUUID(uuid);
            userModelRealm.setEmail(email);
            userModelRealm.setUserName(userName);
            userModelRealm.setFirstName(firstName);
            userModelRealm.setLastName(lastName);
            userModelRealm.setToken(token);
        });
    }


    /**
     * Удаление user'a
     * @param userUUID
     */
    public void deleteUserById(final String userUUID) {
        _realm.executeTransaction(realm -> {
            UserModelRealm userModelRealm = realm.where(UserModelRealm.class).equalTo(Constants.UUID, userUUID).findFirst();
            if (userModelRealm != null) {
                userModelRealm.deleteFromRealm();
            }
        });
    }
}
