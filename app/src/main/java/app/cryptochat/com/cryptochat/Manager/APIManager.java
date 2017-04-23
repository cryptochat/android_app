package app.cryptochat.com.cryptochat.Manager;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by romankov on 08.04.17.
 */

public class APIManager {
    public static final APIManager INSTANCE = new APIManager();
    private static RequestInterface requestInterface;
    private Retrofit retrofit;

    public RequestInterface getRequestInterface(){
        if(requestInterface == null){
             requestInterface = new Retrofit.Builder()
                    .baseUrl("http://wishbyte.org/api/v1/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(RequestInterface.class);
        }
        return requestInterface;
    }

}
