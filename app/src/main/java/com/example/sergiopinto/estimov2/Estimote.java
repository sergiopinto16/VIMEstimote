package com.example.sergiopinto.estimov2;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.example.sergiopinto.estimov2.MainActivity;

/**
 * Created by SergioPinto on 22/10/2017.
 */

public class Estimote {


    private int a=1;
    private BeaconManager beaconManager;
    private BeaconRegion region;

    public ArrayList<BeaconManager> ListBeaconManager;
    private BeaconManager mBeaconManagerBlueberry;
    private BeaconManager mBeaconManagerIcyMarshmallow;
    private BeaconManager mBeaconManagerMint;

    public ArrayList<BeaconRegion> ListRegion;
    private BeaconRegion mRegionBlueberry;
    private BeaconRegion mRegionIcyMarshmallow;
    private BeaconRegion mRegionMint;

   public void create(Context MySingleton) {

       for (int i = 0; i < 3; i++) {
           initBeacon(MySingleton);
           initRegion(Integer.toString(i),null,null,null);
           grabAttributes();
           connectBeacon(ListBeaconManager.get(i),ListRegion.get(i));
           setMonitoringListeners(ListBeaconManager.get(i));
           //  showNotification("hello","Done!",4);
       }
   }



        private void initRegion(String identifier, UUID proximityUUID, Integer major, Integer minor){
        /*    mRegionBlueberry = new BeaconRegion("BlueberryRegion", null,null,null);
            mRegionIcyMarshmallow = new BeaconRegion("IcyMarshmallowRegion", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 2000, 6000);
            mRegionMint = new BeaconRegion("MintRegion", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 1000, 5000);
        */
            ListRegion.add(new BeaconRegion(identifier,proximityUUID,major,minor));
        }

        private void initBeacon(Context MySingleton) {
       /* mBeaconManagerBlueberry = new BeaconManager(MySingleton);
        mBeaconManagerIcyMarshmallow = new BeaconManager(MySingleton);
        mBeaconManagerMint = new BeaconManager(MySingleton);
         */
            ListBeaconManager.add(new BeaconManager(MySingleton));

        }

        private void connectBeacon(final BeaconManager mBeaconManager, final BeaconRegion mRegion) {
          /* mBeaconManagerMint.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    mBeaconManagerMint.startMonitoring(mRegionMint);
                }
            });
            mBeaconManagerBlueberry.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    mBeaconManagerBlueberry.startMonitoring(mRegionBlueberry);
                }
            });
            mBeaconManagerIcyMarshmallow.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    mBeaconManagerIcyMarshmallow.startMonitoring(mRegionIcyMarshmallow);
                }
            });*/
            mBeaconManager.connect(new BeaconManager.ServiceReadyCallback(){
              @Override
              public void onServiceReady(){
                  mBeaconManager.startMonitoring(mRegion);
              }
          });
    }

        public void setMonitoringListeners(BeaconManager mBeaconManager) {

          /*  mBeaconManagerIcyMarshmallow.setMonitoringListener(new BeaconManager.BeaconMonitoringListener(){
                @Override
                public void onEnteredRegion(BeaconRegion region, List<Beacon> list) {
                    showNotification("Beacon IcyMars..." , list.get(0).getMajor() + " : " + list.get(0).getMinor(), 2);
                }

                @Override
                public void onExitedRegion(BeaconRegion region) {
                   showNotification("Out of Region IcyMars..." , region.getMajor() + " : " + region.getMinor(),3);
                }
            });
            mBeaconManagerBlueberry.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
                @Override
                public void onEnteredRegion(BeaconRegion region, List<Beacon> list) {
                    showNotification("Beacon Blueberry " ,list.get(0).getMajor() + " : " + list.get(0).getMinor(),4);
                }

                @Override
                public void onExitedRegion(BeaconRegion region) {
                    showNotification("Out of Region Blueberry: " , region.getMajor() + " : " + region.getMinor(),5);
                }
            });
            mBeaconManagerMint.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
                @Override
                public void onEnteredRegion(BeaconRegion region, List<Beacon> list) {
                    showNotification("Beacon Mint " , list.get(0).getMajor() + " : " + list.get(0).getMinor(), 6);
                }

                @Override
                public void onExitedRegion(BeaconRegion region) {
                    showNotification("Out of Region Mint: "  , region.getMajor() + " : " + region.getMinor(),7);
                }
            });
            */
           mBeaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener(){
               @Override
               public void onEnteredRegion(BeaconRegion region, List<Beacon> list){
                    a++;
               }
               @Override
               public void onExitedRegion(BeaconRegion region){
                    a++;
               }
           });

        }


    private void grabAttributes() {
       /* COORDINATOR_LAYOUT = (CoordinatorLayout) findViewById(R.id.coordinator_layout_all);

        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);*/
    }


        //GETER E SETER

        public BeaconManager getBeaconManager() {
            return beaconManager;
        }

        public void setBeaconManager(BeaconManager beaconManager) {
            this.beaconManager = beaconManager;
        }

        public BeaconRegion getRegion() {
            return region;
        }

        public void setRegion(BeaconRegion region) {
            this.region = region;
        }



    }