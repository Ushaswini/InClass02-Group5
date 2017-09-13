package example.com.inclass02beacons;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by Nitin on 6/13/2017.
 */

public class GetProductAsyncTask extends AsyncTask<RequestParams,Void,ArrayList<Product>> {

    //ProgressDialog progressDialog;
    ArrayList<Product> products;
    IAsyncTaskPassProduct iAsyncProduct;
        public GetProductAsyncTask(IAsyncTaskPassProduct  iProduct){
        this.iAsyncProduct = iProduct;
        }

    public interface IAsyncTaskPassProduct {
        void getArrayList(ArrayList<Product> productList);
        Context getContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*progressDialog = new ProgressDialog(iAsyncProduct.getContext());
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();*/
    }

    @Override
    protected ArrayList<Product> doInBackground(RequestParams... params) {
        StringBuilder sb = new StringBuilder();
        //InputStream in = null;
        products = new ArrayList<>();
        try {
            HttpURLConnection con = params[0].createConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //con.setUseCaches(false);
            String line = "";

            while ((line = reader.readLine()) != null){
                sb.append(line);
            }
            //BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            //in = con.getInputStream();
            Log.d("demo",sb.toString());
            //products = XMLUtil.ArticleSAXParser.articleParser(in);
            products = JSONUtil.classJSONMusicParser.parse(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    protected void onPostExecute(ArrayList<Product> productList) {
        super.onPostExecute(productList);
        iAsyncProduct.getArrayList(productList);
        //progressDialog.dismiss();
    }
}
