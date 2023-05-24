package com.real.hadi.addcontact;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageListLayoutRecyclerAdapter extends RecyclerView.Adapter<MessageListLayoutRecyclerAdapter.NewsViewHolder> {
    ArrayList<MessageListLayout> digital;
    RecyclerView recyclerView;
    ArrayList<ListLayout> sampleNews;
    private Context context;
    private int lastPosition = -1;
    ArrayList<String> read;

    public MessageListLayoutRecyclerAdapter(ArrayList<MessageListLayout> phonelap) {
        digital = new ArrayList<>();
        digital = phonelap;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messagelistlayout, parent, false);
        context = parent.getContext();
        read = new ArrayList<>();

        return new NewsViewHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, final int position) {

        final MessageListLayout dataModel = digital.get(position);
        if(dataModel.getReads()!=null) {
            if (dataModel.getReads().contains(dataModel.getUserId())) {
                holder.imgPocket.setImageResource(R.drawable.ic_remove_red_eye_green_a700_24dp);
            } else
                if (!dataModel.getReads().contains(dataModel.getUserId())) {
                    if (dataModel.getPriority().equals("1")) {
                        holder.imgPocket.setImageResource(R.drawable.ic_local_post_office_red_400_24dp);
                    } else if (dataModel.getPriority().equals("0")) {
                        holder.imgPocket.setImageResource(R.drawable.ic_local_post_office_white_24dp);
                    }
                }
            }

        holder.txtTitle.setText(dataModel.getTitle());
        setAnimation(holder.itemView, position);
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MessageDetail.class);
                intent.putExtra("messageId", dataModel.getId());
                intent.putExtra("detail", dataModel.getDetail());
                intent.putExtra("userId", dataModel.getUserId());
                intent.putExtra("fullname", dataModel.getFullname());
                intent.putExtra("token", dataModel.getToken());
                context.startActivity(intent);
            }
        });

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
        ImageView imgDetail, imgPocket;
        LinearLayout linear;
        ArrayList<String> reads;

        public NewsViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imgDetail = (ImageView) itemView.findViewById(R.id.imgDetail);
            imgPocket = (ImageView) itemView.findViewById(R.id.imgPocket);
            linear = (LinearLayout) itemView.findViewById(R.id.linear);
            reads=new ArrayList<String>();
        }


    }

}
