package app.cryptochat.com.cryptochat.Manager;

import java.util.HashMap;

import app.cryptochat.com.cryptochat.Models.СryptoModel;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by romankov on 08.04.17.
 */

public interface RequestInterface {

    @FormUrlEncoded
    @POST("users/auth")
    Observable<СryptoModel> authUser(@Field("identifier") String identifier, @Field("data") String data);

    @GET("key_exchanger/get_public")
    Observable<HashMap<String, String> > fetchPublicKey();

    @FormUrlEncoded
    @POST("key_exchanger/send_public")
    Observable<HashMap<String, String>> sendPublicKey(@Field("identifier") String identifier, @Field("public_key") String publicKey);

    @FormUrlEncoded
    @GET("key_exchanger/verify_shared_key")
    Observable<HashMap<String, String>> verifSharedKey(@Field("identifier") String identifier, @Field("session_key") String session_key);

}
