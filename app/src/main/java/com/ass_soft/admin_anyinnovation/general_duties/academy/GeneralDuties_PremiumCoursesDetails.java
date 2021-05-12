package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.BuyProductAdpter;
import com.ass_soft.admin_anyinnovation.Adapters.PremiumCourseAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.ShortCourseAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.BuyProduct;
import com.ass_soft.admin_anyinnovation.Objects.ShortCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralDuties_PremiumCoursesDetails extends AppCompatActivity {

    CardView itemViewEntrepreneur;
    RecyclerView RV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__premium_courses_details);
       RV=findViewById(R.id.RV);
        getBuyCourse();
      /*  itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GeneralDuties_PremiumCoursesDetails.this, Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });*/
    }

    private void getBuyCourse() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_PremiumCoursesDetails.this);

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
                                double total=0;
                                ArrayList<BuyProduct> list=new ArrayList<>();
                                Gson gson = new Gson();
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){
                                    BuyProduct buy=gson.fromJson(array.getString(i).toString(), BuyProduct.class);
                                    total=total+Double.parseDouble(buy.getPrice());
                                    list.add(buy);

                                }
                                TextView tv=findViewById(R.id.tv_total_revenue_generated);
                                tv.setText(""+total);

                                TextView course=findViewById(R.id.tv_total_courses_sold);
                                course.setText(""+list.size());
                                LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_PremiumCoursesDetails.this, LinearLayoutManager.VERTICAL, false);
                                BuyProductAdpter adapter=new BuyProductAdpter(GeneralDuties_PremiumCoursesDetails.this,list);
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
                params.put("tb","buy_premium");



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
}