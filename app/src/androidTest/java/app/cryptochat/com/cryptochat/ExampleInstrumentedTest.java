package app.cryptochat.com.cryptochat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

import app.cryptochat.com.cryptochat.Common.CryptoChatApplication;
import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.ChatManager;
import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Models.MyUserModel;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("app.cryptochat.com.cryptochat", appContext.getPackageName());
    }

    @Test
    public void fetchHistoryUser() throws Exception{
        final CountDownLatch signal = new CountDownLatch(1);

        AuthManager authManager = new AuthManager();
        String token = authManager.getMyUser().getToken();

        ChatManager chatManager = ChatManager.INSTANCE;
        chatManager.getHistoryUser(token, 32, 10, 0, (d, s) -> {
         //   signal.countDown();
        });

        signal.await();


    }

    @Test
    public void wsConnect() throws Exception{
        final CountDownLatch signal = new CountDownLatch(1);

        CryptoManager cryptoManager = new CryptoManager();
        AuthManager authManager = new AuthManager();
        MyUserModel myUserModel = authManager.getMyUser();
        String token = authManager.getMyUser().getToken();


        ChatManager chatManager = ChatManager.INSTANCE;
        chatManager.setAuth(token, cryptoManager.getCryptoKeyPairModel().get_identifier());
        chatManager.connectChat();

        signal.await();


    }
}
