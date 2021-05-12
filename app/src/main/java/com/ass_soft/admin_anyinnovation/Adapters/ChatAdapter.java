package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.ass_soft.admin_anyinnovation.Objects.ChatObject;
import com.ass_soft.admin_anyinnovation.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<ChatObject> mainModels;
    Context context;
    String uid;




    public ChatAdapter(Context chatScreen, ArrayList<ChatObject> list, String uid) {
        this.mainModels = list;
        this.context = chatScreen;
        this.uid=uid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        try{
            //set logo to imageview
            //holder.mImageView.setImageResource(mainModels.get(position).getCategoryIcons());
            //set Name to Textview
            final ChatObject c=mainModels.get(position) ;
            if(c.getSender().equals(uid)) {
                   holder.rec.setVisibility(View.GONE);
                   holder.tv_send.setText(c.getData());
                   holder.tv_send_date.setText(c.getDate());
            }
            else{
               holder.send.setVisibility(View.GONE);
               holder.tv_rec.setText(c.getData());
               holder.tv_rec_date.setText(c.getDate());
            }


        }
        catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize Variable
        View view;
        ImageView mImageView;
        TextView tv_send,tv_rec,tv_send_date,tv_rec_date;
       ConstraintLayout send,rec;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            view=itemView;

            send = itemView.findViewById(R.id.send);
            rec=itemView.findViewById(R.id.rec);
            tv_send=itemView.findViewById(R.id.msg_send);
            tv_rec=itemView.findViewById(R.id.msg_receive);
            tv_send_date=itemView.findViewById(R.id.tv_send_time);
            tv_rec_date=itemView.findViewById(R.id.tv_receive_time);
        }
    }
}



