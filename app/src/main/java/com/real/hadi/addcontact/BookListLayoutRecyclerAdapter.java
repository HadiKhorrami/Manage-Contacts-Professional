package com.real.hadi.addcontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class BookListLayoutRecyclerAdapter extends RecyclerView.Adapter<BookListLayoutRecyclerAdapter.NewsViewHolder> {
    ArrayList<BookListLayout> digital;
    RecyclerView recyclerView;
    ArrayList<BookListLayout> sampleNews;
    private Context context;
    private int lastPosition = -1;

    public BookListLayoutRecyclerAdapter(ArrayList<BookListLayout> phonelap) {
        digital = new ArrayList<>();
        digital = phonelap;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklistlayout, parent, false);
        context = parent.getContext();
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {


        final BookListLayout dataModel = digital.get(position);
        holder.txtTitle.setText(dataModel.getTitle());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BookDetail.class);
                intent.putExtra("title", dataModel.getTitle());
                intent.putExtra("detail", dataModel.getDetail());
                intent.putExtra("userId", dataModel.getUserId());
                intent.putExtra("fullname", dataModel.getFullname());
                intent.putExtra("token", dataModel.getToken());
                context.startActivity(intent);
            }
        });
        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return digital.size();
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public int id;
        ImageView imgBook;
        ConstraintLayout layout;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgBook = (ImageView) itemView.findViewById(R.id.imgBook);
            layout = (ConstraintLayout) itemView.findViewById(R.id.layout);
        }


    }

}
