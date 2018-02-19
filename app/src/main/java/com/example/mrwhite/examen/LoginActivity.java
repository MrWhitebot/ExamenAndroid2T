package com.example.mrwhite.examen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.mrwhite.examen.DataHolder.DataHolder;
import com.example.mrwhite.examen.FireBase.FirebaseAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.crash.FirebaseCrash;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by manuel.blanco on 13/02/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;

    private JSONObject datosTwitter;

    Animation rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DataHolder.firebaseAdmin = new FirebaseAdmin();

        Twitter.initialize(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        //rotation = AnimationUtils.loadAnimation(this, R.anim.anim1);

        loginButton= findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                datosTwitter = new JSONObject();
                try {
                    datosTwitter.put("UserName", result.data.getUserName().toString());
                    datosTwitter.put("id", String.valueOf(result.data.getUserId()));
                } catch (JSONException e) {
                    FirebaseCrash.report(new Exception("CREATE JSON WITH USER DATA FAIL"));
                    e.printStackTrace();
                }
                Log.v("USERNAME TWITTER", result.data.getUserName().toString());
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                FirebaseCrash.report(new Exception("USER FAIL AT LOG IN"));
            }

        });
        //loginButton.startAnimation(rotation);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void handleTwitterSession(TwitterSession session) {
        Log.d("Sesion Iniciada Twitter", "handleTwitterSession:" + session);

        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);


       DataHolder.instance.fireBaseAdmin.mAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TwitterSuccess", "signInWithCredential:success");
                            DataHolder.instance.fireBaseAdmin.user  = DataHolder.instance.fireBaseAdmin.mAuth.getCurrentUser();
                            //updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TwitterFailure", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                        // ...
                    }
                });
        //Almacenamos los datos deL USUAIRO DE tWITTER
        DataHolder.instance.jsonObjectTwitter = datosTwitter;

        //CAMBIO DE ACTIVIDAD UNA VEZ OBTENIDO LOS RESULTADOS
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

}
