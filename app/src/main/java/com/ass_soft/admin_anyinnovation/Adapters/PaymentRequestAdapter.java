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
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.Objects.PaymentRequest;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.mentor.Mentor_PayoutRequestDetails;
import com.ass_soft.admin_anyinnovation.organization.Organization_ApproveAccountDetails;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PaymentRequestAdapter extends RecyclerView.Adapter<PaymentRequestAdapter.ViewHolder> {
    Context c;
    ArrayList<PaymentRequest> list;
    int type;
    SharedPref sh;

    public PaymentRequestAdapter(Context c, ArrayList<PaymentRequest> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public PaymentRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payout_request, null);
        PaymentRequestAdapter.ViewHolder rcv = new PaymentRequestAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRequestAdapter.ViewHolder holder, int position) {
        PaymentRequest pp = list.get(position);
        MentorObject p=pp.getMentor();
        holder.name.setText(p.getFullname());
        holder.job.setText(p.getOccupation());
        holder.amount.setText(pp.getAmount());
        String pic= Config.url+p.getPic();
        Glide
                .with(c)
                .load(pic)
                .centerCrop()
                .into(holder.pic);


        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson g=new Gson();
                Intent intent = new Intent(c, Mentor_PayoutRequestDetails.class);
                intent.putExtra("data",g.toJson(pp));
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
                name, speclized, job, city, amount;
        View itemView;
        Context c;
        public ImageView pic, banner;
        Button select;
        RatingBar ratingBar;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
            select = itemView.findViewById(R.id.btn_details);

            this.itemView = itemView;

            name = itemView.findViewById(R.id.tv_mentor_name);
            job = itemView.findViewById(R.id.tv_mentor_job);
            pic=itemView.findViewById(R.id.profile_mentor);
            amount=itemView.findViewById(R.id.tv_requested_amount);

        }
    }
}

