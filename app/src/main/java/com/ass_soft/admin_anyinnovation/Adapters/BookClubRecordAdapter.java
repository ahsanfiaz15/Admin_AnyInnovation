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
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookClubRecordAdapter extends RecyclerView.Adapter<BookClubRecordAdapter.ViewHolder> {
    Context c;
    ArrayList<BookClubRecord> list;
    public BookClubRecordAdapter(Context c,ArrayList<BookClubRecord> list){
        this.c=c;
        this.list=list;
    }

    @NonNull
    @Override
    public BookClubRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_books_added, null);
        BookClubRecordAdapter.ViewHolder rcv = new BookClubRecordAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull BookClubRecordAdapter.ViewHolder holder, int position) {
        BookClubRecord p=list.get(position);
         holder.title.setText(p.getTitle());
         holder.name.setText(p.getAuthor());
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

    private void delete(String id,BookClubRecord p) {
        final ProgressDialog progressDialog = new ProgressDialog(c);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/delete_book_club_record.php";
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
        public TextView name,des,title;
        View itemView;
        Context c;
        public ImageView img; ImageButton cancel;
        public  ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;

            img = (ImageView) itemView.findViewById(R.id.img_book_poster);
            this.itemView=itemView;
            title=(TextView)itemView.findViewById(R.id.tv_book_title);
            des=(TextView)itemView.findViewById(R.id.tv_book_description);
            name=(TextView)itemView.findViewById(R.id.tv_author_name) ;
            cancel=(ImageButton) itemView.findViewById(R.id.btn_cancle);
        }
    }
}
