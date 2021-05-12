package com.ass_soft.admin_anyinnovation.general_duties.update_subscriptions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;

public class GeneralDuties_UpdateSubscriptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__update_subscriptions);
    }

    public void updateHubSubscription(View view) {

        Intent intent = new Intent(GeneralDuties_UpdateSubscriptions.this, GeneralDuties_UpdateHubSubscription.class);
        startActivity(intent);
    }

    public void updateAcademySubscription(View view) {

        Intent intent = new Intent(GeneralDuties_UpdateSubscriptions.this, GeneralDuties_UpdateAcademySubscription.class);
        startActivity(intent);
    }
}