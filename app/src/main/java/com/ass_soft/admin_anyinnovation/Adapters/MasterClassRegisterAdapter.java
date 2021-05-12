package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.BuyProduct;
import com.ass_soft.admin_anyinnovation.Objects.MasterClassRegister;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MasterClassRegisterAdapter extends RecyclerView.Adapter<MasterClassRegisterAdapter.ViewHolder> {
    Context c;
    ArrayList<MasterClassRegister> list;
    public MasterClassRegisterAdapter(Context c,ArrayList<MasterClassRegister> list){
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public MasterClassRegisterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_entrepreneur, null);
        MasterClassRegisterAdapter.ViewHolder rcv = new MasterClassRegisterAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull MasterClassRegisterAdapter.ViewHolder holder, int position) {
        MasterClassRegister p=list.get(position);
        holder.title.setText(p.getPro_name());
        holder.name.setText(p.getName());

        Glide
                .with(c)
                .load(Config.url+p.getPic())
                .centerCrop()
                .into(holder.img);
        holder.con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(c, Entrepreneur_Profile.class);
                intent.putExtra("email",p.getOldemail());
                c.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,des,title;
        View itemView;
        Context c;
        ConstraintLayout con;
        public ImageView img; ImageButton cancel;
        public  ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;

            img = (ImageView) itemView.findViewById(R.id.profile_entrepreneur);
            this.itemView=itemView;
            title=(TextView)itemView.findViewById(R.id.tv_entrepreneur_job);
            con=itemView.findViewById(R.id.itemViewEntrepreneur);
            name=(TextView)itemView.findViewById(R.id.tv_entrepreneur_name) ;

        }
    }
}

