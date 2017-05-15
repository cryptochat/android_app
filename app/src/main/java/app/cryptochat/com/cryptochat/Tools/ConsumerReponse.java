package app.cryptochat.com.cryptochat.Tools;

/**
 * Created by romankov on 14.05.17.
 */

public interface ConsumerReponse<T, S> {
    void accept(T t, S s) throws Exception;
}
