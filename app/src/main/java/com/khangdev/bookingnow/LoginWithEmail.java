package com.khangdev.bookingnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginWithEmail extends AppCompatActivity {

    private LinearLayout linearLayoutSignUp;
    private FirebaseAuth mAuth;

    private Button buttonLogin;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);

        mAuth = FirebaseAuth.getInstance();

        initUi();
        initListener();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }




    private void initUi (){
        linearLayoutSignUp = findViewById(R.id.layout_signUP);

        buttonLogin = findViewById(R.id.btn_login_with_email);
    }

    private void initListener(){
        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginWithEmail.this , SignUpActivityWithEmail.class);
                startActivity(intent);

            }
        });

    }

}