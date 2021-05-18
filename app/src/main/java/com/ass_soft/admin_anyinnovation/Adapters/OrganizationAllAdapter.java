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
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.organization.Organization_ApproveAccountDetails;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OrganizationAllAdapter  extends RecyclerView.Adapter<OrganizationAllAdapter.ViewHolder> {
    Context c;
    ArrayList<OrganizationObject> list;
    int type;
    SharedPref sh;

    public OrganizationAllAdapter(Context c, ArrayList<OrganizationObject> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public OrganizationAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_organization, null);
        OrganizationAllAdapter.ViewHolder rcv = new OrganizationAllAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationAllAdapter.ViewHolder holder, int position) {
        OrganizationObject p = list.get(position);
        holder.name.setText(p.getName());
        holder.job.setText(p.getPro_name());


        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson g=new Gson();
                Intent intent = new Intent(c, Organization_ApproveAccountDetails.class);
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
        ConstraintLayout select;
        RatingBar ratingBar;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
            select = itemView.findViewById(R.id.card);

            this.itemView = itemView;

            name = itemView.findViewById(R.id.tv_organization_name);
            job = itemView.findViewById(R.id.tv_program_name);


        }
    }
}
