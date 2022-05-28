package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginWithEmail extends AppCompatActivity {

    private LinearLayout linearLayoutSignUp , loginWithAdmin;
    private FirebaseAuth mAuth;
    private Button buttonLogin;
    private EditText editTextEmail , editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_email);
        mAuth = FirebaseAuth.getInstance();
        initUi();
        initListener();



    }

    private void initListener(){
        linearLayoutSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginWithEmail.this , SignUpActivityWithEmail.class);
                startActivity(intent);

            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = editTextEmail.getText().toString().trim();
                String strPassword = editTextPassword.getText().toString().trim();
                if (strEmail.length() <= 0 && strPassword.length() <= 0 ) {

                    Toast.makeText(LoginWithEmail.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG ).show();

                } else {
                    mAuth.signInWithEmailAndPassword(strEmail, strPassword).addOnCompleteListener(LoginWithEmail.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // như cái kia
                                Intent intent = new Intent(LoginWithEmail.this ,  MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginWithEmail.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
        editTextEmail = findViewById(R.id.edt_email);
        editTextPassword = findViewById(R.id.edt_password);
    }



}