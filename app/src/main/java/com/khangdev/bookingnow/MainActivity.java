package com.khangdev.bookingnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button buttonSignOut;
    private TextView textViewFullName;
    private ImageView imageViewAvatar;
    private TextView textViewEmail;
    private TextView textViewNumberPhone;
    private TextView textViewLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataIntent();

        initUi ();
        showUserInformation();
        buttonSignOut.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this , "Đăng xuất thành công", Toast.LENGTH_LONG).show();
            onClickSignOut();
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
        textViewFullName = findViewById(R.id.tv_full_name);
        textViewEmail = findViewById(R.id.tv_email);
        //textViewLocation = findViewById(R.id.tv_location);
        imageViewAvatar = findViewById(R.id.img_avatar);
        //textViewNumberPhone = findViewById(R.id.tv_number_phone);


    }


    private void getDataIntent() {
        String strPhoneNumber = getIntent().getStringExtra("phone Number");


        //để tạm để không lỗi
        TextView textViewUserInfor = findViewById(R.id.btn_sign_out);
        textViewUserInfor.setText(strPhoneNumber);

    }

    private void showUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
        {
            return;
        }

        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if (name == null)
        {
            textViewFullName.setVisibility(View.GONE);
        }
        else
        {
            textViewFullName.setVisibility(View.VISIBLE);
        }
        textViewFullName.setText(name);
        textViewEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.chusan).into(imageViewAvatar);
    }
}