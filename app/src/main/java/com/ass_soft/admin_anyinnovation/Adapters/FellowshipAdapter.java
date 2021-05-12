package com.ass_soft.admin_anyinnovation.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.ass_soft.admin_anyinnovation.Objects.BookClubRecord;
import com.ass_soft.admin_anyinnovation.Objects.Fellowship;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_YILFellowship;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FellowshipAdapter extends RecyclerView.Adapter<FellowshipAdapter.ViewHolder> {
    Context c;
    ArrayList<Fellowship> list;
 public    interface  Download{
        public void download(Fellowship fellowship);
    };
    Download download;
    public FellowshipAdapter(Context c,ArrayList<Fellowship> list,Download download){
        this.c=c;
        this.list=list;
        this.download=download;
    }


    @NonNull
    @Override
    public FellowshipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_entrepreneur_yil, null);
        FellowshipAdapter.ViewHolder rcv = new FellowshipAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull FellowshipAdapter.ViewHolder holder, int position) {
        Fellowship p=list.get(position);
        holder.job.setText(p.getPro_name());
        holder.name.setText(p.getFullname());

        Glide
                .with(c)
                .load(Config.url+p.getPic())
                .centerCrop()
                .into(holder.img);
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               download.download(p);
            }
        });
    }

 

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name,job;
        View itemView;
        Context c;
        public ImageView img; ImageButton download;
        public  ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;

            img = (ImageView) itemView.findViewById(R.id.profile_entrepreneur);
            this.itemView=itemView;

            job=(TextView)itemView.findViewById(R.id.tv_entrepreneur_job);
            name=(TextView)itemView.findViewById(R.id.tv_entrepreneur_name) ;
            download=(ImageButton) itemView.findViewById(R.id.btn_download_yil_form);
        }
    }
}
