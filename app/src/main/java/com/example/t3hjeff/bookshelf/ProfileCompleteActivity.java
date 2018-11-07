package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileCompleteActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonCompleteProfile;

    private DatabaseReference databaseReference;
    private EditText editTextName,editTextAddress,editTextPhone,editTextBDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_complete);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ProfileCompleteActivity.this,MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextBDate = (EditText) findViewById(R.id.editTextBDate);

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(user.getEmail());
        buttonCompleteProfile = (Button) findViewById(R.id.buttonCompleteProfile);
        buttonCompleteProfile.setOnClickListener(ProfileCompleteActivity.this);

        buttonCompleteProfile.setOnClickListener(this);
    }

    private void completeProfile() {
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String bdate = editTextBDate.getText().toString().trim();

        UserProfileInfo userProfileInfo = new UserProfileInfo(name,address,phone,bdate);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userProfileInfo);

        Toast.makeText(this,"თქვენი მონაცემები წარმატებით შეინახა", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        if(v == buttonCompleteProfile) {
            completeProfile();
        }
    }
}
