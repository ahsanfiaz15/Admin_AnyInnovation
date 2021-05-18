package com.ass_soft.admin_anyinnovation.organization;

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
import com.ass_soft.admin_anyinnovation.Adapters.OrganizationAllAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.OrganizationNewAdapter;
import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Organization_ViewAllOrganizations extends AppCompatActivity implements SearchView.OnQueryTextListener {

    CardView itemViewOrganization;
  ArrayList<OrganizationObject> list;

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__view_all_organizations);
        list=new ArrayList<>();
        getOrg();
        SearchView searchView=findViewById(R.id.searchView_search_organization);
        searchView.setOnQueryTextListener(this);
  }
    private void getOrg() {
        final ProgressDialog progressDialog = new ProgressDialog(Organization_ViewAllOrganizations.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_all_org.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Gson g=new Gson();
                            list=new ArrayList<>();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                list.add(g.fromJson(object.toString(),OrganizationObject.class));
                            }

                            RecyclerView RV=findViewById(R.id.RV);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Organization_ViewAllOrganizations.this,LinearLayoutManager.VERTICAL,false);
                            OrganizationAllAdapter adapter=new OrganizationAllAdapter(Organization_ViewAllOrganizations.this,list,1);
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

    private void setData(String s) {
    ArrayList<OrganizationObject > tem=new ArrayList<>();
    for(int i=0;i<list.size();i++){
        String name=list.get(i).getName();
        name=name.toLowerCase();
        if(name.contains(s)){
            tem.add(list.get(i));
        }
    }
        RecyclerView RV=findViewById(R.id.RV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Organization_ViewAllOrganizations.this,LinearLayoutManager.VERTICAL,false);
        OrganizationAllAdapter adapter=new OrganizationAllAdapter(Organization_ViewAllOrganizations.this,tem,1);
        RV.setLayoutManager(linearLayoutManager);
        RV.setAdapter(adapter);
  }
}