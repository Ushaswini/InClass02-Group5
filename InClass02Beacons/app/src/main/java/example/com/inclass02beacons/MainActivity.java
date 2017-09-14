package example.com.inclass02beacons;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.internal.Util;
import com.estimote.coresdk.service.BeaconManager;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager, beaconManagerMonitor;
    private BeaconRegion regionGrocery,regionLifestyle,regionProduce;
    ArrayList<Product> productArrayList;
    LinearLayout container;
    ScrollView svmain;
    View itemView;
    double dist, currDist;
    Beacon currBeacon, prevBeacon;
    int maj, min, currMin, currMaj;
    String closestUuid;
    Proximity proximity;
    long lastActiveTime;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productArrayList = new ArrayList<>();

        currMin = 0; min = 0;currDist = -1;
        currMaj = 0; maj = 0;//list.get(0).getMajor();
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        svmain = (ScrollView)findViewById(R.id.svmain);

            /*svmain.removeAllViews();
            RequestParams params = new RequestParams("GET", "http://inclass02-discountsapi.azurewebsites.net/api/Discounts");
            new GetProductAsyncTask(new GetProductAsyncTask.IAsyncTaskPassProduct() {
                @Override
                public void getArrayList(ArrayList<Product> productList) {
                    productArrayList = productList;
                    Log.d("demo", productArrayList.toString());

                    container.removeAllViews();
                    sortList();
                    for (Product product : productArrayList) {
                        final listItemUI listItem = new listItemUI(MainActivity.this);
                        itemView = (View) listItem;
                        listItem.productName.setText(product.getName());
                        listItem.discount.setText(product.getDiscountValue() + "");
                        listItem.price.setText(product.getPrice() + "$");
                        listItem.region.setText(getRegionName(product.getRegionId()));

                        if (product.getImageUrl().length() != 0 && !product.getImageUrl().isEmpty()) {
                            Picasso.with(MainActivity.this).load(product.getImageUrl()).into(listItem.productimg);
                        }
                        container.addView(itemView);
                    }
                }

                @Override
                public Context getContext() {
                    return null;
                }
            }).execute(params);
            svmain.addView(container);*/

        prevBeacon = null;
        currBeacon = null;
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(150,2000);

        regionGrocery= new BeaconRegion("ranged region grocery", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 15212, 31506);
        regionLifestyle= new BeaconRegion("ranged region lifestyle",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 48071, 25324);
        regionProduce= new BeaconRegion("ranged region produce",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 26535, 44799);

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {

            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                /*if(beaconRegion.getIdentifier() == "discount beacons") {
                    refreshActivity();
                    Log.d("hello", "exit region");
                }*/
            }
        });
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {



            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {

                Log.d("demo", list.size()+"");
              //  lastActiveTime=0;

               if (list.size()>0) {

                    for (Beacon beacon : list) {
                        proximity = RegionUtils.computeProximity(beacon);
                        if (proximity == Proximity.IMMEDIATE || proximity == Proximity.NEAR) {
                            lastActiveTime= (new Date()).getTime();
                            dist = RegionUtils.computeAccuracy(beacon);
                            Log.d("demo",lastActiveTime+"first");

                            if (dist > -1 && (dist < currDist || currBeacon == null)) {
                                currDist = dist;
                                currBeacon = beacon;

                                Log.d("CurrBeacon", currBeacon.getMajor() + "");
                                //Log.d("Beacons", "CurrBeacon : " + currBeacon.getMajor() + "  dist : " + RegionUtils.computeAccuracy(beacon) + "   currDist: " + currDist);
                            }
                            if (dist > -1 && (currBeacon.getMajor() == beacon.getMajor())) {
                                currDist = (currDist + dist) / 2;
                                Log.d("demo",lastActiveTime+"updated");


                            }
                        }
                        /*else if(proximity == Proximity.UNKNOWN){
                            Log.d("CurrBeacon","demo");
                            refreshActivity();
                        }*/
                    }
                    if (prevBeacon != null) {
                        maj = prevBeacon.getMajor();
                    }
                    /*if((currBeacon.getMajor() == maj) && currBeacon!=null)
                    {
                        currDist = RegionUtils.computeAccuracy(currBeacon);
                    }*/
                    Log.d("Beacons", "CurrBeacon : " + "  dist : " + dist + "   currDist: " + currDist);
                    if (currBeacon != null) {


                        if (currBeacon.getMajor() != maj) {
                            RequestParams params = new RequestParams("GET", "http://inclass02-discountsapi.azurewebsites.net/api/Discounts");
                            if (prevBeacon != null) {
                                Log.d("Identifier", "CurrBeacon : " + currBeacon.getMajor() + "PrevBeacon : " + prevBeacon.getMajor());
                            }
                            if (currBeacon.getMajor() == 15212) {
                                params.addParams("regionName", "grocery");
                                Toast.makeText(MainActivity.this, "Grocery", Toast.LENGTH_LONG).show();
                            } else if (currBeacon.getMajor() == 48071) {
                                params.addParams("regionName", "lifestyle");
                                Toast.makeText(MainActivity.this, "Lifestyle", Toast.LENGTH_LONG).show();
                            } else if (currBeacon.getMajor() == 26535) {
                                params.addParams("regionName", "produce");
                                Toast.makeText(MainActivity.this, "Produce", Toast.LENGTH_LONG).show();
                            }

                            svmain.removeAllViews();
                            new GetProductAsyncTask(new GetProductAsyncTask.IAsyncTaskPassProduct() {
                                @Override
                                public void getArrayList(ArrayList<Product> productList) {
                                    productArrayList = productList;
                                    Log.d("demo", productArrayList.toString());

                                    container.removeAllViews();
                                    for (Product product : productArrayList) {
                                        final listItemUI listItem = new listItemUI(MainActivity.this);
                                        itemView = (View) listItem;
                                        listItem.productName.setText(product.getName());
                                        listItem.discount.setText(product.getDiscountValue() + "");
                                        listItem.price.setText(product.getPrice() + "$");
                                        listItem.region.setText(getRegionName(product.getRegionId()));

                                        if (product.getImageUrl().length() != 0 && !product.getImageUrl().isEmpty()) {
                                            Picasso.with(MainActivity.this).load(product.getImageUrl()).into(listItem.productimg);
                                        }
                                        container.addView(itemView);
                                    }
                                }

                                @Override
                                public Context getContext() {
                                    return null;
                                }
                            }).execute(params);
                            svmain.addView(container);
                            maj = currBeacon.getMajor();
                        }
                    }
                }else{
                   if (((new Date()).getTime() - lastActiveTime) > 5300 ) {
                       Log.d("demo",lastActiveTime+"in ele");
                       Log.d("demo","inside else " + ((new Date()).getTime() - lastActiveTime));
                       refreshActivity();

                   }
                   lastActiveTime = (new Date()).getTime();
               }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(regionGrocery);
                    beaconManager.startRanging(regionLifestyle);
                    beaconManager.startRanging(regionProduce);
                    beaconManager.startMonitoring(new BeaconRegion(
                            "discount beacons",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
                }catch(Exception ex){
                    Log.d("Exception","Cannot start ranging");
                }

            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(regionGrocery);
        beaconManager.stopRanging(regionLifestyle);
        beaconManager.stopRanging(regionProduce);
        beaconManager.disconnect();

        super.onPause();
    }

    public void sortList(){

        Collections.sort(productArrayList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                //return Double.parseDouble(o1.getTrackPrice()) - Double.parseDouble(o2.getTrackPrice());

                if(o1.getRegionId()> o2.getRegionId())

                    return 1;
                else
                    return -1;
            }
        });


    }

    public String getRegionName(int i){
        switch(i){
            case 1: return "Produce";
            case 2: return "Grocery";
            case 3: return "Lifestyle";
        }
        return "";
    }

    public void refreshActivity(){
        svmain.removeAllViews();
        RequestParams params = new RequestParams("GET", "http://inclass02-discountsapi.azurewebsites.net/api/Discounts");
        new GetProductAsyncTask(new GetProductAsyncTask.IAsyncTaskPassProduct() {
            @Override
            public void getArrayList(ArrayList<Product> productList) {
                productArrayList = productList;
                Log.d("demo", productArrayList.toString());

                container.removeAllViews();
                sortList();
                for (Product product : productArrayList) {
                    final listItemUI listItem = new listItemUI(MainActivity.this);
                    itemView = (View) listItem;
                    listItem.productName.setText(product.getName());
                    listItem.discount.setText(product.getDiscountValue() + "");
                    listItem.price.setText(product.getPrice() + "$");
                    listItem.region.setText(getRegionName(product.getRegionId()));

                    if (product.getImageUrl().length() != 0 && !product.getImageUrl().isEmpty()) {
                        Picasso.with(MainActivity.this).load(product.getImageUrl()).into(listItem.productimg);
                    }
                    container.addView(itemView);
                }
            }

            @Override
            public Context getContext() {
                return null;
            }
        }).execute(params);
        svmain.addView(container);
    }
}
