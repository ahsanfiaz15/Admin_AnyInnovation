package com.ass_soft.admin_anyinnovation.general_duties.academy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Adapters.FellowshipAdapter;
import com.ass_soft.admin_anyinnovation.Adapters.MovieClubRecordAdapter;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Objects.Fellowship;
import com.ass_soft.admin_anyinnovation.Objects.MovieClubRecord;
import com.ass_soft.admin_anyinnovation.R;
import com.ass_soft.admin_anyinnovation.entrepreneur.Entrepreneur_Profile;
import com.ass_soft.admin_anyinnovation.general_duties.GeneralDuties_Academy;
import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneralDuties_YILFellowship extends AppCompatActivity implements FellowshipAdapter.Download {

    CardView itemViewEntrepreneur;
     String name="";
  static    Fellowship fell;
  String Q1,Q2,Q3,Q4,Q5,Q6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_duties__y_i_l_fellowship);
        getFellowship();
        Q1 = getString(R.string.Q1);
        Q2 = getString(R.string.Q2);
        Q3 = getString(R.string.Q3);
        Q4 = getString(R.string.Q4);
        Q5 = getString(R.string.Q5);
        Q6 = getString(R.string.Q6);
     /*   itemViewEntrepreneur = findViewById(R.id.itemViewEntrepreneur);

        itemViewEntrepreneur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GeneralDuties_YILFellowship.this, Entrepreneur_Profile.class);
                startActivity(intent);
            }
        });*/
    }

    private void getFellowship() {
        final ProgressDialog progressDialog = new ProgressDialog(GeneralDuties_YILFellowship.this);

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

                                Gson gson = new Gson();
                                ArrayList<Fellowship> list=new ArrayList<>();
                                JSONArray array = new JSONArray(response);
                                for(int i=array.length()-1;i>=0;i--){
                                    list.add(gson.fromJson(array.getString(i), Fellowship.class));
                                }
                                RecyclerView RV=findViewById(R.id.RV);
                                LinearLayoutManager lLayout = new LinearLayoutManager(GeneralDuties_YILFellowship.this, LinearLayoutManager.VERTICAL, false);
                              FellowshipAdapter  adapter=new FellowshipAdapter(GeneralDuties_YILFellowship.this,list,GeneralDuties_YILFellowship.this);
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
                params.put("tb","felowship");



                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }
    public   void download(Fellowship fellowship){
        fell=fellowship;
        try{
            File file1 = new File(Environment.getExternalStorageDirectory(), "TOOP");
            if (!file1.exists()) {
                boolean c = file1.mkdirs();

            }
          String  name=fellowship.getFullname();
            String ext = ".pdf";
            if (name.length() < 3) {
                name = name + "TOOP";
            }
            File file = File.createTempFile(
                    name,  /* prefix */
                    ext,         /* suffix */
                    file1    /* directory */
            );
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            addTitlePage(document);
            catFont = new Font();
            redFont = new Font();
            smallBold = new Font();
            subFont = new Font();
            Paragraph preface = new Paragraph();
            // We add one empty line
            addEmptyLine(preface, 1);
            // Lets write a big header
            preface.add(new Paragraph("Fellowship Summery", catFont));

            addEmptyLine(preface, 1);
            // Will create: Report generated by: _name, _date

            String info = "Title: " + "Fellowship Report"  + "\nReport generated by: " +fell.getFullname() ;

            preface.add(new Paragraph(
                    info, //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    smallBold));
            document.add(preface);
            addEmptyLine(preface, 3);
            String   data = "";
            //     addMetaData(document);
            data = data + " City: " + fell.getCity();
            data = data + "\n Country: " + fell.getCountry();
            data = data + "\n About:" + fell.getAbout();
            data = data + "\n Social: " + fell.getSocial();
            data = data + "\n" + Q1 +"\n" + fell.getQ1();
            data = data + "\n\n" + Q2 +"\n" + fell.getQ2();
            data = data + "\n\n" + Q3 +"\n" + fell.getQ3();
            data = data + "\n\n" + Q4 +"\n" + fell.getQ4();
            data = data + "\n\n" + Q5 +"\n" + fell.getQ5();
            data = data + "\n\n" + Q6 +"\n" + fell.getQ6();
            data = data + "\n\n\n\n";

            Paragraph p = new Paragraph(data);
            document.add(p);


    document.close();
    Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
   String s=e.getMessage();
   int k=0;
        }
    }
    static Font catFont, redFont, smallBold, subFont;
    private  void addTitlePage(Document document)  {


    }
    private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}