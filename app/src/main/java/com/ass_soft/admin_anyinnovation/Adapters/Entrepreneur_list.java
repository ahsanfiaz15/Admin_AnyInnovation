package com.ass_soft.admin_anyinnovation.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Entrepreneur;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Entrepreneur_list extends RecyclerView.Adapter<Entrepreneur_list_Holder> {
    private Context context;
    private ArrayList<com.ass_soft.admin_anyinnovation.Objects.Entrepreneur> cat_list;
    int type;

    public Entrepreneur_list(Context c, ArrayList<Entrepreneur> cat_list,int type) {
        context = c;

        this.cat_list = cat_list;
        this.type=type;
    }


    @NonNull
    @Override
    public Entrepreneur_list_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


           View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_entrepreneur, null);
           Entrepreneur_list_Holder rcv = new Entrepreneur_list_Holder(context, layoutView,type);
           return rcv;


    }

    @Override
    public void onBindViewHolder(@NonNull Entrepreneur_list_Holder holder, int position) {

        Entrepreneur object = cat_list.get(position);
         holder.name.setText(object.getFullname());
         holder.job.setText(object.getPro_name());
         String pic= Config.url+object.getProfile();
        Glide
                .with(context)
                .load(pic)
                .centerCrop()
                .into(holder.img);

       /*  holder.card.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Intent intent = new Intent(context, Entrepreneur_Profile.class);
                                                intent.putExtra("email", object.getEmail());
                                                intent.putExtra("name", object.getFullname());

                                                intent.putExtra("pic", object.getProfile());
                                                intent.putExtra("occ", object.getOccupation());
                                                context.startActivity(intent);
                                            }
                                        }
         );

*/
    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }
}
