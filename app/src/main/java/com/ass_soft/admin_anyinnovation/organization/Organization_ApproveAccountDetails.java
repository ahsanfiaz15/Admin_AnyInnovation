package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.investor.InvestorApproveAccountDetails;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Organization_ApproveAccountDetails extends AppCompatActivity {
   OrganizationObject object;
   Gson gson;
   String status="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__approve_account_details);
        gson=new Gson();
        object=gson.fromJson(getIntent().getStringExtra("data"),OrganizationObject.class);
        initViews();
    }
    private void block() {
        final ProgressDialog progressDialog = new ProgressDialog(Organization_ApproveAccountDetails.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/update_org_status.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();

                        } catch (Exception e) {

                            //  e.printStackTrace();
                            //  Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        //   Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("status",status);
                params.put("email",object.getEmail());




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
    private void initViews() {
        TextView tv_name=findViewById(R.id.tv_organization_name);
        TextView tv_pro_name=findViewById(R.id.tv_program_name);
        TextView tv_duration=findViewById(R.id.tv_program_duration);
        TextView tv_c_name=findViewById(R.id.tv_contact_person_name);
        TextView tv_c_email=findViewById(R.id.tv_email_address);
        TextView tv_c_phone=findViewById(R.id.tv_phone_number);
        TextView tv_total=findViewById(R.id.tv_total_participants);
        tv_c_email.setText(object.getEmail_person());
        tv_c_name.setText(object.getName_person());
        tv_c_phone.setText(object.getPhone());
        tv_duration.setText(object.getDuration());
        tv_name.setText(object.getName());
        tv_pro_name.setText(object.getPro_name());
        tv_total.setText("0");
        Button block=findViewById(R.id.btn_reject);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="0";
                block();
            }
        });
        Button ap=findViewById(R.id.btn_approve);
        ap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="2";
                block();
            }
        });
        Button pen =findViewById(R.id.btn_pending);
        pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status="1";
                block();
            }
        });

    }
}