package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private Toolbar mToolBar;

    private RecyclerView bookList;
    private DatabaseReference BooksRef;
    private ArrayList<Books> arrayList;
    private EditText EditTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);
        EditTextSearch = (EditText)findViewById(R.id.editTextSearch);
        arrayList = new ArrayList<>();

        mToolBar = (Toolbar) findViewById(R.id.all_books_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("წიგნების ძებნა");


        bookList = (RecyclerView) findViewById(R.id.all_books_view);
        bookList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bookList.setLayoutManager(linearLayoutManager);





        EditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    search(s.toString());
                }else {
                    search("");
                }

            }
        });



        BooksRef = FirebaseDatabase.getInstance().getReference().child("Books");

    }

    private void search(String s) {
        Query query = BooksRef.orderByChild("title")
                .startAt(s)
                .endAt(s + "\uf8ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren()){
                        final Books books = dss.getValue(Books.class);
                        arrayList.add(books);
                    }

                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),arrayList);
                    bookList.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        Intent mainIntent = new Intent(AllBooksActivity.this,HomePageActivity.class);
        startActivity(mainIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Books> options =
                new FirebaseRecyclerOptions.Builder<Books>()
                .setQuery(BooksRef, Books.class)
                .build();

        FirebaseRecyclerAdapter<Books, BooksViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Books, BooksViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull BooksViewHolder holder, int position, @NonNull Books model) {
                        holder.book_id.setText(model.getId());
                        holder.book_name.setText(model.getTitle());
                        holder.book_author.setText(model.getAuthor());
                        holder.book_owner.setText(model.getAuthorname());
                        holder.book_owner_address.setText(model.getAuthoraddress());
                        Picasso.get().load(model.getBooksimage()).into(holder.book_image);
                    }

                    @NonNull
                    @Override
                    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_book_display,viewGroup,false);
                        BooksViewHolder viewHolder = new BooksViewHolder(view);
                        return viewHolder;
                    }
                };

        bookList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class BooksViewHolder extends RecyclerView.ViewHolder{

        TextView book_name,book_author,book_owner,book_owner_address,book_id;
        ImageView book_image;

        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id = itemView.findViewById(R.id.book_Id);
            book_name = itemView.findViewById(R.id.book_name);
            book_author = itemView.findViewById(R.id.book_author);
            book_owner = itemView.findViewById(R.id.book_owner);
            book_owner_address = itemView.findViewById(R.id.book_owner_address);
            book_image = itemView.findViewById(R.id.book_image);
        }
    }
}
