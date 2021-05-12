package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;

public class Mentor_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__profile);
    }

    public void messageMentor(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Chat.class);
        startActivity(intent);
    }

    public void mentorWallet(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Mentor_Wallet.class);
        startActivity(intent);
    }

    public void viewEntrepreneurs(View view) {

        Intent intent = new Intent(Mentor_Profile.this, Mentor_MentpredEntrepreneurs.class);
        startActivity(intent);
    }
}