package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;

public class Mentor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);
    }

    public void mentorNotifications(View view) {

        Intent intent = new Intent(Mentor.this,Mentor_Notifications.class);
        startActivity(intent);
    }

    public void viewAllMentor(View view) {

        Intent intent = new Intent(Mentor.this,Mentor_SearchMentors.class);
        startActivity(intent);
    }

    public void managePayoutRequest(View view) {

        Intent intent = new Intent(Mentor.this,Mentor_ManagePayoutRequest.class);
        startActivity(intent);
    }

    public void approveMentorAccount(View view) {

        Intent intent = new Intent(Mentor.this,Mentor_ApproveAccount.class);
        startActivity(intent);
    }

    public void editCompensationPlan(View view) {

        Intent intent = new Intent(Mentor.this,Mentor_EditCompensationPlan.class);
        startActivity(intent);
    }
}