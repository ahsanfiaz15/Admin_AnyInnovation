package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Mentor_EditCompensationPlan extends AppCompatActivity {
  EditText Message,Pro_mentor,Mentor,Branodate;
  String   message,pro_mentor,mentor,branodate;
  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__edit_compensation_plan);
         Message=findViewById(R.id.edt_comment_points);
         Pro_mentor=findViewById(R.id.edt_pro_mentor_1point_dollar);
         Mentor=findViewById(R.id.edt_mentor_1point_dollar);
         Branodate=findViewById(R.id.edt_braindate_points);
      Button update=findViewById(R.id.btn_update_compensation_plan);
      update.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              update();
          }
      });
    }

    private void update() {
   branodate=Branodate.getText().toString();
   message= Message.getText().toString();
   pro_mentor=Pro_mentor.getText().toString();
   mentor=Mentor.getText().toString();


        final ProgressDialog progressDialog = new ProgressDialog(Mentor_EditCompensationPlan.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/update_payment.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {


                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

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
                params.put("braindate",branodate);
                params.put("message",message);
                params.put("mentor",mentor);
                params.put("pro_mentor",pro_mentor);



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

  }
}