package com.ass_soft.admin_anyinnovation.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.Objects.SkipMembers;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.organization.Organization_ApproveAccountDetails;
import com.ass_soft.admin_anyinnovation.organization.Organization_SkipMembers;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkipMemberAdapter  extends RecyclerView.Adapter<SkipMemberAdapter.ViewHolder> {
    Context c;
    ArrayList<SkipMembers> list;
    int type;
    SharedPref sh;

    public SkipMemberAdapter(Context c, ArrayList<SkipMembers> list, int type) {
        this.c = c;
        this.list = list;
        this.type = type;
        sh = new SharedPref(c);
    }

    @NonNull
    @Override
    public SkipMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skip_members, null);
        SkipMemberAdapter.ViewHolder rcv = new SkipMemberAdapter.ViewHolder(c, layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull SkipMemberAdapter.ViewHolder holder, int position) {
        SkipMembers p = list.get(position);
        holder.email.setText(p.getOrg_email());
        holder.password.setText(p.getNew_password());
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(p);
            }
        });

    }

    private void cancel(SkipMembers p) {
        final ProgressDialog progressDialog = new ProgressDialog(c);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/delete_skip_member.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {



                                list.remove(p);
                                notifyDataSetChanged();



                        } catch (Exception e) {

                            //  e.printStackTrace();
                            //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("id",p.getId());




                return params;
            }
        };

        Volley.newRequestQueue(c).add(postRequest);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView email, password, indus2, indus3,
                name, speclized, job, city, points;
        View itemView;
        Context c;
        public ImageView pic, banner;
        ImageButton cancel;
        RatingBar ratingBar;

        public ViewHolder(Context c, View itemView) {
            super(itemView);
            this.c = c;
            cancel = itemView.findViewById(R.id.btn_cancle);

            this.itemView = itemView;

            email = itemView.findViewById(R.id.tv_email);
            password = itemView.findViewById(R.id.tv_password);


        }
    }
}

