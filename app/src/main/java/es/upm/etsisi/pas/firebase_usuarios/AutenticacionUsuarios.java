package es.upm.etsisi.pas.firebase_usuarios;

// Firebase
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import es.upm.etsisi.pas.BuildConfig;
import es.upm.etsisi.pas.R;
import io.reactivex.rxjava3.annotations.NonNull;

/* Because this class is linked with AuthStateListener, at any point thta the user
 * logs out, it will automatically call the login window.
 */
public class AutenticacionUsuarios {

    final static String LOG_TAG = "btb";

    private final FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private final Activity activity;
    protected CharSequence user_mail;

    private static final int RC_SIGN_IN = 2022;

    public AutenticacionUsuarios(Activity activity){
        user_mail = null;
        mFirebaseAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (isLoggedIn()) {
                    // user is signed in
                    user_mail = mFirebaseAuth.getCurrentUser().getEmail();
                    Toast.makeText(activity,
                            activity.getString(R.string.firebase_user_fmt, user_mail),
                            Toast.LENGTH_LONG).show();
                    Log.i(LOG_TAG, "onAuthStateChanged() " +
                            activity.getString(R.string.firebase_user_fmt, user_mail));
                    ((TextView) activity.findViewById(R.id.textView)).setText(
                            activity.getString(R.string.firebase_user_fmt, user_mail));
                } else {
                    // user is signed out
                    activity.startActivity(
                            // Get an instance of AuthUI based on the default activity
                            AuthUI.getInstance().
                                    createSignInIntentBuilder().
                                    setTheme(R.style.LoginTheme).
                                    setLogo(R.mipmap.ic_logo_auth).
                                    setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                    )).
                                    setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */,
                                            true /* hints */).
                                    //setIsSmartLockEnabled(!BuildConfig.DEBUG /* credentials */,
                                    //true /* hints */).
                                            build()
                    );
                }
            }
        };
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    public void login() {
        Toast.makeText(activity,"Login!",Toast.LENGTH_SHORT).show();
    }

    public Boolean isLoggedIn() {
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        return (user != null);
    }

    public void logOut(){
        mFirebaseAuth.signOut();
    }
}
