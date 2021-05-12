package com.ass_soft.admin_anyinnovation.investor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Investor;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.mentor.Mentor_ApproveAccountDetails;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class InvestorProfile extends AppCompatActivity {
  Investor m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);
        Gson g=new Gson();
        m=g.fromJson(getIntent().getStringExtra("data"),Investor.class);
        initView();
        Button block=findViewById(R.id.btn_block_account);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block();
            }
        });
    }

    private void block() {
        final ProgressDialog progressDialog = new ProgressDialog(InvestorProfile.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/update_investor_status.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();

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
                params.put("status","0");
                params.put("email",m.getEmail());




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void initView() {

      ImageView profile_mentor = findViewById(R.id.profile_investor);
      TextView tv_mentor_name = findViewById(R.id.tv_investor_name);
      TextView  tv_city = findViewById(R.id.tv_city);

       TextView tv_gender = findViewById(R.id.tv_gender);

      TextView  tv_type= findViewById(R.id.tv_investor_type);
      tv_type.setText(m.getType());
       TextView tv_country = findViewById(R.id.tv_country);
       tv_city.setText(m.getCity());
       tv_country.setText(m.getCountry());
       TextView email = findViewById(R.id.tv_investor_email);
       email.setText(m.getEmail());
       TextView range = findViewById(R.id.tv_investment_range);
       range.setText(m.getInvest_range());
       TextView why = findViewById(R.id.tv_why_me);
       why.setText(m.getWhy());
        TextView stage = findViewById(R.id.tv_stage);
        TextView  industry = findViewById(R.id.tv_industry);
        industry.setText(m.getIndustry());
     stage.setText(m.getStage());
        tv_mentor_name.setText(m.getFullname());


        String url = Config.url+m.getPic();
        Glide.with(this).load(url).into(profile_mentor);

        tv_gender.setText(m.getGender());
        tv_city.setText(m.getCity());

    }
    public void messageInvestor(View view) {

        Intent intent = new Intent(InvestorProfile.this, Chat.class);
      intent.putExtra("rec_email",m.getEmail());
        intent.putExtra("profile",m.getPic());
        intent.putExtra("name",m.getFullname());
        startActivity(intent);
    }

    public void viewEntrepreneurs(View view) {

        Intent intent = new Intent(InvestorProfile.this, InvestorSponsoredEntrepreneurs.class);
        intent.putExtra("email",m.getEmail());
        startActivity(intent);
    }
}