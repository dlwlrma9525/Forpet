package kr.forpet.view.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import kr.forpet.R;
import kr.forpet.view.login.model.LoginModel;

public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = LoginPresenterImpl.class.getName();
    private static final int GOOGLE_SIGN_IN = 9001;

    private LoginPresenter.View mView;
    private LoginModel mLoginModel;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    private CallbackManager mCallbackManager;

    public LoginPresenterImpl() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void onCreate(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mView.updateUi(mAuth.getCurrentUser());
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GOOGLE_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                }
                break;

            default:
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void signInGoogle(Activity activity) {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity.getApplicationContext(), gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void signInFacebook(Activity activity) {
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email", "public_profile", "user_friends"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseAuthWithFacebook(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                mView.updateUi(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "facebookLogin:failure", error);
            }
        });
    }

    @Override
    public void signInTwitter() {

    }

    @Override
    public void signOut() {
        if (mGoogleSignInClient != null) {
            mGoogleSignInClient.signOut();
        }

        mAuth.signOut();
    }

    @Override
    public void delete() {
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().delete();
        }
    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void firebaseAuthWithTwitter() {

    }

    private OnCompleteListener<AuthResult> onCompleteListener = (@NonNull Task<AuthResult> task) -> {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.i(TAG, "signInWithCredential:success");

            FirebaseUser user = mAuth.getCurrentUser();
            mView.updateUi(user);
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            mView.updateUi(null);
        }
    };
}
