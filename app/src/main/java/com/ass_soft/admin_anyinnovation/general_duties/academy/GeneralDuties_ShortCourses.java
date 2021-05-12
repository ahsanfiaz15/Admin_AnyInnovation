package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.PremiumCourseAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.ShortCourseAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.Objects.ShortCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralDuties_ShortCourses extends AppCompatActivity {
   EditText Link;
   ArrayList<ShortCourse> list;
   RecyclerView RV;
   ShortCourseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__short_courses);
       Link=findViewById(R.id.edt_video_link);
        Button add=findViewById(R.id.btn_add_video);
        list=new ArrayList<>();
        RV=findViewById(R.id.RV);
        LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_ShortCourses.this, LinearLayoutManager.VERTICAL, false);
        adapter=new ShortCourseAdapter(GeneralDuties_ShortCourses.this,list);
        RV.setLayoutManager(lLayout);
        RV.setAdapter(adapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        getCourses();

    }
    private void getCourses() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_ShortCourses.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_short_courses.php";
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
                                    list.add(gson.fromJson(array.getString(i).toString(),ShortCourse.class));
                                }

                                LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_ShortCourses.this, LinearLayoutManager.VERTICAL, false);
                                adapter=new ShortCourseAdapter(GeneralDuties_ShortCourses.this,list);
                                RV.setLayoutManager(lLayout);
                                RV.setAdapter(adapter);

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
                params.put("inid","");



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
    private void add() {
       String link=Link.getText().toString();
       if(link.isEmpty()){
           Link.setError("Required");
           return;
       }
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_ShortCourses.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_short_course.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        //  e.printStackTrace();
                         String last=response.substring(4);
                         int y=response.indexOf("&");
                         String title=response.substring(y+1);
                         String reply=response.substring(0,4);
                       // Toast.makeText(getApplicationContext(),reply, Toast.LENGTH_LONG).show();
                        ShortCourse p=new ShortCourse();
                        p.setLink(link);
                        p.setTitle(title);
                        p.setId(last);
                        list.add(p);
                       adapter.notifyDataSetChanged();

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
                params.put("link",link);



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

}