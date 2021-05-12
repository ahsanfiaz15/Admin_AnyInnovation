package com.ass_soft.admin_anyinnovation.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Objects.Mentor;
import com.ass_soft.admin_anyinnovation.R;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.ViewHolder> {
    Context c;
    ArrayList<Mentor> list;
    int type;
    SharedPref sh;

    public MentorAdapter(Context c, ArrayList<Mentor> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mentors_list, null);
        ViewHolder rcv = new ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mentor p = list.get(position);
        holder.name.setText(p.getFullname());
        holder.city.setText(p.getCity());
        String industry = p.getIndustry();
        String[] array = industry.split(",");
          if(array.length==3) {
              holder.indus1.setText(array[0]);
              holder.indus2.setText(array[1]);
              holder.indus3.setText(array[2]);
          }
        if(array.length==2) {
            holder.indus1.setText(array[0]);
            holder.indus2.setText(array[1]);

        }
        if(array.length==1) {
            holder.indus1.setText(array[0]);

        }
        String url = Config.url + p.getPic();
        Glide.with(c).load(url).into(holder.pic);
        String url1 = Config.url + p.getCover();
        Glide.with(c).load(url1).into(holder.banner);
        if(p.getRating().length()>0) {
            holder.ratingBar.setRating(Float.parseFloat(p.getRating()));
        }
        holder.select.setVisibility(View.INVISIBLE);
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
        Button select;
        RatingBar ratingBar;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
            select = itemView.findViewById(R.id.btn_select);
            pic = (ImageView) itemView.findViewById(R.id.profile_mentor2);
            banner = (ImageView) itemView.findViewById(R.id.img_cover);
            ratingBar = itemView.findViewById(R.id.mentor_rating);
            this.itemView = itemView;
            summery = (TextView) itemView.findViewById(R.id.tv_summary);
            speclized = (TextView) itemView.findViewById(R.id.tv_specialized_skills);
            indus1 = itemView.findViewById(R.id.tv_industry1);
            indus2 = itemView.findViewById(R.id.tv_industry2);
            indus3 = itemView.findViewById(R.id.tv_industry3);
            name = itemView.findViewById(R.id.tv_mentor_name);
            job = itemView.findViewById(R.id.tv_mentor_job);
            city = itemView.findViewById(R.id.tv_city_name);
            points = itemView.findViewById(R.id.tv_mentor_gem_points);

        }
    }
}
