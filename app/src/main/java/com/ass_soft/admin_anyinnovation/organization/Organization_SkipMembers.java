package com.ass_soft.admin_anyinnovation.organization;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.OrganizationAllAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.SkipMemberAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.OrganizationObject;
import com.ass_soft.admin_anyinnovation.Objects.SkipMembers;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.general_duties.academy.GeneralDuties_MovieClub;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Organization_SkipMembers extends AppCompatActivity {

    MaterialSpinner subscription_plans_spinner,organizations_spinner;
    EditText edt_hub_expiry_date,edt_academy_expiry_date;
    ArrayList<OrganizationObject> list;
    ArrayList<SkipMembers> slist;
    RecyclerView RV;
    SkipMemberAdapter adapter;
    String org_email="",select_sub="",hub_exp="",academy_exp="",new_email="",new_password="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization__skip_members);
        list=new ArrayList<>();
        slist=new ArrayList<>();
        RV=findViewById(R.id.RV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(Organization_SkipMembers.this,LinearLayoutManager.VERTICAL,false);
        adapter=new SkipMemberAdapter(Organization_SkipMembers.this,slist,1);
        RV.setLayoutManager(linearLayoutManager);
        RV.setAdapter(adapter);
        subscription_plans_spinner = findViewById(R.id.subscription_plans_spinner);
        organizations_spinner = findViewById(R.id.organizations_spinner);
        edt_hub_expiry_date = findViewById(R.id.edt_hub_expiry_date);
        edt_academy_expiry_date = findViewById(R.id.edt_academy_expiry_date);

        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_hub_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Organization_SkipMembers.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_hub_expiry_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        edt_academy_expiry_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Organization_SkipMembers.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_academy_expiry_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        subscription_plans_spinner.setItems("Explore","Grow","Accelerate","Academy");
        subscription_plans_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
       select_sub=item;
            }
        });
        getOrg();
        Button add=findViewById(R.id.btn_add_members);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
    getMembers();
    }

    private void add() {
        EditText Email=findViewById(R.id.edt_email);
        EditText Password=findViewById(R.id.edt_password);
        new_email=Email.getText().toString();
        hub_exp=edt_hub_expiry_date.getText().toString();
        academy_exp=edt_academy_expiry_date.getText().toString();
        new_password=Password.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(Organization_SkipMembers.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_skip_members.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(getApplicationContext(),"Successfully Added",Toast.LENGTH_LONG).show();
                           SkipMembers skipMembers=new SkipMembers();
                           skipMembers.setAcademy_exp(academy_exp);
                           skipMembers.setHub_expiry(hub_exp);
                           skipMembers.setNew_email(new_email);
                           skipMembers.setNew_password(new_password);
                           skipMembers.setId(response);
                           skipMembers.setSubscrip_plan(select_sub);
                           skipMembers.setOrg_email(org_email);
                           slist.add(skipMembers);
                           adapter.notifyDataSetChanged();
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
                params.put("subscrip_plan",select_sub);
                params.put("new_email",new_email);
                params.put("new_password",new_password);
                params.put("hub_exp",hub_exp);
                params.put("academy_exp",academy_exp);
                params.put("org_email",org_email);
                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void getOrg() {
        final ProgressDialog progressDialog = new ProgressDialog(Organization_SkipMembers.this);

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
                          ArrayList<String> list1=new ArrayList<>();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                OrganizationObject object1=g.fromJson(object.toString(), OrganizationObject.class);
                                list.add(object1);
                                list1.add(object1.getName());
                            }
                            organizations_spinner.setItems(list1);
                            organizations_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                                @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                                     org_email=list.get(position).getEmail();
                                }
                            });

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
    private void getMembers() {
        final ProgressDialog progressDialog = new ProgressDialog(Organization_SkipMembers.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_skip_members.php";
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

                                slist.add(g.fromJson(object.toString(),SkipMembers.class));
                               adapter.notifyDataSetChanged();
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
                params.put("type","admin");




                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

}