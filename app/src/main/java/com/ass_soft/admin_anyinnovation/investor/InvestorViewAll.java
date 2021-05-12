package com.ass_soft.admin_anyinnovation.investor;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.InvestorAllAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Investor;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InvestorViewAll extends AppCompatActivity implements SearchView.OnQueryTextListener {
    CardView itemViewInvestor;
   ArrayList<Investor> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_view_all);
              list=new ArrayList<>();

              getList();
        SearchView searchView=findViewById(R.id.searchView_search_investor);
        searchView.setOnQueryTextListener(this);
      /*  itemViewInvestor = findViewById(R.id.itemViewInvestor);

        itemViewInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InvestorViewAll.this, InvestorProfile.class);
                startActivity(intent);
            }
        });*/
    }

    private void getList() {
        final ProgressDialog progressDialog = new ProgressDialog(InvestorViewAll.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_all_investor.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Gson g=new Gson();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                list.add(g.fromJson(object.toString(),Investor.class));
                            }

                            RecyclerView RV=findViewById(R.id.RV);
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(InvestorViewAll.this,LinearLayoutManager.VERTICAL,false);
                            InvestorAllAdapter adapter=new InvestorAllAdapter(InvestorViewAll.this,list,1);
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
        search(s.toLowerCase());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
       s=s.toLowerCase();
        search(s);
        return false;
    }
    public  void search(String s){
        ArrayList<Investor> temp=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Investor investor=list.get(i);
            if(investor.getFullname().toLowerCase().contains(s)){
                temp.add(investor);
            }
        }
        RecyclerView RV=findViewById(R.id.RV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(InvestorViewAll.this,LinearLayoutManager.VERTICAL,false);
        InvestorAllAdapter adapter=new InvestorAllAdapter(InvestorViewAll.this,temp,1);
        RV.setLayoutManager(linearLayoutManager);
        RV.setAdapter(adapter);
    }
}