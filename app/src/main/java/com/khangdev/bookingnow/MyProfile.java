package com.khangdev.bookingnow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MyProfile extends AppCompatActivity {

    private ImageView imageViewAvatar;
    private Button buttonUpdate;
    private Button buttonExit;
    private TextView editTextFullName;
    private TextView editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initUi();
        setProfileInformation();
        initListener();
    }

    private void initListener() {

        buttonUpdate.setOnClickListener(v -> onClickUpdateProfile());
        buttonExit.setOnClickListener(v -> startActivity(new Intent(MyProfile.this , MainActivity.class)));
    }

    private void onClickUpdateProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            return;
        }

        String strFullName = editTextFullName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(MyProfile.this , "Update Success!", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void setProfileInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            return;
        }
        editTextFullName.setText(user.getDisplayName());
        editTextEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.chusan).into(imageViewAvatar);

    }
    private void initUi() {
        editTextEmail = findViewById(R.id.edt_email);
        imageViewAvatar = findViewById(R.id.img_avatar);
        editTextFullName = findViewById(R.id.edt_full_name);
        buttonExit = findViewById(R.id.btn_exit);
        buttonUpdate = findViewById(R.id.btn_update_profile);

    }
}