package app.cryptochat.com.cryptochat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import app.cryptochat.com.cryptochat.Common.CryptoChatApplication;
import app.cryptochat.com.cryptochat.Manager.AuthManager;
import app.cryptochat.com.cryptochat.Manager.ChatManager;

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

        ChatManager chatManager = new ChatManager();
        chatManager.getHistoryUser(token, 32, 10, 0, (d, s) -> {
            signal.countDown();
        });

        signal.await();
    }
}
