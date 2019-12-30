package kr.forpet.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import kr.forpet.R;
import kr.forpet.data.db.SQLiteHelper;
import kr.forpet.view.login.activity.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            SQLiteHelper.getAppDatabase(getApplicationContext());
            goToMain();
        }, 1000);
    }

    private void goToMain() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        this.finish();
    }
}