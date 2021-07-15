package com.example.fifo2_sample_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActivity";
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mAuth;

    private SignInButton btnSignin;
    private Button btnSignout;


    // TODO 아래의 값 의미 확인 필요함
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_CODE_REGISTER = 0;
    private static final int REQUEST_CODE_SIGN = 1;
    private static final int GET_ACCOUNTS_PERMISSIONS_REQUEST_REGISTER = 0x11;
    private static final int GET_ACCOUNTS_PERMISSIONS_REQUEST_SIGN = 0x13;
    private static final int GET_ACCOUNTS_PERMISSIONS_ALL_TOKENS = 0x15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestIdToken(Constants.SERVER_CLIENT_ID)
                        .build();
        googleApiClient =
                new GoogleApiClient.Builder(this)
                        .enableAutoManage(this, this /* OnConnectionFailedListener */)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

        btnSignin = findViewById(R.id.main_btn_sign);
        btnSignin.setSize(SignInButton.SIZE_WIDE);
        btnSignin.setScopes(gso.getScopeArray());
        btnSignin.setOnClickListener(this);

        btnSignout = findViewById(R.id.main_btn_signout);
        btnSignout.setOnClickListener(this);

        updateUI();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_sign:
                signIn();
                break;
            case R.id.main_btn_signout:
                signOut();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, String.format("onActivityResult : %d, %d", requestCode, resultCode));

        if (RC_SIGN_IN == requestCode) {
            GoogleSignInResult siginInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(siginInResult);
            return;
        }
    }

    private void updateUI() {
        if (getAccountSignInStatus()) {
            btnSignin.setVisibility(View.GONE);
            btnSignout.setVisibility(View.VISIBLE);
        }
        else {
            btnSignin.setVisibility(View.VISIBLE);
            btnSignout.setVisibility(View.GONE);
        }
    }

    private boolean getAccountSignInStatus() {
        boolean res;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        res = settings.getBoolean(Constants.PREF_SIGNED_IN_STATUS, false);
        Log.d(TAG, "getAccountSignInStatus : " + res);

        return res;
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient)
                .setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                clearAccountSignInStatus();
                                updateUI();
                            }
                        });
    }

    private void saveAccountSignInStatus() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.PREF_SIGNED_IN_STATUS, true);
        Log.d(TAG, "Save account sign in status: true");
        editor.apply();
    }

    private void clearAccountSignInStatus() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(Constants.PREF_SIGNED_IN_STATUS, false);
        Log.d(TAG, "Clear account sign in status");
        editor.apply();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        Log.d(TAG, "sign in result: " + result.getStatus().toString());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            saveAccountSignInStatus();
            Log.d(TAG, "account email" + acct.getEmail());
            Log.d(TAG, "account displayName" + acct.getDisplayName());
            Log.d(TAG, "account id" + acct.getId());
            Log.d(TAG, "account idToken" + acct.getIdToken());
            Log.d(TAG, "account scopes" + acct.getGrantedScopes());
        }
        updateUI();
    }

    @Override
    public void onConnectionFailed(@NonNull @org.jetbrains.annotations.NotNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}