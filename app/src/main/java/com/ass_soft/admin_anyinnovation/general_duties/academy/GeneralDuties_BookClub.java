package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.BookClubRecordAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.PremiumCourseAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.FilePath;
import com.ass_soft.admin_anyinnovation.Helpers.Upload;
import com.ass_soft.admin_anyinnovation.Objects.BookClubRecord;
import com.ass_soft.admin_anyinnovation.Objects.PremiumCourse;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties_Summary;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GeneralDuties_BookClub extends AppCompatActivity {
    EditText edt_title,edt_name,edt_description,edt_link;
    private static final int REQ_CODE_PICK_IMAGEFILE =111 ;
    ImageView imageView;
    String path="",filename="";
    ArrayList<BookClubRecord> list;
    BookClubRecordAdapter adapter;
    RecyclerView RV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__book_club);
        edt_title = findViewById(R.id.edt_book_title);
        edt_description = findViewById(R.id.edt_book_description);
        edt_name= findViewById(R.id.edt_author_name);
        edt_link = findViewById(R.id.edt_book_download_link);
        imageView=findViewById(R.id.img_book_poster);
        list=new ArrayList<>();
        adapter=new BookClubRecordAdapter(this,list);
        RV=findViewById(R.id.RV);
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

        Button add=findViewById(R.id.btn_add_book);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
  getBooks();
    }
    private void getBooks() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_BookClub.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/get_book_club_record.php";
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
                                    list.add(gson.fromJson(array.getString(i).toString(),BookClubRecord.class));
                                }

                                LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_BookClub.this, LinearLayoutManager.VERTICAL, false);
                                adapter=new BookClubRecordAdapter(GeneralDuties_BookClub.this,list);
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
        String name=edt_name.getText().toString();
        String link=edt_link.getText().toString();
        String description=edt_description.getText().toString();
        String title=edt_title.getText().toString();

        if(name.isEmpty()){
            edt_name.setError("Required");
            return;
        }
        if(link.isEmpty()){
            edt_link.setError("Required");
            return;
        }
        if(description.isEmpty()){
            edt_description.setError("Required");
            return;
        }
        if(title.isEmpty()){
            edt_title.setError("Required");
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_BookClub.this);

        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String url = Config.url+ "/add_book_club_record.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //   Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();
                        String last=response.substring(4);
                        String reply=response.substring(0,4);
                        //Toast.makeText(getApplicationContext(),"Book Added Successfully", Toast.LENGTH_LONG).show();


                        BookClubRecord p=new BookClubRecord();
                        p.setLink(link);
                        p.setAuthor(name);
                       p.setDescription(description);
                        p.setId(last);
                        p.setTitle(title);
                        p.setPic("images/"+filename);
                        list.add(p);
                        adapter.notifyDataSetChanged();
                        //  e.printStackTrace();
                    //    Toast.makeText(getApplicationContext(),response, Toast.LENGTH_LONG).show();

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
                params.put("link",link);
                params.put("name",name);
                params.put("title",title);
                params.put("description",description);

                params.put("pic","images/"+filename);


                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }

    public void bookClubDetails(View view) {

        Intent intent = new Intent(GeneralDuties_BookClub.this, BookClub_RegisteredList.class);
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

                    path = FilePath.getPath(GeneralDuties_BookClub.this,file);
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
              //      Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
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
       //     Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return  image;
        }

    }
    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(GeneralDuties_BookClub.this, "Uploading pic", "Please wait...", false, false);
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