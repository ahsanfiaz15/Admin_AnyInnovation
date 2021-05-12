package com.ass_soft.admin_anyinnovation.investor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ass_soft.admin_anyinnovation.R;

public class InvestorMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_menu);
    }

    public void investorNotifications(View view) {

        Intent intent = new Intent(InvestorMenu.this, InvestorNotifications.class);
        startActivity(intent);
    }

    public void approveInvestorAccount(View view) {

        Intent intent = new Intent(InvestorMenu.this,InvestorApproveAccount.class);
        startActivity(intent);
    }

    public void viewAllInvestors(View view) {

        Intent intent = new Intent(InvestorMenu.this, InvestorViewAll.class);
        startActivity(intent);
    }
}