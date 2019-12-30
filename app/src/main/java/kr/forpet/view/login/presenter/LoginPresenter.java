package kr.forpet.view.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

public interface LoginPresenter {

    void setView(View view);
    void onCreate(Context context);
    void onDestroy();
    void onActivityResult(int requestCode, int resultCode, Intent data);

    void signInGoogle(Activity activity);
    void signInFacebook(Activity activity);
    void signInTwitter();

    void signOut();
    void delete();

    void firebaseAuthWithGoogle(GoogleSignInAccount account);
    void firebaseAuthWithFacebook(AccessToken token);
    void firebaseAuthWithTwitter();

    interface View {
        void updateUi(FirebaseUser user);
    }
}
