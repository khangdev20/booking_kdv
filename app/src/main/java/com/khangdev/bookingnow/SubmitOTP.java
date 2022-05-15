package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SubmitOTP extends AppCompatActivity {

    private static final String TAG = SubmitOTP.class.getName();

    private FirebaseAuth mAuth;

    private EditText editTextOTP;
    private Button buttonSubmitOTP;
    private TextView textViewSendOTPAgain;

    private String mPhoneNumber;
    private String mVerificationID;

    private PhoneAuthProvider.ForceResendingToken mForceResendingToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_otp);
        initUi();
        mAuth = FirebaseAuth.getInstance();

        getDataIntent();

        buttonSubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strOTP = editTextOTP.getText().toString().trim();
                onClickSubmitOTP(strOTP);


            }
        });

        textViewSendOTPAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSendOTPAgain();
            }
        });
    }

    private void getDataIntent() {
        mPhoneNumber = getIntent().getStringExtra("phone Number");
        mVerificationID = getIntent().getStringExtra("verificationID");

    }



    private void initUi () {
        editTextOTP = findViewById(R.id.edt_submit_otp);
        buttonSubmitOTP = findViewById(R.id.btn_submit_otp);
        textViewSendOTPAgain = findViewById(R.id.tv_send_otp_again);
    }


    private void onClickSubmitOTP(String strOTP) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationID, strOTP );
        signInWithPhoneAuthCredential(credential);


    }
    private void onClickSendOTPAgain() {


        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mPhoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setForceResendingToken(mForceResendingToken)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(SubmitOTP.this , "Lỗi xác thực!", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(verificationID, forceResendingToken);
                                mVerificationID = verificationID;
                                mForceResendingToken = forceResendingToken;
                            }
                        })
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            goToMainActivity(user.getPhoneNumber());
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(SubmitOTP.this, " // The verification code entered was invalid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void goToMainActivity(String phoneNumber) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("phone Number", phoneNumber);
        startActivity(intent);


    }
}