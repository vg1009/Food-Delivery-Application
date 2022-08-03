package com.example.fooddelivery;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mx.jesusmartinoza.widget.SweetPassword;

public class SignIn_activity extends AppCompatActivity {
    EditText phone;
    EditText passworld;
    DatabaseReference reff;
    String userNameFromDB;
    Button sigin;
    TextView wanttocreateacnt;
    public  void isval()
    {

        final String phoneno=phone.getText().toString().trim();
        final String pass=passworld.getText().toString().trim();
       // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user");
        Query check=FirebaseDatabase.getInstance().getReference("user").orderByChild("dph").equalTo(phoneno);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    phone.setError(null);
                    phone.setEnabled(false);
                    String passworldFromDB = snapshot.child(phoneno).child("dpass").getValue(String.class);
                    if (passworldFromDB.equals(pass)) {
                        phone.setError(null);
                         userNameFromDB = snapshot.child(phoneno).child("dname").getValue(String.class);
                        phone.setEnabled(false);
                        Toast.makeText(SignIn_activity.this, "Logged in", Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(getApplicationContext(),Home_activity.class);
                        intent.putExtra("NAME:",userNameFromDB);
                        startActivity(intent);

                    }
                    else {

                        Toast.makeText(SignIn_activity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(SignIn_activity.this, "Wrong Phone no", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SignIn_activity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });


              /*   Intent intent = new Intent(Sigin_Activity.this, DishesPage.class);
                 startActivity(intent);*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_activity);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

//        getSupportActionBar().hide();
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        phone=findViewById(R.id.phonesignin);
        passworld=findViewById(R.id.passsignin);
        wanttocreateacnt=findViewById(R.id.textView5signin);
        wanttocreateacnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SignUp_activity.class);

                startActivity(i);
            }
        });
        sigin=findViewById(R.id.signinbtn);


        sigin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        isval();



                    }
                }
        );


    }
}
