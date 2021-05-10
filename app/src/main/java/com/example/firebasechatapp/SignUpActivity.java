package com.example.firebasechatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebasechatapp.Model.UserInformation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText userNameEt,userPasswordEt,userEmailEt,userPhoneEt;
    Button createAccountBtn;

    FirebaseAuth mAuth;
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

       // FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference("ChatUsers");

        userNameEt = (EditText) findViewById(R.id.edt_userName);
        userPasswordEt = (EditText) findViewById(R.id.edt_userPassword);
        userEmailEt = (EditText) findViewById(R.id.edt_useremail);
        userPhoneEt = (EditText) findViewById(R.id.edt_userPhone);



        createAccountBtn = (Button) findViewById(R.id.signUpBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userNameEt.getText().toString();
                String password = userPasswordEt.getText().toString();
                String email = userEmailEt.getText().toString();
                String phone = userPhoneEt.getText().toString();

                UserInformation currentuser = new UserInformation(name,email,password,phone);

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //we store user information here into our database
            //                mAuth.getCurrentUser().getUid() we can get current register user id
                            userRef.child(mAuth.getCurrentUser().getUid()).setValue(currentuser);
                            Toast.makeText(SignUpActivity.this,"Account Created!",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish(); //once user registered
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}