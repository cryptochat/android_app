package app.cryptochat.com.cryptochat.Manager;

import java.util.ArrayList;

import app.cryptochat.com.cryptochat.Models.ChatModel;
import app.cryptochat.com.cryptochat.Models.ChatModelRealm;
import app.cryptochat.com.cryptochat.Models.MyUserModel;
import app.cryptochat.com.cryptochat.Models.MyUserModelRealm;
import app.cryptochat.com.cryptochat.Models.UserModel;
import app.cryptochat.com.cryptochat.Models.UserModelRealm;
import app.cryptochat.com.cryptochat.Tools.Constants;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by amudarisova on 25.04.17.
 */

public class RealmDataManager {
    private Realm _realm = Realm.getDefaultInstance();
    private UserModelRealm _userModelRealm;


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
     *  Получение my user'a
     * @return
     */
    public MyUserModel getMyUserModel(){
        RealmResults<MyUserModelRealm> realmResults = _realm.where(MyUserModelRealm.class).findAll();
        if(realmResults.size() == 0){
            return null;
        }
        MyUserModelRealm userModelRealm = realmResults.first();

        return new MyUserModel(userModelRealm);
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


    /**
     * Сохранение user'a
     * @param id
     * @param userName
     * @param firstName
     * @param lastName
     */
    public void createUser(final int id, final String userName,
                           final String firstName, final String lastName) {
        _realm.executeTransaction(realm -> {
            _userModelRealm = realm.createObject(UserModelRealm.class);
            _userModelRealm.setId(id);
            _userModelRealm.setUserName(userName);
            _userModelRealm.setFirstName(firstName);
            _userModelRealm.setLastName(lastName);
        });
    }


    /**
     * Сохранение чата
     * @param lastMessage
     * @param isRead
     * @param fromMe
     */
    public void createChat(final String lastMessage, final boolean isRead, final boolean fromMe) {
        _realm.executeTransaction(realm -> {
            ChatModelRealm chatModelRealm = realm.createObject(ChatModelRealm.class);
            chatModelRealm.setLastMessage(lastMessage);
            chatModelRealm.setRead(isRead);
            chatModelRealm.setFromMe(fromMe);
            chatModelRealm.setUserId(_userModelRealm.getId());
        });
    }
}
