package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mentor_Profile extends AppCompatActivity {
  MentorObject m;
  Gson g;
    CircleImageView profile_mentor;
    TextView tv_mentor_name,
            tv_city,
            tv_gender,
            tv_age,
            tv_total_reviews,
            tv_work_title,
            tv_occupation,
            tv_skills,
            tv_biography;
    RatingBar mentor_rating;
    Button reject,approve,pending;
    String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__profile);
        g=new Gson();
        m=g.fromJson(getIntent().getStringExtra("data"),MentorObject.class);
        initView();
     Button   reject=findViewById(R.id.btn_block_account);
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="0";
                update_status();
            }
        });
    }
    private void update_status() {
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_Profile.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/update_mentor_status.php";
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
                params.put("status",status);
                params.put("email",m.getEmail());




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
    private void initView() {

        profile_mentor = findViewById(R.id.profile_entrepreneur);
        tv_mentor_name = findViewById(R.id.tv_mentor_name);
        tv_city = findViewById(R.id.tv_city);
        tv_age = findViewById(R.id.tv_age);
        tv_gender = findViewById(R.id.tv_gender);
        TextView tv_toal_points=findViewById(R.id.tv_total_gem_points);
        tv_toal_points.setText(m.getPoints());

        tv_work_title = findViewById(R.id.tv_work_title);
        tv_occupation = findViewById(R.id.tv_occupation);
        tv_skills = findViewById(R.id.tv_skills);
        tv_biography = findViewById(R.id.tv_biography);
        mentor_rating = findViewById(R.id.mentor_rating);

        tv_mentor_name.setText(m.getFullname());
        String industry=m.getIndustry();
        String url = Config.url+m.getPic();
        Glide.with(this).load(url).into(profile_mentor);
        tv_occupation.setText(m.getOccupation());
        tv_skills.setText(m.getSkills());
        if(m.getRating().length()>0) {
            mentor_rating.setRating(Float.parseFloat(m.getRating()));
        }
        tv_biography.setText(m.getBio());
        tv_gender.setText(m.getGender());
        tv_city.setText(m.getCity());
        String dob=m.getDob();
        tv_age.setText(Config.getAge(dob));
    }

    public void messageMentor(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Chat.class);
        intent.putExtra("rec_email",m.getEmail());
        intent.putExtra("profile",m.getPic());
        intent.putExtra("name",m.getFullname());
        startActivity(intent);
    }

    public void mentorWallet(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Mentor_Wallet.class);
        intent.putExtra("email",m.getEmail());
        intent.putExtra("name",m.getFullname());
        startActivity(intent);
    }

    public void viewEntrepreneurs(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Mentor_MentpredEntrepreneurs.class);
        intent.putExtra("email",m.getEmail());
        startActivity(intent);
    }
}