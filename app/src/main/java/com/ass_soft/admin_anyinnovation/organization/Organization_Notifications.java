package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ass_soft.admin_anyinnovation.R;

public class Organization_Notifications extends AppCompatActivity {
   String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__notifications);
        email=getIntent().getStringExtra("email");
        getNot();
    }

    private void getNot() {

    }
}