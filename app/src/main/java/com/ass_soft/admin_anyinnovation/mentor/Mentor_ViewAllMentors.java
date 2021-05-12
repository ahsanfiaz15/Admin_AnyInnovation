package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;

public class Mentor_ViewAllMentors extends AppCompatActivity {

    CardView itemViewMentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__view_all_mentors);

        itemViewMentor = findViewById(R.id.itemViewMentor);

        itemViewMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mentor_ViewAllMentors.this,Mentor_Profile.class);
                startActivity(intent);
            }
        });
    }
}