package com.ass_soft.admin_anyinnovation.general_duties;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ass_soft.admin_anyinnovation.R;

import java.util.Calendar;

public class GeneralDuties_Summary extends AppCompatActivity {

    TextView tv_date,tv_select_date,tv_month,tv_select_month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__summary);

        tv_select_date = findViewById(R.id.tv_select_date);
        tv_date = findViewById(R.id.tv_date);
        tv_select_month = findViewById(R.id.tv_select_month);
        tv_month = findViewById(R.id.tv_month);

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GeneralDuties_Summary.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        tv_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        tv_select_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }

}