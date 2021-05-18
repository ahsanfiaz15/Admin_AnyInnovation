package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ass_soft.admin_anyinnovation.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class Mentor_SearchMentors extends AppCompatActivity {

    MaterialSpinner choose_industry_spinner;
    EditText edt_worktitle_position,edt_city;
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

        choose_industry_spinner.setItems("Industry 1","Industry 2","Industry 3","Industry 4");
        choose_industry_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
    }

    private void initViews()
    {
        btn_search = findViewById(R.id.btn_search);
        choose_industry_spinner = (MaterialSpinner)findViewById(R.id.choose_industry_spinner);
        itemViewMentor = findViewById(R.id.itemViewMentor);
        edt_worktitle_position = findViewById(R.id.edt_worktitle_position);
        edt_city = findViewById(R.id.edt_city);
     }

    public void mentorSearch(View view) {
    }


    public void viewAllMentors(View view) {

        Intent intent = new Intent(Mentor_SearchMentors.this,Mentor_ViewAllMentors.class);
        startActivity(intent);
    }
}