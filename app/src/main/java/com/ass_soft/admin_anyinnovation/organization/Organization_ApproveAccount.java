package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ass_soft.admin_anyinnovation.R;

public class Organization_ApproveAccount extends AppCompatActivity {

    Button btn_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__approve_account);

        btn_details = findViewById(R.id.btn_details);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Organization_ApproveAccount.this,Organization_ApproveAccountDetails.class);
                startActivity(intent);
            }
        });
    }
}