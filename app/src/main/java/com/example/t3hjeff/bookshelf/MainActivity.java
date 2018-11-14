package com.example.t3hjeff.bookshelf;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference userRef;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getUid();

        if (firebaseAuth.getCurrentUser() != null){
            //თუ მომხმარებელი ავტორიზირებულია, პირდაპირ ჰოუმპეიჯზე ვუშვებთ
        }
        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }



    //იუზერის ავტორიზაცია
    private void userAuth() {
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
        //თუ ვალიდაცია გაიარა, პროგრესბარი, ავტორიზაცია
        progressDialog.setMessage("მიმდინარეობს მონაცემების დამუშავება...");
        progressDialog.show();

        //ფაირბეიზის რეგისტრაციის მეთოდი
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user_id);
                        if (task.isSuccessful()){

                            finish();
                            //ვმისამართდებით პროფილის დასრულების ფეიჯზე, სადაც იუზერი ატვირთავს სურათს და შეიყვანს დეტალურ ინფორმაციას
                            if (userRef == null) {
                                startActivity(new Intent(MainActivity.this,ProfileCompleteActivity.class));
                            }else {
                                startActivity(new Intent(MainActivity.this,HomePageActivity.class));
                            }

                        }
                    }
                });
    }


    //კლიკის ფუნქცია პასუხისმგებელი ფორმების გადართვა-გადმორთვაზე
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        }
        if (v == buttonLogin) {
            userAuth();
        }
    }
}
