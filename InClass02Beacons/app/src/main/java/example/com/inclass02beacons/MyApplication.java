package example.com.inclass02beacons;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.util.Log;
//import com.estimote.sdk.BeaconManager;


import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.mgmtsdk.feature.settings.api.Beacon;

import java.util.List;
import java.util.UUID;

//import io.reactivex.Notification;

/**
 * Created by Nitin on 9/12/2017.
 */

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(1,0);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            /*@Override
            public void onEnteredRegion(BeaconRegion region, List<Beacon> beacons) {
                Log.d("demo",region.toString());Log.d("demo",region.getIdentifier());
            }*/

            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<com.estimote.coresdk.recognition.packets.Beacon> beacons) {
                Log.d("demo",beaconRegion.toString());Log.d("demo",beaconRegion.getIdentifier());
            }

            @Override
            public void onExitedRegion(BeaconRegion region) {
                // could add an "exit" notification too if you want (-:
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region grocery",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 15212, 31506));
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region lifestyle",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 48071, 25324));
                beaconManager.startMonitoring(new BeaconRegion(
                        "monitored region produce",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 26535, 44799));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            notification = new Notification.Builder(getApplicationContext(),"")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        //notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }


}
