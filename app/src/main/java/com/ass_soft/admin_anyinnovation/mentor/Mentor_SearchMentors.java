package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.MentorAdapter1;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.Objects.MentorPoints;
import com.ass_soft.admin_anyinnovation.Objects.PointSystem;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentor_SearchMentors extends AppCompatActivity {

    MaterialSpinner choose_industry_spinner;
    EditText edt_worktitle_position,edt_city;
    Button btn_search;
    CardView itemViewMentor;
    String industry="",city="",work="";
    ArrayList<MentorObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__search_mentors);
        list=new ArrayList<>();
        initViews();

      /*  itemViewMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Mentor_SearchMentors.this,Mentor_Profile.class);
                startActivity(intent);
            }
        });
*/
        choose_industry_spinner.setItems(getResources().getStringArray(R.array.items));
        choose_industry_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
           industry=item;
            }
        });
    }

    private void initViews()
    {
        btn_search = findViewById(R.id.btn_search);
        choose_industry_spinner = (MaterialSpinner)findViewById(R.id.choose_industry_spinner);
    //    itemViewMentor = findViewById(R.id.itemViewMentor);
        edt_worktitle_position = findViewById(R.id.edt_worktitle_position);
        edt_city = findViewById(R.id.edt_city);
     }

    public void mentorSearch(View view) {
      city=edt_city.getText().toString();
      work=edt_worktitle_position.getText().toString();;
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_SearchMentors.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_all_mentors_with_points.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Gson g=new Gson();
                            JSONArray array=new JSONArray(response);
                            JSONArray array1=array.getJSONArray(0);
                            JSONObject object=array.getJSONObject(1);
                            PointSystem system=g.fromJson(object.toString(),PointSystem.class);
                            for(int i=0;i<array1.length();i++){
                                JSONArray array11=array1.getJSONArray(i);

                                JSONObject object1=array11.getJSONObject(0);
                                MentorObject m=g.fromJson(object1.toString(),MentorObject.class);
                               if(m.getIndustry().toLowerCase().contains(industry)||
                               m.getWorktitle().toLowerCase().contains(work)||
                                m.getCity().toLowerCase().contains(city)) {
                                   JSONObject object2 = array11.getJSONObject(1);
                                   MentorPoints mentorPoints = g.fromJson(object2.toString(), MentorPoints.class);

                                   String n_comments = mentorPoints.getN_comments();

                                   double n_c = Double.parseDouble(n_comments);
                                   String n_brain = mentorPoints.getN_brain();

                                   double n_b = Double.parseDouble(n_brain);

                                   String comments = system.getMessage();
                                   double c = Double.parseDouble(comments);
                                   String brain = system.getBraindate();
                                   double b = Double.parseDouble(brain);
                                   double amount_c = n_c * c;
                                   double amount_b = n_b * b;
                                   double total = amount_b + amount_c;
                                   m.setPoints("" + total);


                                   list.add(m);

                               }

                            }

                            RecyclerView RV=findViewById(R.id.RV);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Mentor_SearchMentors.this,LinearLayoutManager.VERTICAL,false);
                            MentorAdapter1 adapter=new MentorAdapter1(Mentor_SearchMentors.this,list,1);
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
                // the POST parameters
                params.put("id","admin");




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }


    public void viewAllMentors(View view) {

        Intent intent = new Intent(Mentor_SearchMentors.this,Mentor_ViewAllMentors.class);
        startActivity(intent);
    }
}