package app.cryptochat.com.cryptochat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import app.cryptochat.com.cryptochat.Manager.CryptoManager;
import app.cryptochat.com.cryptochat.Models.CryptoKeyPairModel;
import app.cryptochat.com.cryptochat.R;
import app.cryptochat.com.cryptochat.Tools.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
