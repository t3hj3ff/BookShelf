package com.example.t3hjeff.bookshelf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddBookActivity extends AppCompatActivity {
    //თულბარი
    private Toolbar mToolBar;

    private ProgressDialog loadingBar;
    private ImageButton selectBookImage;
    private Button addBookButton;
    private EditText bookTitle;
    private EditText bookDescription;
    private EditText bookAuthor;
    private Uri ImageUrl;
    private String Author;
    private String Description;
    private String Title;
    private static final int Gallery_Pick = 1;
    private StorageReference BookReference;
    private DatabaseReference userRef,bookRef;
    private FirebaseAuth mAuth;
    String currentUserID;
    private String saveCurrentDate,saveCurrentTime,bookRandomName,DownloadURL,current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        current_user_id = mAuth.getUid();
        BookReference = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference();
        bookRef = FirebaseDatabase.getInstance().getReference().child("Books");

        selectBookImage = (ImageButton) findViewById(R.id.addimageButton);
        addBookButton = (Button) findViewById(R.id.buttonAddBook);
        bookTitle = (EditText) findViewById(R.id.addbookTitle);
        bookDescription = (EditText) findViewById(R.id.addbookDescription);
        bookAuthor = (EditText) findViewById(R.id.addbookAuthor);

        loadingBar = new ProgressDialog(this);

        mToolBar = (Toolbar) findViewById(R.id.add_book_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("წიგნის დამატება");

        selectBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateBookInfo();
            }
        });
    }

    private void ValidateBookInfo() {
        Author = bookAuthor.getText().toString();
        Description = bookDescription.getText().toString();
        Title = bookTitle.getText().toString();
        if (ImageUrl == null){
            Toast.makeText(this,"წიგნის სურათი არ შეიძლება იყოს ცარიელი",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Author)){
            Toast.makeText(this,"წიგნის სათაური არ შეიძლება იყოს ცარიელი",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Description)){
            Toast.makeText(this,"წიგნის აღწერა არ შეიძლება იყოს ცარიელი",Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Title)){
            Toast.makeText(this,"წიგნის ავტორი არ შეიძლება იყოს ცარიელი",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("ახალი წიგნის დამატება");
            loadingBar.setMessage("გთხოვთ დაელოდეთ.. წიგნი იტვირთება..");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            StoringImageToStorage();
        }
    }

    private void StoringImageToStorage() {
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        bookRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = BookReference.child("Book Images").child(ImageUrl.getLastPathSegment() + bookRandomName + ".jpg");

        filePath.putFile(ImageUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    DownloadURL = task.getResult().toString();
                    Toast.makeText(AddBookActivity.this,"წიგნის სურათი წარმატებით აიტვირთა",Toast.LENGTH_SHORT).show();

                    SaveBookToDatabase();
                }else{
                    String message = task.getException().getMessage();
                    Toast.makeText(AddBookActivity.this,"დაფიქსირდა შეცდომა " + message,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void SaveBookToDatabase() {
        userRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Integer BookLikes = 0;
                    String userFullName = dataSnapshot.child("name").getValue().toString();


                    Title = bookTitle.getText().toString();
                    Description = bookDescription.getText().toString();
                    Author = bookTitle.getText().toString();
                    HashMap booksMap = new HashMap();
                    booksMap.put("uid",current_user_id);
                    booksMap.put("date",saveCurrentDate);
                    booksMap.put("time",saveCurrentTime);
                    booksMap.put("title",Title);
                    booksMap.put("description",Description);
                    booksMap.put("author",Author);
                    booksMap.put("booksimage",DownloadURL);
                    booksMap.put("likes",BookLikes);
                    booksMap.put("authorname",userFullName);

                    bookRef.child(current_user_id + bookRandomName).updateChildren(booksMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()){
                                        SendUserToHomePageActivity();
                                        Toast.makeText(AddBookActivity.this,"წიგნი წარმატებით დაემატა!",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }else {
                                        String message = task.getException().getMessage();
                                        Toast.makeText(AddBookActivity.this,"დაფიქსირდა შეცდომა" + message,Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                }
                            });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,Gallery_Pick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
            ImageUrl = data.getData();
            selectBookImage.setImageURI(ImageUrl);

        }
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
        Intent mainIntent = new Intent(AddBookActivity.this,ProfileActivity.class);
        startActivity(mainIntent);
    }
}
