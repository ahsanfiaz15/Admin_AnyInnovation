package com.ass_soft.admin_anyinnovation.investor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.Entrepreneur_list;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvestorSponsoredEntrepreneurs extends AppCompatActivity {

    CardView itemViewEntrepreneur;
  String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_sponsored_entrepreneurs);
            email=getIntent().getStringExtra("email");
       /* itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InvestorSponsoredEntrepreneurs.this, Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });*/
        getEnt();
    }

    private void getEnt() {
        final ProgressDialog progressDialog = new ProgressDialog(InvestorSponsoredEntrepreneurs.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/investor_get_ent.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            if(response.startsWith("Invalid")){

                            }
                            else {

                                Gson gson = new Gson();
                              ArrayList<com.ass_soft.admin_anyinnovation.Objects.Entrepreneur>  list=new ArrayList<>();
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){

                                    com.ass_soft.admin_anyinnovation.Objects.Entrepreneur mentor=gson.fromJson(array.getString(i).toString(),com.ass_soft.admin_anyinnovation.Objects.Entrepreneur.class);



                                    list.add(mentor);

                                }
                                RecyclerView RV=findViewById(R.id.RV);
                                LinearLayoutManager lLayout = new LinearLayoutManager(InvestorSponsoredEntrepreneurs.this, LinearLayoutManager.VERTICAL, false);
                                Entrepreneur_list adapter=new Entrepreneur_list(InvestorSponsoredEntrepreneurs.this,list,1);
                                RV.setLayoutManager(lLayout);
                                RV.setAdapter(adapter);



                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

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