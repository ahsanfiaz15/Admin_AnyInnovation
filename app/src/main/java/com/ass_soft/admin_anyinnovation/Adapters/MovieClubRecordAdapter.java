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
import com.ass_soft.admin_anyinnovation.Objects.MovieClubRecord;
import com.ass_soft.admin_anyinnovation.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieClubRecordAdapter extends RecyclerView.Adapter<MovieClubRecordAdapter.ViewHolder> {
        Context c;
        ArrayList<MovieClubRecord> list;
public MovieClubRecordAdapter(Context c, ArrayList<MovieClubRecord> list){
        this.c=c;
        this.list=list;
        }

@NonNull
@Override
public MovieClubRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies_added, null);
        MovieClubRecordAdapter.ViewHolder rcv = new MovieClubRecordAdapter.ViewHolder(c, layoutView);
        return rcv;
        }

@Override
public void onBindViewHolder(@NonNull MovieClubRecordAdapter.ViewHolder holder, int position) {
        MovieClubRecord p=list.get(position);
        holder.name.setText(p.getName());
        holder.date.setText(p.getDate());
        holder.des.setText(p.getDescription());
        Glide
        .with(c)
        .load(Config.url+p.getPic())
        .centerCrop()
        .into(holder.img);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        delete(p.getId(),p);
        }
        });
        }

private void delete(String id, MovieClubRecord p) {
final ProgressDialog progressDialog = new ProgressDialog(c);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/delete_movie_club_record.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
        new Response.Listener<String>() {
@Override
public void onResponse(String response) {
        progressDialog.dismiss();

        //  e.printStackTrace();
       // Toast.makeText(c, response, Toast.LENGTH_LONG).show();
        list.remove(p);
        notifyDataSetChanged();
        }

        },
        new Response.ErrorListener() {
@Override
public void onErrorResponse(VolleyError error) {
        progressDialog.dismiss();

        // error.printStackTrace();
        Toast.makeText(c, error.getMessage(), Toast.LENGTH_LONG).show();

        }
        }
        ) {
@Override
protected Map<String, String> getParams()
        {
        Map<String, String> params = new HashMap<>();
        // the POST parameters:
        params.put("id",id);



        return params;
        }
        };

        Volley.newRequestQueue(c).add(postRequest);

        }

@Override
public int getItemCount() {
        return list.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView name,des,date;
    View itemView;
    Context c;
    public ImageView img; ImageButton cancel;
    public  ViewHolder(Context c, View itemView) {
        super(itemView);
        this.c = c;

        img = (ImageView) itemView.findViewById(R.id.img_movie_poster);
        this.itemView=itemView;
        date=(TextView)itemView.findViewById(R.id.tv_release_date);
        des=(TextView)itemView.findViewById(R.id.tv_movie_description2);
        name=(TextView)itemView.findViewById(R.id.tv_movie_name) ;
        cancel=(ImageButton) itemView.findViewById(R.id.btn_cancle);
    }
}
}

