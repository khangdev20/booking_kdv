package com.khangdev.bookingnow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InputInformationCustomer extends AppCompatActivity {

    private Button buttonNext;
    private EditText editTextName , editTextAdress , editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_information_customer);


        initUi ();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateProfile ();

            }
        });

    }

    private void initUi() {
        buttonNext = findViewById(R.id.btn_next);
        editTextName = findViewById(R.id.edt_name);
        editTextAdress = findViewById(R.id.edt_adress);
        editTextPhone = findViewById(R.id.edt_phone);

    }


    private void onClickUpdateProfile() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null)
        {
            return;
        }

        String strFullName = editTextName.getText().toString().trim();
        String strAdress = editTextAdress.getText().toString().trim();
        String strPhone = editTextPhone.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strFullName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference referenceName = database.getReference("realTime-DB/all-user/"+strFullName);

                        UserCustomer userCustomer = new UserCustomer(strFullName , strAdress , strPhone);
                        referenceName.setValue(userCustomer, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(InputInformationCustomer.this , "Update Success!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(InputInformationCustomer.this , MainActivity.class));

                            }
                        });

                    }
                });
    }
}