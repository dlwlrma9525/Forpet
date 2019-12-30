package kr.forpet.view.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.forpet.R;
import kr.forpet.view.login.presenter.LoginPresenter;
import kr.forpet.view.login.presenter.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    private static final String TAG = LoginActivity.class.getName();

    private LoginPresenter mLoginPresenter;

    @BindView(R.id.button_sign_in_google)
    SignInButton buttonSignInGoogle;

    @BindView(R.id.button_sign_in_facebook)
    Button buttonSingInFacebook;

    @BindView(R.id.text_sign_out)
    TextView textSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mLoginPresenter = new LoginPresenterImpl();
        mLoginPresenter.setView(this);
        mLoginPresenter.onCreate(this);

        buttonSignInGoogle.setOnClickListener((v) -> mLoginPresenter.signInGoogle(LoginActivity.this));
        buttonSingInFacebook.setOnClickListener((v) -> mLoginPresenter.signInFacebook(this));

        textSignOut.setOnClickListener((v) -> mLoginPresenter.signOut());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mLoginPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updateUi(FirebaseUser user) {
        if (user != null) {
            user.getIdToken(true)
                    .addOnCompleteListener((@NonNull Task<GetTokenResult> task) -> {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.i(TAG, "Token: " + idToken);
                        }
                    });
            Toast.makeText(this, "firebase auth is signed..", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "sign in failed..", Toast.LENGTH_SHORT).show();
        }
    }
}
