package com.ass_soft.admin_anyinnovation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.ChatAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.Objects.ChatObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    RecyclerView RV;
    EditText edt_message;
    TextView tv_user_name;
    CircleImageView img_profile;
    ImageButton btn_send, btn_attach_file;
    String rec_email="",name="",profile="";
    String email="";
    SharedPref sh;
    ChatAdapter adapter;
    ArrayList<ChatObject> list;
    String data="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        rec_email=getIntent().getStringExtra("rec_email");
        name=getIntent().getStringExtra("name");
        profile=getIntent().getStringExtra("profile");
        sh=new SharedPref(this);
        email=sh.GetSharedPref("email");
        initViews();
        list=new ArrayList<>();
        LinearLayoutManager layout=new LinearLayoutManager(Chat.this, LinearLayoutManager.VERTICAL, false);
        adapter=new ChatAdapter(Chat.this,list,email);
        RV.setLayoutManager(layout);
        RV.setAdapter(adapter);
        getChat();
    }

    private void getChat() {
        final ProgressDialog progressDialog = new ProgressDialog(Chat.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_chat.php";
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
                                for(int i=0;i<array.length();i++){
                                    list.add(gson.fromJson(array.getString(i).toString(), ChatObject.class));
                                    adapter.notifyDataSetChanged();
                                }



                            }
                        } catch (Exception e) {

                            //  e.printStackTrace();
                            //                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                params.put("ent_email",email);
                params.put("men_email",rec_email);



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    private void initViews()
    {
        RV = findViewById(R.id.RV);
        edt_message = findViewById(R.id.edt_message);
        tv_user_name =findViewById(R.id.tv_user_name);
        img_profile = findViewById(R.id.img_profile);
        btn_send    = findViewById(R.id.btn_send);
        btn_attach_file = findViewById(R.id.btn_attach_file);
        tv_user_name.setText(name);
        String url = Config.url+profile;
        Glide.with(this).load(url).into(img_profile);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data=edt_message.getText().toString();
                if(data.isEmpty()){
                    edt_message.setError("Required");
                    return;
                }
                final ProgressDialog progressDialog = new ProgressDialog(Chat.this);

                progressDialog.setCancelable(false);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                String url = Config.url+ "/send_message.php";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {


                                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

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
                        params.put("s_email",email);
                        params.put("r_email",rec_email);
                        params.put("data",data);





                        return params;
                    }
                };

                Volley.newRequestQueue(getApplicationContext()).add(postRequest);

            }
        });
    }
}