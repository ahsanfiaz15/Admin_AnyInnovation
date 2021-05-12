package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.MainActivity;
import com.ass_soft.admin_anyinnovation.R;

public class Organization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
    }

    public void organizationNotifications(View view) {

        Intent intent = new Intent(Organization.this, Organization_Notifications.class);
        startActivity(intent);
    }

    public void skipMembers(View view) {

        Intent intent = new Intent(Organization.this, Organization_SkipMembers.class);
        startActivity(intent);
    }

    public void approveAcount(View view) {

        Intent intent = new Intent(Organization.this, Organization_ApproveAccount.class);
        startActivity(intent);
    }

    public void viewOrganizations(View view) {

        Intent intent = new Intent(Organization.this, Organization_ViewAllOrganizations.class);
        startActivity(intent);
    }
}