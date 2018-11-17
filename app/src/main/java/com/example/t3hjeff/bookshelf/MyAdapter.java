package com.example.t3hjeff.bookshelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> implements View.OnClickListener{

    public Context c;
    public ArrayList<Books> arrayList;
    public MyAdapter(Context c, ArrayList<Books> arrayList) {
        this.c=c;
        this.arrayList=arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) { return position; }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.all_book_display,viewGroup,false);
        return new MyAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder myAdapterViewHolder, int i) {
        Books books = arrayList.get(i);

        myAdapterViewHolder.bookId.setId(i);
        myAdapterViewHolder.t1.setText(books.getTitle());
        myAdapterViewHolder.t2.setText(books.getAuthor());
        myAdapterViewHolder.t3.setText(books.getAuthorname());
        myAdapterViewHolder.t4.setText(books.getAuthoraddress());
        Picasso.get().load(books.getBooksimage()).into(myAdapterViewHolder.i1);

    }

    @Override
    public void onClick(View view) {
        //test
        view.setBackgroundColor(1);

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView t1;
        public TextView t2;
        public TextView t3;
        public TextView t4;
        public ImageView i1;
        public TextView bookId;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(R.id.book_name);
            t2 = (TextView)itemView.findViewById(R.id.book_author);
            t3 = (TextView)itemView.findViewById(R.id.book_owner);
            t4 = (TextView)itemView.findViewById(R.id.book_owner_address);
            i1 = (ImageView)itemView.findViewById(R.id.book_image);
            //bookId = (TextView)itemView.findViewById(R.id.bookId);
        }
    }
}
