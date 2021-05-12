package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_MovieClub;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;

public class Organization_SkipMembers extends AppCompatActivity {

    MaterialSpinner subscription_plans_spinner,organizations_spinner;
    EditText edt_hub_expiry_date,edt_academy_expiry_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__skip_members);

        subscription_plans_spinner = findViewById(R.id.subscription_plans_spinner);
        organizations_spinner = findViewById(R.id.organizations_spinner);
        edt_hub_expiry_date = findViewById(R.id.edt_hub_expiry_date);
        edt_academy_expiry_date = findViewById(R.id.edt_academy_expiry_date);

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_hub_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Organization_SkipMembers.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_hub_expiry_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        edt_academy_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Organization_SkipMembers.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_academy_expiry_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        subscription_plans_spinner.setItems("Explore","Grow","Accelerate","Academy");
        subscription_plans_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });

        organizations_spinner.setItems("Organization1","Organization2","Organization3");
        organizations_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

            }
        });
    }
}