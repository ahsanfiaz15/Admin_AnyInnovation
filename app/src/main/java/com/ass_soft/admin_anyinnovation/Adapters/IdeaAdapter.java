package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Fellowship;
import com.ass_soft.admin_anyinnovation.Objects.Idea;
import com.ass_soft.admin_anyinnovation.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolder> {
    Context c;
    ArrayList<Idea> list;
    public    interface  Download{
        public void download(Idea idea);
    };
    IdeaAdapter.Download download;
    public IdeaAdapter(Context c, ArrayList<Idea> list, IdeaAdapter.Download download){
        this.c=c;
        this.list=list;
        this.download=download;
    }


    @NonNull
    @Override
    public IdeaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_entrepreneur, null);
        IdeaAdapter.ViewHolder rcv = new IdeaAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull IdeaAdapter.ViewHolder holder, int position) {
        Idea p=list.get(position);
        holder.job.setText(p.getPro_name());
        holder.name.setText(p.getName());

        Glide
                .with(c)
                .load(Config.url+p.getPic())
                .centerCrop()
                .into(holder.img);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        ConstraintLayout cardView;
        public ImageView img; ImageButton download;
        public  ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;

            img = (ImageView) itemView.findViewById(R.id.profile_entrepreneur);
            this.itemView=itemView;
            cardView=itemView.findViewById(R.id.test);
            job=(TextView)itemView.findViewById(R.id.tv_entrepreneur_job);
            name=(TextView)itemView.findViewById(R.id.tv_entrepreneur_name) ;
       //     download=(ImageButton) itemView.findViewById(R.id.btn_download_yil_form);
        }
    }
}

