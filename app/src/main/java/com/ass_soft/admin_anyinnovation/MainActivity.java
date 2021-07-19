package com.ass_soft.admin_anyinnovation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.Permissions;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties_Academy;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties_Summary;
import com.ass_soft.admin_anyinnovation.general_duties.update_subscriptions.GeneralDuties_UpdateSubscriptions;
import com.ass_soft.admin_anyinnovation.investor.InvestorMenu;
import com.ass_soft.admin_anyinnovation.mentor.Mentor;
import com.ass_soft.admin_anyinnovation.organization.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  Permissions permissions;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST =1 ;
    SharedPref sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissions=new Permissions(this);
        sh=new SharedPref(this);
        if(!permissions.checkPermissionWrite()){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }
      try {
          FirebaseApp.initializeApp(this);
          FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
              @Override
              public void onComplete(@NonNull Task<InstanceIdResult> task) {
                  if (!task.isSuccessful()) {
                      return;
                  }
                  String token = task.getResult().getToken();
                  // if (token.equals(sharedPref.get("token"))) {
                  sh.SaveSharedPref("token", token);
                  if (!sh.GetSharedPref("email").isEmpty()) {
                      update_details(token);
                  }
                  //   startSinchClient(sh.get(getApplicationContext(),"country")+""+sh.get(getApplicationContext(),"phone"));


              }
          });
      }catch (Exception e){
          Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
      }
    }   private void update_details(final String token) {
        String url = Config.url+"/update_token.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //     pDialog.hide();
//                         Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //   Toast.makeText(getApplicationContext(),"fail to load data",Toast.LENGTH_LONG).show();

                        // error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("email", sh.GetSharedPref("email"));
                params.put("type","admin");
                params.put("token",token);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Log.e("value", "Permission Granted, Now you can use local drive .");
                    // share(pack);

                } else {
                    // show_dialog("Please give permission");
                    // Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //   uploadVideo();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

                }
                else{
                    // show_dialog("Please give permission");
                }
                break;
        }
    }
    public void entrepreneur(View view) {
       // Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Entrepreneur.class);
        startActivity(intent);
    }

    public void mentor(View view) {
       // Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Mentor.class);
        startActivity(intent);
    }

    public void organizations(View view) {
        //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, Organization.class);
        startActivity(intent);
    }

    public void downloadEmails(View view) {
        //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, DownloadEmails.class);
        startActivity(intent);
    }

    public void academy(View view) {

        Intent intent = new Intent(MainActivity.this, GeneralDuties_Academy.class);
        startActivity(intent);
    }

    public void updateSubscriptionPlan(View view) {
        //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
         Intent intent = new Intent(MainActivity.this, GeneralDuties_UpdateSubscriptions.class);
        startActivity(intent);
    }

    public void summary(View view) {
        //Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, GeneralDuties_Summary.class);
        startActivity(intent);
    }

    public void investor(View view) {

        Intent intent = new Intent(MainActivity.this, InvestorMenu.class);
        startActivity(intent);
    }


    public void logout(View view) {
    }
}