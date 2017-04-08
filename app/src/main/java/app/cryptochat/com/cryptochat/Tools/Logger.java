package app.cryptochat.com.cryptochat.Tools;

import android.util.Log;

/**
 * Created by romankov on 01.04.17.
 */


public class Logger {

    public static final boolean isDebug = true;
    public static final String TAG = "LOG_Crypto";

    public static void l(String statement){
        if (isDebug) {
            Log.v(TAG, statement);
        }
    }

}
