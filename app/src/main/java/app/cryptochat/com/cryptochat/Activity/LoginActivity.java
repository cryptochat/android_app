package app.cryptochat.com.cryptochat.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.jakewharton.rxbinding2.widget.RxTextView;
import app.cryptochat.com.cryptochat.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

public class LoginActivity extends AppCompatActivity {
    private EditText _email, _password;
    private Button _btnLogin;

    private Flowable<CharSequence> _emailChangeObservable;
    private Flowable<CharSequence> _passwordChangeObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _email      = (EditText) findViewById(R.id.edit_email);
        _password   = (EditText) findViewById(R.id.edit_password);
        _btnLogin   = (Button) findViewById(R.id.button_log_in);
        _btnLogin.setEnabled(false);

        _emailChangeObservable =
                RxTextView.textChanges(_email).skip(1).toFlowable(BackpressureStrategy.LATEST);

        _passwordChangeObservable =
                RxTextView.textChanges(_password).skip(1).toFlowable(BackpressureStrategy.LATEST);

        _combineLatestEvents();

    }

    private void _combineLatestEvents() {

        Flowable.combineLatest(
                _emailChangeObservable,
                _passwordChangeObservable,
                (newEmail, newPassword) -> {
                    boolean emailValid = _validateEmail(newEmail);
                    if (!emailValid) {
                        _email.setError(getText(R.string.invalid_email));
                    }

                    boolean passValid = _validatePassword(newPassword);
                    if (!passValid) {
                        _password.setError(getText(R.string.invalid_password));
                    }

                    return emailValid && passValid;
                })
                .subscribe(_btnLogin::setEnabled);
    }

    private boolean _validateEmail(CharSequence email) {
        return  !isEmpty(email) &&
                EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean _validatePassword(CharSequence password) {
        return  !isEmpty(password) &&
                password.length() > 6;
    }


}
