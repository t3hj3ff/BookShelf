package com.example.t3hjeff.bookshelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {

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
    public long getItemId(int position) {
        return position;
    }

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

        myAdapterViewHolder.t1.setText(books.getTitle());
        Picasso.get().load(books.getBooksimage()).into(myAdapterViewHolder.i1);

    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView t1;
        public ImageView i1;
        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = (TextView)itemView.findViewById(R.id.book_name);
            i1 = (ImageView)itemView.findViewById(R.id.book_image);
        }
    }
}
