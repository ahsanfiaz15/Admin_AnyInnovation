package com.ass_soft.admin_anyinnovation.entrepreneur;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Chat;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Login;
import com.ass_soft.admin_anyinnovation.MainActivity;
import com.ass_soft.admin_anyinnovation.Objects.EntScrip;
import com.ass_soft.admin_anyinnovation.Objects.EntrepreneurObject;
import com.ass_soft.admin_anyinnovation.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Entrepreneur_Profile extends AppCompatActivity {
   String email="";
   EntrepreneurObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur__profile);
        email=getIntent().getStringExtra("email");

        getProfile();
        Button block=findViewById(R.id.btn_block_account);
        block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                block();
            }
        });

        Button reward=findViewById(R.id.btn_reward_free_month);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_reward_month();
            }
        });
    }

    private void set_reward_month() {
        final ProgressDialog progressDialog = new ProgressDialog(Entrepreneur_Profile.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_sub.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Date date = new Date();
                SimpleDateFormat df  = new SimpleDateFormat("YYYY-MM-dd");
                Calendar c1 = Calendar.getInstance();
                String cdate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                c1.add(Calendar.DAY_OF_YEAR, 30);
                df = new SimpleDateFormat("yyyy-MM-dd");
                Date resultDate = c1.getTime();
                String     dueDate = df.format(resultDate);


                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("packge","Reward for month");
                params.put("email",email);
                params.put("date",cdate);
                params.put("type","reward");
                params.put("exp",dueDate);



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void block() {
        final ProgressDialog progressDialog = new ProgressDialog(Entrepreneur_Profile.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/block_user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();


                            Toast.makeText(getApplicationContext(), "Blocked", Toast.LENGTH_LONG).show();
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
                params.put("email",email);



                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void getProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(Entrepreneur_Profile.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/admin_get_ent_profile.php";
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
                            JSONArray array1=array.getJSONArray(0);
                             EntrepreneurObject entrepreneur=new EntrepreneurObject();
                             object=entrepreneur;
                            // for(int i=0;i<array.length();i++) {
                                    entrepreneur = gson.fromJson(array1.getJSONObject(0).toString(), EntrepreneurObject.class);

                              //  }
                                ImageView imageView=findViewById(R.id.profile_entrepreneur);
                                Glide
                                        .with(Entrepreneur_Profile.this)
                                        .load(Config.url+entrepreneur.getProfile())
                                        .centerCrop()
                                        .into(imageView);
                                TextView name=findViewById(R.id.tv_entrepreneur_name);
                                name.setText(entrepreneur.getFullname());
                                TextView city=findViewById(R.id.tv_city);
                                city.setText(entrepreneur.getCity());
                                TextView Email=findViewById(R.id.tv_entrepreneur_email);
                                Email.setText(entrepreneur.getEmail());
                                TextView gender=findViewById(R.id.tv_gender);
                                gender.setText(entrepreneur.getGender());
                               //   age.setText(entrepreneur.getAge());
                                TextView state=findViewById(R.id.tv_state);
                                state.setText(entrepreneur.getState());
                                TextView skils=findViewById(R.id.tv_skills);
                                skils.setText(entrepreneur.getSkills());
                                TextView address=findViewById(R.id.tv_address);
                                address.setText(entrepreneur.getAddress());
                                TextView country=findViewById(R.id.tv_country);
                                country.setText(entrepreneur.getCountry());
                                TextView languge=findViewById(R.id.tv_language);
                                languge.setText(entrepreneur.getLanguage());
                                TextView status=findViewById(R.id.tv_employment_status);
                                status.setText(entrepreneur.getStatus());
                                TextView experience=findViewById(R.id.tv_experience);
                                experience.setText(entrepreneur.getExperience());
                                TextView education=findViewById(R.id.tv_education);
                                education.setText(entrepreneur.getEducation());
                                TextView work=findViewById(R.id.tv_work_title);
                                work.setText(entrepreneur.getWorktitle());
                                TextView occup=findViewById(R.id.tv_occupation);
                                occup.setText(entrepreneur.getOccupation());
                                TextView bio=findViewById(R.id.tv_biography);
                                bio.setText(entrepreneur.getBio());
                                TextView des=findViewById(R.id.tv_3words);
                                des.setText(entrepreneur.getDescription());
                                String dob=entrepreneur.getDob();
                                String day=dob.substring(0,dob.indexOf("/"));
                                String month=dob.substring(dob.indexOf("/")+1,dob.lastIndexOf("/"));
                                String year=dob.substring(dob.lastIndexOf("/")+1);
                                int d=Integer.parseInt(day);
                                int m=Integer.parseInt(month);
                                int y=Integer.parseInt(year);
                                int a=getAge(y,m,d);
                            //    int aa=Integer.parseInt(a);
                               a=a+1;
                                TextView age=findViewById(R.id.tv_age);

                                age.setText(""+a);

                                TextView pro_name=findViewById(R.id.tv_project_name);
                                pro_name.setText(entrepreneur.getPro_name());

                                if(array1.get(1).equals(null)){
                                    //String s="ahsan";
                                  //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                }
                                else{
                                    EntScrip scrip= gson.fromJson(array1.getJSONObject(1).toString(),EntScrip.class);
                                    TextView tv_script=findViewById(R.id.tv_hub_expiry_date);
                                    tv_script.setText(scrip.getExp_date());
                                    TextView tv_pc=findViewById(R.id.tv_subscription_plan);
                                    tv_pc.setText(scrip.getPackge());

                                }
                                if(array1.get(2).equals(null)){
                                    //String s="ahsan";
                                    //  Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                                }
                                else{
                                    EntScrip scrip= gson.fromJson(array1.getJSONObject(2).toString(),EntScrip.class);
                                    TextView tv_script=findViewById(R.id.tv_academy_expiry_date);
                                    tv_script.setText(scrip.getExp_date());


                                }



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
                        // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

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

    public void messageEntrepreneur(View view) {
 // Toast.makeText(getApplicationContext(),"Underdevelopment",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Entrepreneur_Profile.this, Chat.class);
        intent.putExtra("name",object.getFullname());
        intent.putExtra("rec_email",object.getEmail());
        intent.putExtra("profile",object.getProfile());
        startActivity(intent);
    }

    public void viewMentors(View view) {
       // Toast.makeText(getApplicationContext(),"Underdevelopment",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Entrepreneur_Profile.this, Entrepreneur_ViewMentors.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }
    private int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
     //   String ageS = ageInt.toString();

        return ageInt;
    }
}