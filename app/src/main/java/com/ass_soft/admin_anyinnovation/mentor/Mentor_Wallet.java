package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.PaymentRequestAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.Objects.MentorPoints;
import com.ass_soft.admin_anyinnovation.Objects.PaymentRequest;
import com.ass_soft.admin_anyinnovation.Objects.PointSystem;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentor_Wallet extends AppCompatActivity {
  String email="",name="";
  TextView tv_n_comments,tv_p_comments,tv_n_brain,tv_p_brain,tv_total,tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__wallet);

        email=getIntent().getStringExtra("email");
        name=getIntent().getStringExtra("name");

        getPoints();

    }

    private void getPoints() {
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_Wallet.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_mentor_points.php";
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

                                MentorPoints info=g.fromJson(object.toString(),MentorPoints.class);
                                PointSystem points=g.fromJson(array1.getJSONObject(1).toString(), PointSystem.class);

                                initView(info,points);
                                break;
                            }


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
                params.put("email",email);




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void initView(MentorPoints mentorPoints,PointSystem system) {
        tv_name=findViewById(R.id.tv_mentor_name);
        tv_n_brain=findViewById(R.id.tv_amount_of_braindates);
        tv_p_brain=findViewById(R.id.tv_amount_of_points_braindates);
        tv_n_comments=findViewById(R.id.tv_no_of_comments);
        tv_p_comments=findViewById(R.id.tv_amount_of_points_comments);
        tv_total=findViewById(R.id.tv_total_money);
        tv_name.setText(name);
        String n_comments=mentorPoints.getN_comments();
        tv_n_comments.setText(n_comments);
        double n_c=Double.parseDouble(n_comments);
        String n_brain=mentorPoints.getN_brain();
        tv_n_brain.setText(n_brain);
        double n_b=Double.parseDouble(n_brain);

        String comments=system.getMessage();
        double c=Double.parseDouble(comments);
        String brain=system.getBraindate();
        double b=Double.parseDouble(brain);

        double amount_c=n_c*c;
        tv_p_comments.setText(""+amount_c);
        double amount_b=n_b*b;
        tv_p_brain.setText(""+amount_b);
        double total=amount_b+amount_c;
        tv_total.setText(""+total);
    }
}