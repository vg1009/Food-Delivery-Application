package com.example.fooddelivery;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SignUp_activity extends AppCompatActivity {

    TextView name,ph,email,pass,alredyhaveacnt;
    Button register;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageButton imageButton;
    LoginButton loginButton;
    private static final String TAG="FacebookAuthentication";
    String gname=null,gph=null,gemail=null,gpass=null;

    private CallbackManager mcallbackmanager;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokentracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=findViewById(R.id.namef);
        ph=findViewById(R.id.phf);
        email=findViewById(R.id.emailf);
        pass=findViewById(R.id.passf);
        register=findViewById(R.id.registerbtn);
//         gname = name.getText().toString();
//         gph = ph.getText().toString();
//         gemail = email.getText().toString();
//         gpass = pass.getText().toString();

        name.addTextChangedListener(loginTextWatcher);
        ph.addTextChangedListener(loginTextWatcher);
        email.addTextChangedListener(loginTextWatcher);
        pass.addTextChangedListener(loginTextWatcher);
        alredyhaveacnt=findViewById(R.id.textView5);
        alredyhaveacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignIn_activity.class);
                startActivity(intent);
            }
        });


       // if (gname!= null && gph!= null && gemail!= null && gpass!= null)
       // {
           // register.setClickable(true);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("user");
                    // reference.setValue("Data stored");

                    //  if (gname!= null && gph!= null && gemail!= null && gpass!= null){
                    dataofmembers d = new dataofmembers(gname, gph, gemail, gpass);
                    reference.child(gph).setValue(d);
                    Toast.makeText(SignUp_activity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),SignIn_activity.class);
                    startActivity(intent);
                    name.setText("");
                    ph.setText("");
                    email.setText("");
                    pass.setText("");
                    //  }
//                else {
//                    Toast.makeText(SignUp_activity.this, "Some Fields are empty", Toast.LENGTH_LONG).show();
//
//                }
                    // register.setClickable(false);
                }
            });
      //  }
//        else {
//            register.setEnabled(false);
//        }



        mfirebaseAuth=FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        imageButton=findViewById(R.id.imageButton3);
        loginButton=findViewById(R.id.fb_button);
        loginButton.setReadPermissions("email","public_profile");
        mcallbackmanager=CallbackManager.Factory.create();
        loginButton.registerCallback(mcallbackmanager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               Log.d(TAG,"onSuccess"+loginResult);
               handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"onError" + error);
            }
        });
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null)
                {
                    updateui(user);
                }
                else
                {
                    updateui(null);
                }
            }
        };

        accessTokentracker=new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken==null)
                {
                    mfirebaseAuth.signOut();
                }
            }
        };

    }

    private void handleFacebookToken(AccessToken token)
    {
       Log.d(TAG,"handleFacebookToken"+token);
        AuthCredential credential= FacebookAuthProvider.getCredential(token.getToken());
        mfirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG,"Sign in with credential successfull");
                    FirebaseUser user=mfirebaseAuth.getCurrentUser();
                    updateui(user);
                    Intent intent=new Intent(getApplicationContext(),Home_activity.class);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG,"Sign in with credential failure",task.getException());
                    Toast.makeText(SignUp_activity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                    updateui(null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mcallbackmanager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateui(FirebaseUser user)
    {
        if(user!=null) {
            name.setText(user.getDisplayName());
           // Toast.makeText(SignUp_activity.this, "FB login successfull", Toast.LENGTH_LONG).show();

            if (user.getPhotoUrl() != null) {
                String photourl = user.getPhotoUrl().toString();
                photourl = photourl + "?type=large";
                Picasso.get().load(photourl).into(imageButton);

            }
        }
        else{
            name.setText("");
            //Toast.makeText(SignUp_activity.this, "FB login unsuccessfull", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mfirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!= null)
        {
            mfirebaseAuth.removeAuthStateListener(authStateListener);

        }
    }

    private TextWatcher loginTextWatcher =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            String wname = name.getText().toString();
//            String wph = ph.getText().toString();
//            String wemail = email.getText().toString();
//            String wpass = pass.getText().toString();

            gname = name.getText().toString();
            gph = ph.getText().toString();
            gemail = email.getText().toString();
            gpass = pass.getText().toString();

            if(gph.length()>0 && gph.length()<10)
            {
                ph.setError("Must contain 10 digits");
                ph.requestFocus();
            }
            if(gpass.length()>0 && gpass.length()<5)
            {
                pass.setError("Must contain atleast 5 digits");
                pass.requestFocus();
            }


            register.setEnabled(!gname.isEmpty() && !gph.isEmpty() && !gemail.isEmpty() && !gpass.isEmpty() && gph.length()==10 && gpass.length()>=5);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
