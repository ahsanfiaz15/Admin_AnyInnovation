package com.ass_soft.admin_anyinnovation.entrepreneur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.MentorAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Mentor;
import com.ass_soft.admin_anyinnovation.R;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entrepreneur_ViewMentors extends AppCompatActivity {
   String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur__view_mentors);
       email=getIntent().getStringExtra("email");
     getMentor();
    }
    private void getMentor() {
        final ProgressDialog progressDialog = new ProgressDialog(Entrepreneur_ViewMentors.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/ent_get_mentor.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            if(response.startsWith("Invalid")){

                            }
                            else {

                                Gson gson = new Gson();
                             ArrayList<Mentor>   list=new ArrayList<>();
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){
                                    Mentor mentor=gson.fromJson(array.getString(i).toString(),Mentor.class);


                                    list.add(mentor);


                                }

                               RecyclerView view_mentor_RV=findViewById(R.id.RV);
                                LinearLayoutManager lLayout = new LinearLayoutManager(Entrepreneur_ViewMentors.this, LinearLayoutManager.VERTICAL, false);
                                MentorAdapter adapter=new MentorAdapter(Entrepreneur_ViewMentors.this,list,1);
                                view_mentor_RV.setLayoutManager(lLayout);
                                view_mentor_RV.setAdapter(adapter);


                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

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

}