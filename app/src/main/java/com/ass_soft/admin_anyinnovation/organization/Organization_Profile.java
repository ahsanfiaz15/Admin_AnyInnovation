package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_MasterClasses;

import java.util.Calendar;

public class Organization_Profile extends AppCompatActivity {

    CardView itemViewEntrepreneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__profile);

        itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Organization_Profile.this, Entrepreneur_Profile.class);
                startActivity(intent);

            }
        });
    }

    public void messageOrganization(View view) {

        Intent intent = new Intent(Organization_Profile.this, Chat.class);
        startActivity(intent);
    }

    public void editOrganizationProfile(View view) {

        Intent intent = new Intent(Organization_Profile.this, Organization_EditProfile.class);
        startActivity(intent);
    }
}