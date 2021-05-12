package com.ass_soft.admin_anyinnovation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ass_soft.admin_anyinnovation.Helpers.Permissions;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties;
import com.ass_soft.admin_anyinnovation.mentor.Mentor;
import com.ass_soft.admin_anyinnovation.organization.Organization;

public class MainActivity extends AppCompatActivity {
  Permissions permissions;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE_REQUEST =1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissions=new Permissions(this);
        if(!permissions.checkPermissionWrite()){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        }

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
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
       // Intent intent = new Intent(MainActivity.this, Entrepreneur.class);
        //startActivity(intent);
    }

    public void mentor(View view) {
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(MainActivity.this, Mentor.class);
        //startActivity(intent);
    }

    public void organizations(View view) {
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(MainActivity.this, Organization.class);
        //startActivity(intent);
    }

    public void generalDuties(View view) {

        Intent intent = new Intent(MainActivity.this, GeneralDuties.class);
        startActivity(intent);
    }

    public void downloadEmails(View view) {
        Toast.makeText(getApplicationContext(),"Under Development",Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(MainActivity.this, DownloadEmails.class);
        //startActivity(intent);
    }
}