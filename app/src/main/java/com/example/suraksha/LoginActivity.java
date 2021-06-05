package com.example.suraksha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText adminmail;
    private EditText adminpass;
    private Button createBtn;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginButton);
        adminmail = findViewById(R.id.adminMail);
        adminpass = findViewById(R.id.adminPass);
        createBtn = findViewById(R.id.createButton);
        //initializing firebase authentication

        mAuth = FirebaseAuth.getInstance();



        //Gets executed when admin wants to login to the app

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = adminmail.getText().toString();
                String password = adminpass.getText().toString();
                if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) // checks whether e-mail or password is empty
                {
                    //sign in function for existing user
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //Gets executed when login is successful
                            if (task.isSuccessful()) {

                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);

                            }

                            //when e-mail or password doesnot match
                            else {

                                Toast.makeText(LoginActivity.this, "CANNOT SIGNIN PLEASE CHECK THE FORM AGAIN", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                //when e-mail or password fields are empty
                else {
                    Toast.makeText(LoginActivity.this, "Email or Password is empty", Toast.LENGTH_LONG).show();
                }
            }
        });
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Register.class);
                startActivity(intent);
            }
        });


    }


}
