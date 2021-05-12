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
import com.ass_soft.admin_anyinnovation.Objects.Investor;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.investor.InvestorApproveAccountDetails;
import com.ass_soft.admin_anyinnovation.investor.InvestorProfile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InvestorNewAdapter extends RecyclerView.Adapter<InvestorNewAdapter.ViewHolder> {
    Context c;
    ArrayList<Investor> list;
    int type;
    SharedPref sh;

    public InvestorNewAdapter(Context c, ArrayList<Investor> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public InvestorNewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approve_investor_account, null);
        InvestorNewAdapter.ViewHolder rcv = new InvestorNewAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull InvestorNewAdapter.ViewHolder holder, int position) {
        Investor p = list.get(position);
        holder.name.setText(p.getFullname());
        holder.city.setText(p.getCity());

        String url = Config.url + p.getPic();
        Glide.with(c).load(url).into(holder.pic);
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson g=new Gson();
                Intent intent = new Intent(c, InvestorApproveAccountDetails.class);
                intent.putExtra("data",g.toJson(p));
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
        Button select;
        RatingBar ratingBar;
        ConstraintLayout con;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;

            pic = (ImageView) itemView.findViewById(R.id.profile_investor);

            this.itemView = itemView;
            select=itemView.findViewById(R.id.btn_details);
            name = itemView.findViewById(R.id.tv_investor_name);
            city = itemView.findViewById(R.id.tv_investor_city);

        }
    }
}
