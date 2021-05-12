package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.R;


public class Entrepreneur_list_Holder extends RecyclerView.ViewHolder{
    public TextView name,job;
    View itemView;
    Context c;
    CardView card;
    Button select;
    public ImageView img;
    public  Entrepreneur_list_Holder(Context c, View itemView,int type) {
        super(itemView);
        this.c = c;

        img = (ImageView) itemView.findViewById(R.id.profile_entrepreneur);
        this.itemView=itemView;
        name=(TextView)itemView.findViewById(R.id.tv_entrepreneur_name);
        job=(TextView)itemView.findViewById(R.id.tv_entrepreneur_job);
         card=itemView.findViewById(R.id.itemViewEntrepreneur);


    }
}
