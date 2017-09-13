package example.com.inclass02beacons;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.id.input;

/**
 * Created by Nitin on 6/13/2017.
 */

public class JSONUtil {

    static class classJSONMusicParser{
        static public ArrayList<Product> parse(String input){
            ArrayList<Product> trackList = new ArrayList<Product>();
            try {
                //JSONObject root = new JSONObject(input);
                JSONArray listJSONArray = new JSONArray(input);//root.getJSONArray("results");
                for(int i=0; i< listJSONArray.length(); i++){
                    JSONObject js = listJSONArray.getJSONObject(i);
                    Product product = new Product();
                    product.setName(js.getString("Name"));
                    product.setDiscountValue(js.getDouble("DiscountValue"));
                    product.setRegionId(js.getInt("RegionId"));
                    product.setPrice(js.getDouble("Price"));
                    product.setImageUrl(js.getString("Photo"));
                    product.setId(js.getInt("Id"));

                    trackList.add(product);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d("demo","Tracklist :" + trackList);
            return trackList;
        }
    }
}
