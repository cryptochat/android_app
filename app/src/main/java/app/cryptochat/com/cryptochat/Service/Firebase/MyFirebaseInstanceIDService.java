package app.cryptochat.com.cryptochat.Service.Firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by amudarisova on 15.05.17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String phoneToken = FirebaseInstanceId.getInstance().getToken();
    }
}
