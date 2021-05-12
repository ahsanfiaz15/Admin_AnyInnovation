package com.ass_soft.admin_anyinnovation.investor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.R;

public class InvestorProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_profile);
    }

    public void messageInvestor(View view) {

        Intent intent = new Intent(InvestorProfile.this, Chat.class);
        startActivity(intent);
    }

    public void viewEntrepreneurs(View view) {

        Intent intent = new Intent(InvestorProfile.this, InvestorSponsoredEntrepreneurs.class);
        startActivity(intent);
    }
}