package com.example.t3hjeff.bookshelf;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
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
                    Toast.makeText(MainActivity.this,"შეიყვანეთ პაროლი",Toast.LENGTH_SHORT).show();

                }else{

                }
            }
        });
    }


    //კლიკის ფუნქცია პასუხისმგებელი ფორმების გადართვა-გადმორთვაზე
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
        if (v == buttonLogin) {
            //ავტორიზაციის ფორმაზე უნდა გადავიდეს
        }
    }
}
