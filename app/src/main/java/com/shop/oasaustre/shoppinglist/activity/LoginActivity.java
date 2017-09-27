package com.shop.oasaustre.shoppinglist.activity;

import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gelitenight.waveview.library.WaveView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.app.App;
import com.shop.oasaustre.shoppinglist.app.User;
import com.shop.oasaustre.shoppinglist.db.entity.Usuario;
import com.shop.oasaustre.shoppinglist.db.service.IUsuarioService;
import com.shop.oasaustre.shoppinglist.db.service.ServiceFactory;
import com.shop.oasaustre.shoppinglist.service.ParameterService;
import com.shop.oasaustre.shoppinglist.util.WaveHelper;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;



public class LoginActivity extends AppCompatActivity {

    private WaveHelper mWaveHelper;
    private WaveView waveView;
    private RelativeLayout container;
    private RelativeLayout layoutButton;
    private SignInButton btnGoogle;
    private LoginButton btnFacebook;
    private TwitterLoginButton btnTwitter;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    private static final int RC_GOOGLE_SIGN_IN = 123;
    private GoogleApiClient googleApiClient;

    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        container = (RelativeLayout) findViewById(R.id.content_login);
        layoutButton = (RelativeLayout) findViewById(R.id.layoutButton);

        btnGoogle = (SignInButton) findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogin();
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        waveView = (WaveView) findViewById(R.id.wave);

        this.mWaveHelper = new WaveHelper(this.waveView);
        this.waveView.setShapeType(WaveView.ShapeType.SQUARE);
        this.waveView.setWaveColor(Color.parseColor("#FFFFFF"), Color.parseColor("#00000000"));

        //Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showSnackbar(getString(R.string.error_connection_failed));
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Facebook
        callbackManager = CallbackManager.Factory.create();
        btnFacebook = (LoginButton) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress();
            }
        });
        btnFacebook.setReadPermissions("email", "public_profile");
        btnFacebook.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override public void onSuccess(LoginResult loginResult) {
                        facebookAuth(loginResult.getAccessToken());
                    }
                    @Override public void onCancel() {
                        showSnackbar(getResources().getString(R.string.error_cancelled));
                    }
                    @Override public void onError(FacebookException error) {
                        showSnackbar(error.getLocalizedMessage());
                    }
                });

        //Twitter
        btnTwitter = (TwitterLoginButton) findViewById(R.id.btnTwitter);
        btnTwitter.setCallback(new Callback<TwitterSession>() {
            @Override public void success(Result<TwitterSession> result) {
                twitterAuth(result.data);
            }
            @Override public void failure(TwitterException exception) {
                showSnackbar(exception.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        this.mWaveHelper.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mWaveHelper.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                           Intent data) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                googleAuth(account);
            } else {
                hideProgress();
                showSnackbar(getResources().getString(R.string.error_google));
            }
        }else if (requestCode == btnFacebook.getRequestCode()) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }else if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            btnTwitter.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void googleLogin() {
        showProgress();
        Intent signInIntent = Auth.GoogleSignInApi
                .getSignInIntent( googleApiClient);
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    private void googleAuth(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(
                acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            hideProgress();
                            showSnackbar(task.getException().getLocalizedMessage());
                        } else {
                            doLogin();
                        }
                    }
                });
    }

    private void facebookAuth(AccessToken accessToken) {
        final AuthCredential credential = FacebookAuthProvider.getCredential(
                accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            hideProgress();
                            if (task.getException() instanceof
                                    FirebaseAuthUserCollisionException) {
                                LoginManager.getInstance().logOut();
                            }
                            showSnackbar(task.getException().getLocalizedMessage());
                        } else {
                            doLogin();
                        }
                    }
                });

    }

    private void twitterAuth(TwitterSession session) {
        AuthCredential credential = TwitterAuthProvider.getCredential(
                session.getAuthToken().token,
                session.getAuthToken().secret);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            hideProgress();
                            showSnackbar(task.getException().getLocalizedMessage());
                        } else {
                            doLogin();
                        }
                    }
                });
    }



    private void showSnackbar(String message) {
        Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
    }

    private void showProgress() {
        layoutButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgress() {
        layoutButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    private void doLogin() {
        FirebaseUser currentUser = auth.getCurrentUser();
        User user = new User();
        if (currentUser != null) {
            user.setName(currentUser.getDisplayName());
            user.setEmail(currentUser.getEmail());
            user.setProvider(currentUser.getProviders().get(0));
            user.setUid(currentUser.getUid());
            user.setImage(currentUser.getPhotoUrl());



            ((App) this.getApplication()).setUser(user);

            IUsuarioService usuarioService = ServiceFactory.getInstance().create(this,((App) this.getApplication()),"firebase");
            for (UserInfo profile : currentUser.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                Uri photoUrl = profile.getPhotoUrl();
            };

            Usuario usuario = new Usuario();
            usuario.setUid(user.getUid());
            usuario.setEmail(user.getEmail());
            usuario.setName(user.getName());
            usuario.setProvider(user.getProvider());

            usuarioService.createUser(usuario);

            if(!isServiceRunning()){
                this.startService(new Intent(this, ParameterService.class));
            }




        }
    }

    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(ParameterService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
