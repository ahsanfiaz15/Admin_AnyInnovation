package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;

public class Mentor_MentpredEntrepreneurs extends AppCompatActivity {

    CardView itemViewEntrepreneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__mentpred_entrepreneurs);

        itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mentor_MentpredEntrepreneurs.this, Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });
    }
}