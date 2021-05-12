package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Idea;
import com.ass_soft.admin_anyinnovation.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class GeneralDuties_IdeaEvaluationViewIdea extends AppCompatActivity {
     Idea idea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__idea_evaluation_view_idea);
        idea=GeneralDuties_IdeaEvaluation.idea;
        TextView tv_industry=findViewById(R.id.tv_industry);
        tv_industry.setText(idea.getIndustry());

        TextView tv_date=findViewById(R.id.tv_evaluated_date);
        tv_date.setText(idea.getDate());

        Button download_idea=findViewById(R.id.btn_download_submitted_idea);
        download_idea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadIdea();
            }
        });

        Button download_pro=findViewById(R.id.btn_download_submitted_prototype);
        download_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadPro();
            }
        });

    }

    private void downloadPro() {
        try {
            File file1 = new File(Environment.getExternalStorageDirectory(), "TOOP");
            if (!file1.exists()) {
                boolean c = file1.mkdirs();

            }
            String tem=idea.getFile2();
            String name = tem.substring(tem.indexOf("/")+1,tem.indexOf("."));
            String ext = tem.substring(tem.indexOf("."));
            if (name.length() < 3) {
                name = name + "TOOP";
            }
            File file = File.createTempFile(
                    name,  /* prefix */
                    ext,         /* suffix */
                    file1    /* directory */
            );
            new Async(Config.url + idea.getFile2(),file ).execute();
        }catch (Exception e){

        }
    }


    private void downloadIdea() {
        try {
            File file1 = new File(Environment.getExternalStorageDirectory(), "TOOP");
            if (!file1.exists()) {
                boolean c = file1.mkdirs();

            }
            String tem=idea.getFile1();
            String name = tem.substring(tem.indexOf("/")+1,tem.indexOf("."));
            String ext = tem.substring(tem.indexOf("."));
            if (name.length() < 3) {
                name = name + "TOOP";
            }
            File file = File.createTempFile(
                    name,  /* prefix */
                    ext,         /* suffix */
                    file1    /* directory */
            );
            new Async(Config.url + idea.getFile1(),file ).execute();
        }catch (Exception e){

        }
    }

    class Async extends AsyncTask<Void, Integer, String> {
        ProgressDialog mProgressDialog;
        String link="";
        File outputFile;
       public  Async(String link,File out){
           mProgressDialog = new ProgressDialog(GeneralDuties_IdeaEvaluationViewIdea.this);
           mProgressDialog.setMessage("Downloading File");
           mProgressDialog.setIndeterminate(false);
           mProgressDialog.setMax(100);
           mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
           mProgressDialog.setCancelable(true);

           this.link=link;
           outputFile=out;
       }

protected void onPreExecute() {
        super.onPreExecute();
    mProgressDialog.show();

        }

protected String doInBackground(Void...arg0) {
    try {
        URL u = new URL(link);
        URLConnection conn = u.openConnection();
        int contentLength = conn.getContentLength();

        DataInputStream stream = new DataInputStream(u.openStream());

        byte[] buffer = new byte[contentLength];
        stream.readFully(buffer);
        stream.close();

        DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
        fos.write(buffer);
        fos.flush();
        fos.close();
    } catch(FileNotFoundException e) {
        return e.getMessage(); // swallow a 404
    } catch (IOException e) {
        return e.getMessage(); // swallow a 404
    }
        return "Downloaded";
        }

protected void onProgressUpdate(Integer...a) {
        super.onProgressUpdate(a);
    mProgressDialog.setProgress(a[0]);
       }

protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();
    Toast.makeText(getApplicationContext(),"Downloaded",Toast.LENGTH_LONG).show();
        }
        }
}