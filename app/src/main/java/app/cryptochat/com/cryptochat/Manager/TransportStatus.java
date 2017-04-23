package app.cryptochat.com.cryptochat.Manager;

import android.support.v4.BuildConfig;

import com.crashlytics.android.Crashlytics;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * Created by romankov on 08.04.17.
 */



public enum TransportStatus {
    TransportStatusDefault,
    TransportStatusSuccess,
    TransportStatusError,
    TransportStatusNotInternet;

    public TransportStatus getStatus(Throwable e) {
        TransportStatus status;
        if(e instanceof ConnectException) {
            status = TransportStatusNotInternet;
        }else if (e instanceof HttpException){
            status = TransportStatusError;
            sendReportCrashlitics(e);
        }else{
            status = TransportStatusError;
            sendReportCrashlitics(e);
        }
        return status;
    }


    public void sendReportCrashlitics(Throwable e){
        String message = "null";
        if(e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            message = "code=" + exception.code() + "&body=" + exception.response().errorBody();
        } else if(e instanceof HttpException){
            JsonSyntaxException exception = (JsonSyntaxException) e;

        }else{
            message = e.getMessage();
        }
        Crashlytics.setString("error",message);
        Crashlytics.logException(e);
    }
}

