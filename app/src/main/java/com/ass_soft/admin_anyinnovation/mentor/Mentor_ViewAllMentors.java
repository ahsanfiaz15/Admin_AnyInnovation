package com.ass_soft.admin_anyinnovation.mentor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.MentorAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.MentorAdapter1;
import com.ass_soft.admin_anyinnovation.Adapters.MentorNewAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.Objects.MentorPoints;
import com.ass_soft.admin_anyinnovation.Objects.PointSystem;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mentor_ViewAllMentors extends AppCompatActivity implements SearchView.OnQueryTextListener {

    CardView itemViewMentor;
   ArrayList<MentorObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor__view_all_mentors);
        list=new ArrayList<>();
        getAllMentors();
        SearchView searchView=findViewById(R.id.searchView_search_mentor);
        searchView.setOnQueryTextListener(this);
    }

    private void getAllMentors() {
        final ProgressDialog progressDialog = new ProgressDialog(Mentor_ViewAllMentors.this);

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
                                JSONObject object2=array11.getJSONObject(1);
                                MentorPoints mentorPoints=g.fromJson(object2.toString(),MentorPoints.class);

                                String n_comments=mentorPoints.getN_comments();

                                double n_c=Double.parseDouble(n_comments);
                                String n_brain=mentorPoints.getN_brain();

                                double n_b=Double.parseDouble(n_brain);

                                String comments=system.getMessage();
                                double c=Double.parseDouble(comments);
                                String brain=system.getBraindate();
                                double b=Double.parseDouble(brain);
                                double amount_c=n_c*c;
                                double amount_b=n_b*b;
                                double total=amount_b+amount_c;
                                m.setPoints(""+total);


                                list.add(m);



                            }

                            RecyclerView RV=findViewById(R.id.RV);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Mentor_ViewAllMentors.this,LinearLayoutManager.VERTICAL,false);
                            MentorAdapter1 adapter=new MentorAdapter1(Mentor_ViewAllMentors.this,list,1);
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        setData(s.toLowerCase());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setData(s.toLowerCase());
        return false;
    }

    private void setData(String toLowerCase) {
     ArrayList<MentorObject> temp=new ArrayList<>();
     for(int i=0;i<list.size();i++){
         MentorObject m=list.get(i);
         if(m.getFullname().toLowerCase().contains(toLowerCase)){
             temp.add(m);
         }
     }
        RecyclerView RV=findViewById(R.id.RV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Mentor_ViewAllMentors.this,LinearLayoutManager.VERTICAL,false);
        MentorAdapter1 adapter=new MentorAdapter1(Mentor_ViewAllMentors.this,list,1);
        RV.setLayoutManager(linearLayoutManager);
        RV.setAdapter(adapter);

    }
}