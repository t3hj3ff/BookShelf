package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileCompleteActivity extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseAuth;

    private TextView textViewUserEmail;
    private Button buttonCompleteProfile;

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

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        textViewUserEmail.setText(user.getEmail());
        buttonCompleteProfile = (Button) findViewById(R.id.buttonCompleteProfile);
        buttonCompleteProfile.setOnClickListener(ProfileCompleteActivity.this);
    }

    @Override
    public void onClick(View v) {
        //პროფილის ველების შევსების შემდეგ ვამისამართებთ ჰოუპეიჯზე
    }
}
