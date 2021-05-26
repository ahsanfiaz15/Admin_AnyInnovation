package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.Entrepreneur_list;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Entrepreneur;
import com.ass_soft.admin_anyinnovation.Objects.EntrepreneurObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentor_MentpredEntrepreneurs extends AppCompatActivity {
    CardView itemViewEntrepreneur;
   String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__mentpred_entrepreneurs);
        email=getIntent().getStringExtra("email");
        getEnt();
    }

    private void getEnt() {
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_MentpredEntrepreneurs.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/ment_get_ent.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            if(response.startsWith("Invalid")){

                            }
                            else {
                                ArrayList<Entrepreneur> list=new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){
                                    list.add(gson.fromJson(array.getString(i).toString(), Entrepreneur.class));

                                }
                                RecyclerView RV=findViewById(R.id.RV);
                                LinearLayoutManager lLayout = new LinearLayoutManager(Mentor_MentpredEntrepreneurs.this, LinearLayoutManager.VERTICAL, false);
                                Entrepreneur_list adapter=new Entrepreneur_list(Mentor_MentpredEntrepreneurs.this,list,1);
                                RV.setLayoutManager(lLayout);
                                RV.setAdapter(adapter);



                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                            //   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("email",email);



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
}