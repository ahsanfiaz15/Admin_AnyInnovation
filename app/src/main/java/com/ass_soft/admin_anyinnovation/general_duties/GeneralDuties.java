package com.ass_soft.admin_anyinnovation.general_duties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.general_duties.update_subscriptions.GeneralDuties_UpdateHubSubscription;
import com.ass_soft.admin_anyinnovation.general_duties.update_subscriptions.GeneralDuties_UpdateSubscriptions;

public class GeneralDuties extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties);
    }

    public void updateSubscriptionPlan(View view) {
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
       // Intent intent = new Intent(GeneralDuties.this, GeneralDuties_UpdateSubscriptions.class);
        //startActivity(intent);
    }

    public void academy(View view) {

        Intent intent = new Intent(GeneralDuties.this, GeneralDuties_Academy.class);
        startActivity(intent);
    }

    public void summary(View view) {
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(GeneralDuties.this, GeneralDuties_Summary.class);
        //startActivity(intent);
    }
}