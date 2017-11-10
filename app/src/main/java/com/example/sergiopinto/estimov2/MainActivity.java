package com.example.sergiopinto.estimov2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity  implements View.OnClickListener {


    public  AtomicInteger NotificationID = new AtomicInteger(0);

    private BeaconManager beaconManager;
    private BeaconRegion beaconRegion;
    private BeaconManager beaconManagerBlue;
    private BeaconRegion beaconRegionBlue;
    private BeaconManager beaconManagerGreen;
    private BeaconRegion beaconRegionGreen;
    private BeaconManager beaconManagerPurple;
    private BeaconRegion beaconRegionPurple;

    BeaconRegion region1;
    BeaconRegion region2;
    BeaconRegion region3;


    private int Unique_Integer_Number=0;
        TextView DispNumber ;
        TextView ListText;
        private Button btnRefresh;
      //  Estimote estimote = new Estimote();

        private static final Map<String, List<String>> PLACES_BY_BEACONS;

        // TODO: replace "<major>:<minor>" strings to match your own beacons.
        static {
            Map<String, List<String>> placesByBeacons = new HashMap<>();
        /*placesByBeacons.put("22504:48827", new ArrayList<String>() {{
            add("Heavenly Sandwiches");
            // read as: "Heavenly Sandwiches" is closest
            // to the beacon with major 22504 and minor 48827
            add("Green & Green Salads");
            // "Green & Green Salads" is the next closest
            add("Mini Panini");
            // "Mini Panini" is the furthest away
        }});
        placesByBeacons.put("648:12", new ArrayList<String>() {{
            add("Mini Panini");
            add("Green & Green Salads");
            add("Heavenly Sandwiches");
        }});*/
            placesByBeacons.put("62761:34197", new ArrayList<String>() {{
                add("VIME_Estimote");
                add("Green");
                add("TEST");
            }});
            PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
        }



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            DispNumber = (TextView)findViewById(R.id.DispNumber);
            ListText = (TextView)findViewById(R.id.editText);
            btnRefresh = (Button)findViewById(R.id.refresh) ;
            SystemRequirementsChecker.checkWithDefaultDialogs(this);
            // btnRefresh.setOnClickListener(MainActivity.this);
            //refreshONCLICK();
           // estimote.create(getApplicationContext());

            initRegions();

        }

        @Override
        public void onClick(View v){
            beaconManager = new BeaconManager(getApplicationContext());

            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
             @Override
             public void onServiceReady() {
                 beaconManager.startMonitoring(new BeaconRegion(
                         "monitored region",
                         null, null, null)); //UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D")
             }
            });

            //monitoring
            beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
                @Override
                public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
                       showNotificationWeb(
                                " CLEVER ADVERTISING "+Unique_Integer_Number,
                                "ID " + BeaconDistanc(beacons.get(0).getRssi(),beacons.get(0).getMeasuredPower()),100);
                                // "open website");
                    }
                @Override
                public void onExitedRegion(BeaconRegion region) {
                    // could add an "exit" notification too if you want (-:
                }
            });

            //Ranging
            /*beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener(){
                @Override
                public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                    if (beacons.size() != 0) {
                        Beacon beacon = beacons.get(0);
                        showNotification("RANG FIND",Integer.toString(beacon.getMajor()),Integer.parseInt(beacon.getUniqueKey()));
                    }
                }
            });*/

        }

        public double BeaconDistanc(double RSSI, double TxPower){

            return  Math.pow(10d,(double)((TxPower-RSSI)/(10*2)));
        }

        @Override
        protected void onResume() {
            super.onResume();
            SystemRequirementsChecker.checkWithDefaultDialogs(this);
            btnRefresh.setOnClickListener(MainActivity.this);
        //    estimote.setMonitoringListeners();
            searchOnResume();

        }

        @Override
        protected void onPause() {
            super.onPause();
            searchOnPause();
        }

        @Override
        protected void onStop(){
            super.onStop();
         //   beaconManager.stopRanging(beaconRegion);

        }


    public void searchOnResume(){

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(region1);
                beaconManager.startMonitoring(region2);
                beaconManager.startMonitoring(region3);

            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
                if (region.getIdentifier().equals("region 1")) {
                    /*showNotificationWeb(
                            " CLEVER ADV " + Unique_Integer_Number,
                            "ID " + BeaconDistanc(beacons.get(0).getRssi(), beacons.get(0).getMeasuredPower()), 100);*/
                    ListText.append(beacons.get(0).getUniqueKey()+"\n");
                } else if (region.getIdentifier().equals("region 2")) {
                   /* showNotificationInAPP(
                            " BEACON found " + Unique_Integer_Number,
                            "ID " + BeaconDistanc(beacons.get(0).getRssi(), beacons.get(0).getMeasuredPower()), 200);*/
                    ListText.append(beacons.get(0).getUniqueKey()+"\n");

                } else if (region.getIdentifier().equals("region 3")) {
                   /* showNotificationOutAPP(
                            " VISIT OUR APP " + Unique_Integer_Number,
                            "ID " + BeaconDistanc(beacons.get(0).getRssi(), beacons.get(0).getMeasuredPower()), 300);*/
                    ListText.append(beacons.get(0).getUniqueKey()+"\n");
                }

            }

            @Override
            public void onExitedRegion(BeaconRegion region) {

            }
        });
    }

    public void searchOnPause(){
        monitoringOnPause();
    }

    public void initRegions(){
        region1 = new BeaconRegion(
                "region 1",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),3232,3970);
        region2 = new BeaconRegion(
                "region 2",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),62761,34197);
        region3 = new BeaconRegion(
                "region 3",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),12661,57355);
    }


    public void monitoringOnPause() {

        beaconManagerPurple = new BeaconManager(getApplicationContext());

        beaconManagerPurple.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManagerPurple.startMonitoring(new BeaconRegion(
                        "PurpleBeacon",
                        null,null,null));
            }
        });

        //monitoring
        beaconManagerPurple.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
                showNotificationInAPP(
                        " FOUND ONE " ,
                        "ID " + BeaconDistanc(beacons.get(0).getRssi(), beacons.get(0).getMeasuredPower()), 300);
                // "open website");
            }

            @Override
            public void onExitedRegion(BeaconRegion region) {
                // could add an "exit" notification too if you want (-:
            }
        });

    }



    public void showNotificationOutAPP(String title, String message,int notificationID) {

      //URL
        //Uri webpage = Uri.parse("http://clever-advertising.com/");
        //Intent notifyIntent = new Intent(Intent.ACTION_VIEW, webpage);

      //Open MainActivity
        //Intent notifyIntent = new Intent(this, MainActivity.class);

      //Open another APP
        Intent notifyIntent = startNewActivity(getApplicationContext(),"io.voodoo.dune");

        //comun code
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);
    }

    public void showNotificationInAPP(String title, String message,int notificationID) {

        //URL
        //Uri webpage = Uri.parse("http://clever-advertising.com/");
        //Intent notifyIntent = new Intent(Intent.ACTION_VIEW, webpage);

        //Open MainActivity
        Intent notifyIntent = new Intent(this, MainActivity.class);

        //Open another APP
        //Intent notifyIntent = startNewActivity(getApplicationContext(),"io.voodoo.dune");

        //comun code
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);
    }

    public void showNotificationWeb(String title, String message,int notificationID) {

        //URL
        Uri webpage = Uri.parse("http://clever-advertising.com/");
        Intent notifyIntent = new Intent(Intent.ACTION_VIEW, webpage);

        //Open MainActivity
        //Intent notifyIntent = new Intent(this, MainActivity.class);

        //Open another APP
        //Intent notifyIntent = startNewActivity(getApplicationContext(),"io.voodoo.dune");

        //comun code
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, notification);

    }



    public Intent startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       return intent;
    }


    }