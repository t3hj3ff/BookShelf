package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //თულბარი
    private Toolbar mToolBar;
    //წიგნის დამატება
    private Button buttonAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //პოსტის დამატების ღილაკი
        buttonAddBook = (Button) findViewById(R.id.buttonAddnewpost);
        buttonAddBook.setOnClickListener(this);

        mToolBar = (Toolbar) findViewById(R.id.profile_page_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("პროფილი");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            SendUserToHomePageActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToHomePageActivity() {
        Intent mainIntent = new Intent(ProfileActivity.this,HomePageActivity.class);
        startActivity(mainIntent);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonAddBook) {
            startActivity(new Intent(ProfileActivity.this,AddBookActivity.class));
        }
    }
}
