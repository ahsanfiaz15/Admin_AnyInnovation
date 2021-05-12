package com.ass_soft.admin_anyinnovation;

import androidx.appcompat.app.AppCompatActivity;

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
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.EditTextValueCheck;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edt_email, edt_password;
    Button btn_login, btn_forgot_password;
    String email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
   initViews();
    SharedPref sh=new SharedPref(this);
    if(!sh.GetSharedPref("email").isEmpty()){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    }
    private void initViews()
    {
        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);


    }
    public void login(View view) {
        EditTextValueCheck check = new EditTextValueCheck();
        if (check.hasValue(edt_email) && check.hasValue(edt_password)) {
            final ProgressDialog progressDialog = new ProgressDialog(Login.this);

            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            email = edt_email.getText().toString().trim();
            password = edt_password.getText().toString().trim();
            String url ="https://innovatorstower.com/apis/admin_login.php";
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                if(response.startsWith("Invalid")){
                                    edt_email.setError("Invalid Email or Password");
                                }
                                else {
                                    Gson gson = new Gson();
                                    JSONArray array = new JSONArray(response);
                                      //                                       Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                    // JSONArray json_stores1=json_stores.getJSONArray(0);

                                    SharedPref sh = new SharedPref(Login.this);
                                    sh.SaveSharedPref("email", email);
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
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
                             Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    // the POST parameters:
                    params.put("email",email);
                    params.put("password",password);


                    return params;
                }
            };

            Volley.newRequestQueue(getApplicationContext()).add(postRequest);



                   /* mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Entrepreneur_Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        SharedPref sh=new SharedPref(Entrepreneur_Login.this);
                                        sh.SaveSharedPref("type","enter");
                                        Intent intent = new Intent(Entrepreneur_Login.this, Entrepreneur_Main.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(Entrepreneur_Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    progressDialog.dismiss();
                                }
                            });
*/
        }
    }


    }
