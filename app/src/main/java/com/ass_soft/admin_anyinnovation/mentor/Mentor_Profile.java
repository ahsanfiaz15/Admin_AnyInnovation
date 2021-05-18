package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

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
        startActivity(intent);
    }

    public void viewEntrepreneurs(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Mentor_MentpredEntrepreneurs.class);
        startActivity(intent);
    }
}