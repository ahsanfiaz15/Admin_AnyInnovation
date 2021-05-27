package com.ass_soft.admin_anyinnovation;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ass_soft.admin_anyinnovation.Helpers.Config;
import com.ass_soft.admin_anyinnovation.Helpers.SharedPref;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class MyFirebaseMessaging extends FirebaseMessagingService {
    String name="";
    SharedPref sharedPref;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
         sharedPref=new SharedPref(this);
         sharedPref.SaveSharedPref("token", s);
         update_details(s);
         Log.e("NEW_TOKEN", s);
    }
    private void update_details(final String token) {
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
                params.put("email", sharedPref.GetSharedPref("email"));

                params.put("token",token);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);


    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        int chker=0;

        Map<String, String> map = remoteMessage.getData();
        String setting=map.get("setting");
       String title="",body="";
        if(setting==null||setting.isEmpty()){
            chker=0;
            body=remoteMessage.getNotification().getBody();
            title=remoteMessage.getNotification().getTitle();
        }
        else {
            chker = 1;
            title = map.get("title");
            body = map.get("message");
        }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                    .setSmallIcon(R.drawable.logo) // notification icon
                    .setContentTitle(title) // title for notification
                    .setContentText(body)// message for notification
                    // set alarm sound for notification
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                .bigText(body));

     /*   NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.icon_main)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentTitle("HOPE Quotes")
                .setContentText("New Quote Added")
                .setAutoCancel(true);
*/
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("default",
                        "App_Test",
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("Test");
                mNotificationManager.createNotificationChannel(channel);
            }
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(uri);
           Intent in=null;

                in = new Intent(getApplicationContext(), Splash.class);
               in.putExtra("chat", "1");

            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, in, PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(pendingIntent);
            Random random = new Random();
            int m = random.nextInt(1000-1) + 1;

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(m, mBuilder.build());

    }
}