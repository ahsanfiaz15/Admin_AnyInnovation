package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ass_soft.admin_anyinnovation.R;

import java.util.Calendar;

public class Organization_EditProfile extends AppCompatActivity {

    EditText edt_payment_expiration_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__edit_profile);

        edt_payment_expiration_date = findViewById(R.id.edt_payment_expiration_date);

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_payment_expiration_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Organization_EditProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_payment_expiration_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });
    }
}