package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ass_soft.admin_anyinnovation.Adapters.PremiumCourseAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Login;
import com.ass_soft.admin_anyinnovation.MainActivity;
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralDuties_PremiumCourses extends AppCompatActivity {
   EditText Link,Name,Price,Title;
   ArrayList<PremiumCourse> list;
   RecyclerView RV;
   PremiumCourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__premium_courses);
        Link=findViewById(R.id.edt_course_link);
        Name=findViewById(R.id.edt_tutor_name);
        Price=findViewById(R.id.edt_course_price);
        Title=findViewById(R.id.edt_course_title);
        Button add=findViewById(R.id.btn_add_course);
        list=new ArrayList<>();
        RV=findViewById(R.id.RV);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        getCourses();
    }

    private void getCourses() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_PremiumCourses.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_premium_courses.php";
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
                                 list.add(gson.fromJson(array.getString(i).toString(),PremiumCourse.class));
                                }

                                LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_PremiumCourses.this, LinearLayoutManager.VERTICAL, false);
                                 adapter=new PremiumCourseAdapter(GeneralDuties_PremiumCourses.this,list);
                                RV.setLayoutManager(lLayout);
                                RV.setAdapter(adapter);

                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                      //      Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("inid","");



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void add() {
    String name=Name.getText().toString();
    String price=Price.getText().toString();
    String link=Link.getText().toString();
    String title=Title.getText().toString();
    if(name.isEmpty()){
        Name.setError("Required");
        return;
    }
        if(link.isEmpty()){
            Link.setError("Required");
            return;
        }
        if(price.isEmpty()){
            Price.setError("Required");
            return;
        }
        if(title.isEmpty()){
            Title.setError("Required");
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_PremiumCourses.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_premium_course.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                            //  e.printStackTrace();
                       //    Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                        String last=response.substring(4);
                        String reply=response.substring(0,4);
                        int y=response.indexOf("|");
                        String quantity=response.substring(y+1);
                        int yy=response.indexOf("&");
                        String url=response.substring(yy+1);
                        Toast.makeText(getApplicationContext(),reply, Toast.LENGTH_LONG).show();


                        PremiumCourse p=new PremiumCourse();
                           p.setLink(link);
                           p.setTutor_name(name);
                           p.setPrice(price);
                           p.setId(last);
                           p.setUrl(url);
                           p.setTitle(title);
                           p.setQuantity(quantity);
                           list.add(p);
                           adapter.notifyDataSetChanged();
                      /*  LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_PremiumCourses.this, LinearLayoutManager.VERTICAL, false);
                        PremiumCourseAdapter adapter=new PremiumCourseAdapter(GeneralDuties_PremiumCourses.this,list);
                        RV.setLayoutManager(lLayout);
                        RV.setAdapter(adapter);*/
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
                params.put("link",link);
                params.put("name",name);
                params.put("price",price);
                params.put("title",title);

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);


    }


    public void premiumCoursesDetails(View view) {

        Intent intent = new Intent(GeneralDuties_PremiumCourses.this,GeneralDuties_PremiumCoursesDetails.class);

        startActivity(intent);
    }
}