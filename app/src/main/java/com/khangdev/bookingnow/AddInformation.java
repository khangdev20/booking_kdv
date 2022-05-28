package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AddInformation extends AppCompatActivity {

    private Button buttonSumit;
    private Button buttonBack;
    private EditText editTextFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        initUi();

        buttonSumit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserInformation();

            }
        });


    }

    private void setUserInformation() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String addName = editTextFullName.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(addName)
                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddInformation.this , "Cập nhật thành công!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private void initUi() {

        buttonSumit =  findViewById(R.id.btn_submit_otp);
        buttonBack = findViewById(R.id.btn_exit);
        editTextFullName = findViewById(R.id.edt_add_fullname);

    }
}