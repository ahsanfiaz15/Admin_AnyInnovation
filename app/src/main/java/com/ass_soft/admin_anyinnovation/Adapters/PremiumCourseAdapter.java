package com.ass_soft.admin_anyinnovation.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Login;
import com.ass_soft.admin_anyinnovation.MainActivity;
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PremiumCourseAdapter extends RecyclerView.Adapter<PremiumCourseAdapter.ViewHolder> {
    Context c;
    ArrayList<PremiumCourse> list;
    public PremiumCourseAdapter(Context c,ArrayList<PremiumCourse> list){
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_course, null);
        ViewHolder rcv = new ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
           PremiumCourse p=list.get(position);
           holder.name.setText(p.getTutor_name());
          holder.price.setText(p.getPrice()+"$");
          holder.title.setText(p.getTitle());
          holder.total.setText(p.getQuantity());
        Glide.with(c).load(p.getUrl()).into(holder.img);
           holder.cancel.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   delete(p.getId(),p);
               }
           });
           holder.con.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {

               }
           });
    }

    private void delete(String id,PremiumCourse p) {
        final ProgressDialog progressDialog = new ProgressDialog(c);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/delete_course.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                            //  e.printStackTrace();
                            Toast.makeText(c, response, Toast.LENGTH_LONG).show();
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
        public TextView name,price,title,total;
        View itemView;
        Context c;
        public ImageView img; ImageButton cancel;
   ConstraintLayout con;
        public  ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
             con=itemView.findViewById(R.id.item_video_list);
            img = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            this.itemView=itemView;
            title=(TextView)itemView.findViewById(R.id.tv_course_title);
            total=(TextView)itemView.findViewById(R.id.tv_total_videos);
            name=(TextView)itemView.findViewById(R.id.tv_tutor_name);
            price=(TextView)itemView.findViewById(R.id.textView40);
            cancel=(ImageButton) itemView.findViewById(R.id.btn_cancle);
        }
    }
}
