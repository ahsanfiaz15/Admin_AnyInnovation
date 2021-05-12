package com.ass_soft.admin_anyinnovation.investor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ass_soft.admin_anyinnovation.R;

public class InvestorViewAll extends AppCompatActivity {
    CardView itemViewInvestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_view_all);

        itemViewInvestor = findViewById(R.id.itemViewInvestor);

        itemViewInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InvestorViewAll.this, InvestorProfile.class);
                startActivity(intent);
            }
        });
    }
}