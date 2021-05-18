package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.OrganizationNewAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.PaymentRequestAdapter;
import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.Objects.PaymentRequest;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.organization.Organization_ApproveAccount;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentor_ManagePayoutRequest extends AppCompatActivity {

    Button btn_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__manage_payout_request);

   /*     btn_details = findViewById(R.id.btn_details);

        btn_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mentor_ManagePayoutRequest.this, Mentor_PayoutRequestDetails.class);
                startActivity(intent);
            }
        });

 */    getRequests();
    }

    private void getRequests() {
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_ManagePayoutRequest.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_payment_request.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Gson g=new Gson();
                            ArrayList<PaymentRequest> list=new ArrayList<>();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){

                                JSONArray array1=array.getJSONArray(i);
                                JSONObject object=array1.getJSONObject(0);

                               PaymentRequest request=g.fromJson(object.toString(),PaymentRequest.class);
                               MentorObject m=g.fromJson(array1.getJSONObject(1).toString(), MentorObject.class);
                               request.setMentor(m);
                               list.add(request);
                            }

                            RecyclerView RV=findViewById(R.id.RV);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Mentor_ManagePayoutRequest.this,LinearLayoutManager.VERTICAL,false);
                            PaymentRequestAdapter adapter=new PaymentRequestAdapter(Mentor_ManagePayoutRequest.this,list,1);
                            RV.setLayoutManager(linearLayoutManager);
                            RV.setAdapter(adapter);
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
                params.put("type","admin");




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
}