package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.R;

public class Organization_ViewAllOrganizations extends AppCompatActivity {

    CardView itemViewOrganization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__view_all_organizations);

        itemViewOrganization = findViewById(R.id.itemViewOrganization);

        itemViewOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Organization_ViewAllOrganizations.this, Organization_Profile.class);
                startActivity(intent);
            }
        });


    }
}