package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivityWithEmail extends AppCompatActivity {

    private EditText editTextEmail , editTextPassword;
    private Button buttonSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_with_email);

        initUi();
        initListener();
    }

    private void initListener() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onClickSignUp();
                
            }
        });
    }

    private void onClickSignUp() {

        //Sign UP
        String StrEmail = editTextEmail.getText().toString().trim();
        String StrPassword = editTextPassword.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        mAuth.createUserWithEmailAndPassword(StrEmail, StrPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(SignUpActivityWithEmail.this , MainActivity.class);
                            startActivity(intent);
                            finishAffinity();

                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivityWithEmail.this, "Email này đã được sử dụng, hãy thử một Email khác?",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initUi () {
        editTextEmail = findViewById(R.id.edt_email);
        editTextPassword = findViewById(R.id.edt_password);
        buttonSignUp = findViewById(R.id.btn_signUp);
    }
}