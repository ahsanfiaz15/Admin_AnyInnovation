package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.FilePath;
import com.ass_soft.admin_anyinnovation.Helpers.Upload;
import com.ass_soft.admin_anyinnovation.R;
import com.bumptech.glide.Glide;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GeneralDuties_InnovationCircle extends AppCompatActivity {

    EditText edt_meetup_date,edt_meetup_time;
    EditText edt_venue,edt_event_name,edt_detail;
    private static final int REQ_CODE_PICK_IMAGEFILE =111 ;
    ImageView imageView;
    String path="",filename="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__innovation_circle);

        edt_meetup_date = findViewById(R.id.edt_meetup_date);
        edt_meetup_time = findViewById(R.id.edt_meetup_time);
        edt_event_name= findViewById(R.id.edt_event_name);
        edt_venue = findViewById(R.id.edt_event_venue);
        edt_detail=findViewById(R.id.edt_event_other_details);


        Calendar calendar = Calendar.getInstance();
        final  int year = calendar.get(Calendar.YEAR);
        final  int month = calendar.get(Calendar.MONTH);
        final  int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_meetup_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GeneralDuties_InnovationCircle.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        edt_meetup_date.setText(date);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

        edt_meetup_time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(GeneralDuties_InnovationCircle.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            String _24HourTime = selectedHour + ":" + selectedMinute;
                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                            Date _24HourDt = _24HourSDF.parse(_24HourTime);
                            edt_meetup_time.setText(_12HourSDF.format(_24HourDt));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, hour, minute, false);
                mTimePicker.show();

            }
        });

        imageView=findViewById(R.id.img_meetup_poster);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Choose Image File"), REQ_CODE_PICK_IMAGEFILE);

            }
        });


        Button post=findViewById(R.id.btn_post_event);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }
    private void add() {
        String name=edt_event_name.getText().toString();
        String venue=edt_venue.getText().toString();
        String detail=edt_detail.getText().toString();
        String date=edt_meetup_date.getText().toString();
        String time=edt_meetup_time.getText().toString();
        if(name.isEmpty()){
            edt_event_name.setError("Required");
            return;
        }
        if(venue.isEmpty()){
            edt_venue.setError("Required");
            return;
        }
        if(detail.isEmpty()){
            edt_detail.setError("Required");
            return;
        }
        if(date.isEmpty()){
            edt_meetup_date.setError("Required");
            return;
        }
        if(time.isEmpty()){
            edt_meetup_time.setError("Required");
            return;
        }


        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_InnovationCircle.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_innovation_cirlce.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        //  e.printStackTrace();
                        Toast.makeText(getApplicationContext(),"S", Toast.LENGTH_LONG).show();

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
                params.put("date",date);
                params.put("name",name);
                params.put("detail",detail);
                params.put("time",time);
                params.put("venue",venue);
                params.put("file","images/"+filename);


                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
    public void innovationCircleDetails(View view) {

        Intent intent = new Intent(GeneralDuties_InnovationCircle.this,GeneralDuties_InnovationCircleRegisteredList.class);
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_PICK_IMAGEFILE && resultCode == Activity.RESULT_OK) {
            if ((data != null) && (data.getData() != null)) {
                try{
                    Uri imageFileUri = data.getData();
               /* mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Uploading..");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setMax(100);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();*/
                    Glide
                            .with(this)
                            .load(imageFileUri)
                            .centerCrop()
                            .into(imageView);
                    Uri file = imageFileUri;

                    path = FilePath.getPath(GeneralDuties_InnovationCircle.this,file);
                    filename = path.substring(path.lastIndexOf("/") + 1);



                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageFileUri);
                    File f=createImageFile();
                    // File file = new File("path");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(f));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();
                    filename=f.getName();
                    path=f.getAbsolutePath();
///                    imageView.setImageBitmap(bitmap);
                    uploadVideo();

                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }




            }

        }


    }



    private File createImageFile()  {
        File image=null;
        try {
            String timeStamp =
                    new SimpleDateFormat("yyyyMMdd_HHmmss",
                            Locale.getDefault()).format(new Date());
            String imageFileName = "IMG_" + timeStamp + "_";
            //filename=imageFileName;
            File storageDir =
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            path = image.getAbsolutePath();
            return image;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return  image;
        }

    }
    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(GeneralDuties_InnovationCircle.this, "Uploading pic", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                //  textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                // textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadVideo(path,filename);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }

}