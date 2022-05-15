package com.khangdev.bookingnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button buttonSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataIntent();

        initUi ();
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignOut();
            }
        });

    }

    private void onClickSignOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this ,
                LoginWithEmail.class));
        finish();
    }

    private void initUi() {
        buttonSignOut = findViewById(R.id.btn_sign_out);
    }

    private void getDataIntent() {
        String strPhoneNumber = getIntent().getStringExtra("phone Number");


        //để tạm để không lỗi
        TextView textViewUserInfor = findViewById(R.id.btn_sign_out);
        textViewUserInfor.setText(strPhoneNumber);

    }
}