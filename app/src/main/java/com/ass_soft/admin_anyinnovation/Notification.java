package com.ass_soft.admin_anyinnovation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.NotificationAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.SkipMemberAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.EntrepreneurObject;
import com.ass_soft.admin_anyinnovation.Objects.Investor;
import com.ass_soft.admin_anyinnovation.Objects.MentorObject;
import com.ass_soft.admin_anyinnovation.Objects.NotificationObject;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.Objects.SkipMembers;
import com.ass_soft.admin_anyinnovation.organization.Organization;
import com.ass_soft.admin_anyinnovation.organization.Organization_SkipMembers;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification extends AppCompatActivity {
    String email="",user_type="";
    ArrayList<NotificationObject> list;

    RecyclerView RV;
    NotificationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        email=getIntent().getStringExtra("email");
        list=new ArrayList<>();

        RV=findViewById(R.id.RV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Notification.this,LinearLayoutManager.VERTICAL,false);
        adapter=new NotificationAdapter(Notification.this,list,1);
        RV.setLayoutManager(linearLayoutManager);
        RV.setAdapter(adapter);
        getNot();
    }

    private void getNot() {
        final ProgressDialog progressDialog = new ProgressDialog(Notification.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_notification.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Gson g=new Gson();

                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONArray array1=array.getJSONArray(i);
                                JSONObject object=array1.getJSONObject(0);
                                NotificationObject obj=g.fromJson(object.toString(),NotificationObject.class);
                                if(obj.getUser_type().startsWith("ent")){
                                    EntrepreneurObject ent=g.fromJson(array1.getJSONObject(1).toString(),EntrepreneurObject.class);
                                    obj.setName(ent.getFullname());
                                    obj.setProfile(ent.getProfile());
                                }
                                if(obj.getUser_type().startsWith("ment")){
                                    MentorObject ent=g.fromJson(array1.getJSONObject(1).toString(), MentorObject.class);
                                    obj.setName(ent.getFullname());
                                    obj.setProfile(ent.getPic());
                                }
                                if(obj.getUser_type().startsWith("inv")){
                                    Investor ent=g.fromJson(array1.getJSONObject(1).toString(),Investor.class);
                                    obj.setName(ent.getFullname());
                                    obj.setProfile(ent.getPic());
                                }
                                if(obj.getUser_type().startsWith("org")){
                                    OrganizationObject ent=g.fromJson(array1.getJSONObject(1).toString(), OrganizationObject.class);
                                    obj.setName(ent.getName());
                                    obj.setProfile("");
                                }


                                list.add(obj);
                                adapter.notifyDataSetChanged();
                            }


                        }
                        catch (Exception e) {

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
}