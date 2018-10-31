package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        buttonRegister.setOnClickListener(this);
    }
    //იუზერის რეგისტრაცია
    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //ელ-ფოსტა ცარიელი თუა
            Toast.makeText(this,"შეიყვანეთ ელ-ფოსტა",Toast.LENGTH_SHORT).show();
            //მხოლოდ ერთხელ ეშვება
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //პაროლი ცარიელი თუა
            Toast.makeText(this,"შეიყვანეთ პაროლი",Toast.LENGTH_SHORT).show();
            //მხოლოდ ერთხელ ეშვება
            return;
        }
        //თუ ვალიდაცია გაიარა, პროგრესბარი, რეგისტრაცია
        progressDialog.setMessage("მიმდინარეობს რეგისტრაცია..");
        progressDialog.show();

        //ფაირბეიზის რეგისტრაციის მეთოდი
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //მომხმარებელმა წარმატებით გაიარა რეგისტრაცია და ავტორიზაცია და მისამართდება პროფილზე
                    Toast.makeText(RegisterActivity.this,"თქვენ წარმატებით გაიარეთ რეგისტრაცია..",Toast.LENGTH_SHORT).show();
                    finish();
                    //ვამისამართებთ პროფილის დასრულების ფეიჯზე
                    startActivity(new Intent(RegisterActivity.this,ProfileCompleteActivity.class));

                }else{
                    //მომხმარებელმა ვერ გაიარა რეგისტრაცია
                    Toast.makeText(RegisterActivity.this,"დაფიქსირდა შეცდომა!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //კლიკის ფუნქცია პასუხისმგებელი ფორმების გადართვა-გადმორთვაზე
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
    }
}
