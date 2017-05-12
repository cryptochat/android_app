package app.cryptochat.com.cryptochat.Manager;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.Models.MyUserModelRealm;
import app.cryptochat.com.cryptochat.Models.UserModelRealm;
import app.cryptochat.com.cryptochat.Tools.Constants;
import io.realm.Realm;

/**
 * Created by amudarisova on 25.04.17.
 */

public class RealmDataManager {
    private Realm _realm = Realm.getDefaultInstance();


    /**
     *  Добавление my user'a
     *
     * @param uuid
     * @param email
     * @param userName
     * @param firstName
     * @param lastName
     * @param token
     */

    public void createMyUser(final String uuid, final String email, final String userName,
                             final String firstName, final String lastName, final String token) {
        _realm.executeTransaction(realm -> {
            MyUserModelRealm myUserModelRealm = realm.createObject(MyUserModelRealm.class);
            myUserModelRealm.setUUID(uuid);
            myUserModelRealm.setEmail(email);
            myUserModelRealm.setUserName(userName);
            myUserModelRealm.setFirstName(firstName);
            myUserModelRealm.setLastName(lastName);
            myUserModelRealm.setToken(token);
        });
    }


    /**
     * Удаление my user'a
     * @param userUUID
     */
    public void deleteMyUserById(final String userUUID) {
        _realm.executeTransaction(realm -> {
            MyUserModelRealm myUserModelRealm = realm.where(MyUserModelRealm.class).equalTo(Constants.UUID, userUUID).findFirst();
            if (myUserModelRealm != null) {
                myUserModelRealm.deleteFromRealm();
            }
        });
    }



    public void createUser(final int id, final String userName,
                           final String firstName, final String lastName) {
        _realm.executeTransaction(realm -> {
            UserModelRealm userModelRealm = realm.createObject(UserModelRealm.class);
            userModelRealm.setId(id);
            userModelRealm.setUserName(userName);
            userModelRealm.setFirstName(firstName);
            userModelRealm.setLastName(lastName);

        });
    }

}
