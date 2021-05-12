package com.ass_soft.admin_anyinnovation.general_duties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_BookClub;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_IdeaEvaluation;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_InnovationCircle;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_MasterClasses;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_MovieClub;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_PremiumCourses;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_ShortCourses;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_YILFellowship;

public class GeneralDuties_Academy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__academy);
    }

    public void shortCourses(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_ShortCourses.class);
        startActivity(intent);
    }

    public void premiumCourses(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_PremiumCourses.class);
        startActivity(intent);
    }

    public void bookClub(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_BookClub.class);
        startActivity(intent);
    }

    public void movieClub(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_MovieClub.class);
        startActivity(intent);
    }

    public void masterClasses(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_MasterClasses.class);
        startActivity(intent);
    }

    public void innovationCircle(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_InnovationCircle.class);
        startActivity(intent);
    }

    public void yilFellowship(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_YILFellowship.class);
        startActivity(intent);
    }

    public void ideaEvaluation(View view) {

        Intent intent = new Intent(GeneralDuties_Academy.this, GeneralDuties_IdeaEvaluation.class);
        startActivity(intent);
    }
}