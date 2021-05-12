package com.ass_soft.admin_anyinnovation.entrepreneur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ass_soft.admin_anyinnovation.R;

public class Entrepreneur extends AppCompatActivity {

    CardView itemViewEntrepreneur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur);

        itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Entrepreneur.this,Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });
    }

    public void entrepreneurNotifications(View view) {

        Intent intent = new Intent(Entrepreneur.this, Entrepreneur_Notifications.class);
        startActivity(intent);
    }
}