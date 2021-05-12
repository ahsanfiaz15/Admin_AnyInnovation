package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ass_soft.admin_anyinnovation.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Mentor_SearchMentors extends AppCompatActivity {

    MaterialSpinner specialized_skills_spinner, choose_industry_spinner, job_function_spinner, location_spinner;
    Button btn_search;
    CardView itemViewMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__search_mentors);
        initViews();

        itemViewMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mentor_SearchMentors.this,Mentor_Profile.class);
                startActivity(intent);
            }
        });

        specialized_skills_spinner.setItems("Skill 1","Skill 2","Skill 3","Skill 4");
        specialized_skills_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        choose_industry_spinner.setItems("Industry 1","Industry 2","Industry 3","Industry 4");
        choose_industry_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        job_function_spinner.setItems("Job Function 1","Job Function 2","Job Function 3","Job Function 4");
        job_function_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        location_spinner.setItems("Country1","Country2","Country3","Country4");
        location_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
    }

    private void initViews()
    {
        btn_search = findViewById(R.id.btn_search);
        specialized_skills_spinner = (MaterialSpinner)findViewById(R.id.specialized_skills_spinner);
        choose_industry_spinner = (MaterialSpinner)findViewById(R.id.choose_industry_spinner);
        job_function_spinner = (MaterialSpinner)findViewById(R.id.job_function_spinner);
        location_spinner = (MaterialSpinner)findViewById(R.id.location_spinner);
        itemViewMentor = findViewById(R.id.itemViewMentor);
    }

    public void mentorSearch(View view) {
    }


    public void viewAllMentors(View view) {

        Intent intent = new Intent(Mentor_SearchMentors.this,Mentor_ViewAllMentors.class);
        startActivity(intent);
    }
}