package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Objects.Mentor;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.mentor.Mentor_Profile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MentorAdapter1 extends RecyclerView.Adapter<MentorAdapter1.ViewHolder> {
    Context c;
    ArrayList<MentorObject> list;
    int type;
    SharedPref sh;

    public MentorAdapter1(Context c, ArrayList<MentorObject> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public MentorAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_mentor, null);
        MentorAdapter1.ViewHolder rcv = new MentorAdapter1.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MentorAdapter1.ViewHolder holder, int position) {
        MentorObject p = list.get(position);
        holder.name.setText(p.getFullname());
        holder.job.setText(p.getWorktitle());
        holder.points.setText(p.getPoints());

        String url = Config.url + p.getPic();
        Glide.with(c).load(url).into(holder.pic);

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson g=new Gson();
                String data=g.toJson(p);
                Intent intent = new Intent(c, Mentor_Profile.class);
                intent.putExtra("data",data);
               c.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView summery, indus1, indus2, indus3,
                name, speclized, job, city, points;
        View itemView;
        Context c;
        public ImageView pic, banner;
        ConstraintLayout select;
        RatingBar ratingBar;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
            select = itemView.findViewById(R.id.con);
            pic = (ImageView) itemView.findViewById(R.id.profile_mentor);
            points = itemView.findViewById(R.id.tv_gem_points);
            name = itemView.findViewById(R.id.tv_mentor_name);
            job = itemView.findViewById(R.id.tv_mentor_job);
            this.itemView = itemView;


        }
    }
}

