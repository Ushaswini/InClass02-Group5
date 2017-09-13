package example.com.inclass02beacons;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    private BeaconRegion regionGrocery,regionLifestyle,regionProduce;
    ArrayList<Product> productArrayList;
    LinearLayout container;
    ScrollView svmain;
    View itemView;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productArrayList = new ArrayList<>();
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        svmain = (ScrollView)findViewById(R.id.svmain);
        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setForegroundScanPeriod(1,0);
        regionGrocery= new BeaconRegion("ranged region grocery", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 15212, 31506);
        regionLifestyle= new BeaconRegion("ranged region lifestyle",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 48071, 25324);
        regionProduce= new BeaconRegion("ranged region produce",UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 26535, 44799);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                svmain.removeAllViews();
                container.removeAllViews();
                RequestParams params = new RequestParams("GET","http://inclass02-discountsapi.azurewebsites.net/api/Discounts");
                Log.d("Identifier",region.getIdentifier());
                if(region.getIdentifier() == "ranged region grocery"){
                    params.addParams("regionName","grocery");
                    Toast.makeText(MainActivity.this,"Grocery",Toast.LENGTH_LONG).show();
                }else if(region.getIdentifier() == "ranged region lifestyle"){
                    params.addParams("regionName","lifestyle");
                    Toast.makeText(MainActivity.this,"Lifestyle",Toast.LENGTH_LONG).show();
                }else if(region.getIdentifier() == "ranged region produce"){
                    params.addParams("regionName","produce");
                    Toast.makeText(MainActivity.this,"Produce",Toast.LENGTH_LONG).show();
                }
               // params.addParams("regionName",2 + "");
                Log.d("url",params.getEncodedURL());

                //params.addParams("limit",(seekBarMin + seekBar.getProgress()) + "");

                new GetProductAsyncTask(new GetProductAsyncTask.IAsyncTaskPassProduct() {
                    @Override
                    public void getArrayList(ArrayList<Product> productList) {
                        productArrayList = productList;
                        Log.d("demo", productArrayList.toString());
                        container.removeAllViews();
                        for (Product product: productArrayList) {
                            final listItemUI listItem = new listItemUI(MainActivity.this);
                            itemView = (View) listItem;
                            listItem.productName.setText(product.getName());
                            listItem.discount.setText(product.getDiscountValue() + "");
                            listItem.price.setText(product.getPrice() + "$");
                            listItem.region.setText(product.getRegionId() + "");

                            if(product.getImageUrl().length() != 0 && !product.getImageUrl().isEmpty()){
                                Picasso.with(MainActivity.this).load(product.getImageUrl()).into(listItem.productimg);
                                /*new DownloadImage(new DownloadImage.AsyncImage() {
                                    @Override
                                    public void getImage(Bitmap image) {
                                        //Log.d("img" , image.toString());
                                    }

                                    @Override
                                    public Context getContext() {
                                        return null;
                                    }
                                }).execute(product.getImageUrl());*/
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

                /*if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    List<String> places = null;//placesNearBeacon(nearestBeacon);
                    // TODO: update the UI here
                    Log.d("Airport", "Nearest places: " + places);
                }*/
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
                Log.d("demo","startRanging");
                try {
                    beaconManager.startRanging(regionLifestyle);
                    beaconManager.startRanging(regionLifestyle);
                    beaconManager.startRanging(regionProduce);
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

        super.onPause();
    }
}
