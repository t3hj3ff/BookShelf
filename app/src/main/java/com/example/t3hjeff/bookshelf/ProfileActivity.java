package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //თულბარი
    private Toolbar mToolBar;
    //წიგნის დამატება
    private Button buttonAddBook;
    private FirebaseAuth mAuth;
    String currentUserID;

    private TextView textViewUserName;
    private TextView textViewUserAge;

    private RecyclerView bookList;
    private DatabaseReference BooksRef;
    private DatabaseReference UsersRef;
    private TextView current_user_name,current_user_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();



        //პოსტის დამატების ღილაკი
        buttonAddBook = (Button) findViewById(R.id.buttonAddnewpost);
        buttonAddBook.setOnClickListener(this);

        mToolBar = (Toolbar) findViewById(R.id.profile_page_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("პროფილი");

        bookList = (RecyclerView) findViewById(R.id.all_books_view);
        bookList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bookList.setLayoutManager(linearLayoutManager);





        BooksRef = FirebaseDatabase.getInstance().getReference().child("Books");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ReturnUsers returnUsers = dataSnapshot.getValue(ReturnUsers.class);
                current_user_name = (TextView) findViewById(R.id.textViewName);
                current_user_name.setText(returnUsers.getName());
                current_user_age = (TextView) findViewById(R.id.textViewAge);
                current_user_age.setText(returnUsers.getBdate());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Books> options =
                new FirebaseRecyclerOptions.Builder<Books>()
                        .setQuery(BooksRef.orderByChild("uid").equalTo(currentUserID), Books.class)
                        .build();

        FirebaseRecyclerAdapter<Books, ProfileActivity.BooksViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Books, ProfileActivity.BooksViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProfileActivity.BooksViewHolder holder, int position, @NonNull Books model) {
                        holder.book_name.setText(model.getTitle());
                        holder.book_author.setText(model.getAuthor());
                        holder.book_owner.setText(model.getAuthorname());
                        holder.book_owner_address.setText(model.getAuthoraddress());
                        Picasso.get().load(model.getBooksimage()).into(holder.book_image);
                    }

                    @NonNull
                    @Override
                    public ProfileActivity.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_book_display_currentuser,viewGroup,false);
                        ProfileActivity.BooksViewHolder viewHolder = new ProfileActivity.BooksViewHolder(view);
                        return viewHolder;
                    }
                };

        bookList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder{

        TextView book_name,book_author,book_owner,book_owner_address;
        ImageView book_image;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name);
            book_author = itemView.findViewById(R.id.book_author);
            book_owner = itemView.findViewById(R.id.book_owner);
            book_owner_address = itemView.findViewById(R.id.book_owner_address);
            book_image = itemView.findViewById(R.id.book_image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            SendUserToProfileActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void SendUserToProfileActivity() {
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
