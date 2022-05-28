package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
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

    private ImageView imageViewSignOut, imageViewMyProfile, imageViewListStadium , imageViewChatBox, imageViewAvatar;
    private TextView textViewFullName;
    private TextView textViewEmail;
    private Button buttonAddStadium;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        getDataIntent();

        initUi ();
        showUserInformation();
        imageViewSignOut.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this , "Đăng xuất thành công", Toast.LENGTH_LONG).show();
            onClickSignOut();
            finish();
        });

        imageViewMyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , MyProfile.class));
            }
        });


        imageViewListStadium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ListStadium.class));
            }
        });

        imageViewChatBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , ListStadium.class));
            }
        });


    }

    private void onClickSignOut() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this ,
                LoginWithEmail.class));
    }

    private void initUi() {
        imageViewSignOut = findViewById(R.id.btn_sign_out);
        textViewFullName = findViewById(R.id.tv_full_name);
        textViewEmail = findViewById(R.id.tv_email);
        imageViewAvatar = findViewById(R.id.img_avatar);
        imageViewMyProfile = findViewById(R.id.btn_my_profile);
        imageViewListStadium = findViewById(R.id.btn_list_stadium);
        imageViewChatBox = findViewById(R.id.btn_box_chat);
    }


    public void showUserInformation() {
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
            textViewFullName.setText(name);
        }

        textViewEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.chusan).into(imageViewAvatar);
    }

}