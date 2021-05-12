package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.R;

public class Mentor_PayoutRequestDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__payout_request_details);
    }

    public void messageMentor(View view) {

        Intent intent = new Intent(Mentor_PayoutRequestDetails.this, Chat.class);
        startActivity(intent);
    }

    public void mentorWallet(View view) {

        Intent intent = new Intent(Mentor_PayoutRequestDetails.this, Mentor_Wallet.class);
        startActivity(intent);
    }
}