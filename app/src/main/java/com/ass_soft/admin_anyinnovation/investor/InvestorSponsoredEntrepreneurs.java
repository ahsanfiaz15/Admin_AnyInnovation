package com.ass_soft.admin_anyinnovation.investor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;

public class InvestorSponsoredEntrepreneurs extends AppCompatActivity {

    CardView itemViewEntrepreneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_sponsored_entrepreneurs);

        itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InvestorSponsoredEntrepreneurs.this, Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });
    }
}