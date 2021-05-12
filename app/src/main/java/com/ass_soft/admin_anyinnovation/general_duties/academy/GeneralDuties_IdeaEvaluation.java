package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.IdeaAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.PremiumCourseAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Idea;
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralDuties_IdeaEvaluation extends AppCompatActivity implements IdeaAdapter.Download, SearchView.OnQueryTextListener {
    CardView itemViewEntrepreneur;
    ArrayList<Idea> list;
    IdeaAdapter adapter;
    RecyclerView RV;
    SearchView searchView;
   ArrayList<Idea> old;
    static  Idea idea;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__idea_evaluation);
        searchView=findViewById(R.id.searchView_search_entrepreneur);
        list=new ArrayList<>();
        old=new ArrayList<>();
        adapter=new IdeaAdapter(GeneralDuties_IdeaEvaluation.this,list,this);
        RV=findViewById(R.id.RV);
       LinearLayoutManager lLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

       RV.setLayoutManager(lLayout);
       RV.setAdapter(adapter);
        getIdea();
        searchView.setOnQueryTextListener(this);
      /*  itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GeneralDuties_IdeaEvaluation.this,GeneralDuties_IdeaEvaluationViewIdea.class);
                startActivity(intent);
            }
        });
    */
    }

    private void getIdea() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_IdeaEvaluation.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_data.php";
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
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){
                                    list.add(gson.fromJson(array.getString(i).toString(), Idea.class));
                                    adapter.notifyDataSetChanged();
                                }
                                old=list;



                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        // error.printStackTrace();
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("tb","idea");



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }


    @Override
    public void download(Idea idea1) {
        idea=idea1;
        Intent intent = new Intent(GeneralDuties_IdeaEvaluation.this,GeneralDuties_IdeaEvaluationViewIdea.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
       ArrayList<Idea> temp=new ArrayList<>();
       for(int i=0;i<list.size();i++) {
           Idea t = list.get(i);
           String name = t.getName().toLowerCase();
           String pro = t.getPro_name().toLowerCase();
           s = s.toLowerCase();
           if (name.contains(s) || pro.contains(s)) {
               temp.add(t);
           }
       }
           adapter=new IdeaAdapter(GeneralDuties_IdeaEvaluation.this,temp,this);
           RV.setAdapter(adapter);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        ArrayList<Idea> temp=new ArrayList<>();
        for(int i=0;i<list.size();i++) {
            Idea t = list.get(i);
            String name = t.getName().toLowerCase();
            String pro = t.getPro_name().toLowerCase();
            s = s.toLowerCase();
            if (name.contains(s) || pro.contains(s)) {
                temp.add(t);
            }
        }
            adapter=new IdeaAdapter(GeneralDuties_IdeaEvaluation.this,temp,this);
            RV.setAdapter(adapter);

       return false;
    }
}