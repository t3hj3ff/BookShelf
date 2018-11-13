package com.example.t3hjeff.bookshelf;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomePageActivity extends AppCompatActivity {


    //ზედა მენიუს დეკლარაცია, ფაილები navigation_header.xml და /res/menu/navigation_menu.xml
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    //რესაიკლერ ვიუს დეკლარაცია ნავიგაციისთვის
    private RecyclerView recyclerView;
    //ჰედერ თულბარი
    private Toolbar mToolbar;
    //ექშენბარის ტუგლი
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView bookList;
    private RecyclerView userList;
    private DatabaseReference BooksRef;
    private DatabaseReference UsersRef;
    private TextView total_users_num,total_books_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("მთავარი");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(HomePageActivity.this, drawerLayout, R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        bookList = (RecyclerView) findViewById(R.id.all_books_view);
        bookList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        bookList.setLayoutManager(linearLayoutManager);

        userList = (RecyclerView) findViewById(R.id.all_users_view);
        userList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        userList.setLayoutManager(linearLayoutManager1);





        BooksRef = FirebaseDatabase.getInstance().getReference().child("Books");
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long num = dataSnapshot.getChildrenCount();
                total_users_num = (TextView) findViewById(R.id.total_users);
                total_users_num.setText(String.valueOf(num));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        BooksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long num = dataSnapshot.getChildrenCount();
                total_books_num = (TextView) findViewById(R.id.total_books);
                total_books_num.setText(String.valueOf(num));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                UserMenuSelector(menuItem);
                return false;
            }
        });
    }



    //წიგნების რესაიკლერის ონსტარტი
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Books> options =
                new FirebaseRecyclerOptions.Builder<Books>()
                        .setQuery(BooksRef.orderByChild("likes"), Books.class)
                        .build();

        FirebaseRecyclerOptions<Users> options1 =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(UsersRef.orderByChild("userrating"), Users.class)
                        .build();

        FirebaseRecyclerAdapter<Users, HomePageActivity.UsersViewHolder> firebaseRecyclerAdapter1 =
                new FirebaseRecyclerAdapter<Users, HomePageActivity.UsersViewHolder>(options1) {
                    @Override
                    protected void onBindViewHolder(@NonNull HomePageActivity.UsersViewHolder holder, int position, @NonNull Users model1) {
                        holder.user_name.setText(model1.getName());
                        holder.user_sharecount.setText(String.valueOf(model1.getSharecount()));
                        holder.user_age.setText(model1.getBdate());
                    }

                    @NonNull
                    @Override
                    public HomePageActivity.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_users_display,viewGroup,false);
                        HomePageActivity.UsersViewHolder viewHolder = new HomePageActivity.UsersViewHolder(view);
                        return viewHolder;
                    }
                };

        FirebaseRecyclerAdapter<Books, HomePageActivity.BooksViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Books, HomePageActivity.BooksViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull HomePageActivity.BooksViewHolder holder, int position, @NonNull Books model) {
                        holder.book_name.setText(model.getTitle());
                        holder.book_author.setText(model.getAuthor());
                        holder.book_owner.setText(model.getAuthorname());
                        holder.book_owner_address.setText(model.getAuthoraddress());
                        Picasso.get().load(model.getBooksimage()).into(holder.book_image);
                    }

                    @NonNull
                    @Override
                    public HomePageActivity.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_book_display_horizontal,viewGroup,false);
                        HomePageActivity.BooksViewHolder viewHolder = new HomePageActivity.BooksViewHolder(view);
                        return viewHolder;
                    }
                };

        userList.setAdapter((firebaseRecyclerAdapter1));
        firebaseRecyclerAdapter1.startListening();
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

    public static class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView user_name,user_age,user_sharecount;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            user_age = itemView.findViewById(R.id.user_age);
            user_sharecount = itemView.findViewById(R.id.user_sharecount);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                //გადადის მთავარ გვერდზე;
                break;
            case R.id.nav_booklist:
                startActivity(new Intent(HomePageActivity.this,AllBooksActivity.class));
                break;
            case R.id.nav_topbooks:
                startActivity(new Intent(HomePageActivity.this,TopBooksActivity.class));
                break;
            case R.id.nav_parameters:
                //პარამეტრები აპლიკაციის (ვერსია და ყლეობები);
                break;
            case R.id.nav_profile:
                startActivity(new Intent(HomePageActivity.this,ProfileActivity.class));
                break;
            case R.id.nav_logout:
                //ლოგ-აუთი;
                break;
        }
    }
}
