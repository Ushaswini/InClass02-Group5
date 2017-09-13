package example.com.inclass02beacons;

/**
 * Created by Nitin on 6/6/2017.
 */

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;


/**
 * Created by Nitin on 6/4/2017.
 */

public class RequestParams {

    String methodType, baseUrl;
    HashMap<String,String> params = new HashMap<String, String>();
    //String token = "NMoRlR75-nga7S8jmMlCgWaYZWJKl7jeZUqMc3M9n4vuWgU5Qo0Jz41X1D-y0J08GCzdIjFCd4QiBqYsHCPaqx9gM0mvP2HvEpbczbGlhCs9ufaNrljSgcuEkMl0R54V2Hg53-mzAcHFmSReVZv5MbVRnMuVv28bM5i4QF_IwQSzH_x-RfkyiyhtYTbjefGx5yqjts_mC3Emt9idddXJiUmDzPe4n9QRm17gM5JvkDqvB4mf6iAsGZnS4GfJjeogGgIsxKkd5l8eejd6231dbSPGkYTWyKQbQTwMPgE21FBv0TFm-pi72yj4vXT_3Qp1Vu6opEdqqF8aEah3E2H6__lMn1xN5w8m4RQ-4ApLrE3H4LdJhXCIwoKrhxsLg1w9KIiSi35gQAwfXP52NdnrOerBEFZjByXTERyofL7PwPsGdF33dNBgD_yXYPG_nRj49VI_OilbLPDzhatxmpEJhq360pQgK8qJ_yWwvbwR7Do";

    public RequestParams(String methodType, String baseUrl) {
        this.methodType = methodType;
        this.baseUrl = baseUrl;
    }

    public HttpURLConnection createConnection() throws IOException {
        HttpURLConnection con = null;
        if(methodType == "GET") {
            URL url = new URL(getEncodedURL());
            con = (HttpURLConnection) url.openConnection();
            //con.setRequestProperty("Authorization","Bearer " + token);
            con.setRequestMethod(methodType);
        }else{
            URL url = new URL(this.baseUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(methodType);

            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(getEncodedParams());
            writer.flush();
        }
        return con;
    }

    public void addParams(String key, String value){
        this.params.put(key,value);
    }

    public String getEncodedParams(){
        StringBuilder sb = new StringBuilder();
        for (String key:params.keySet()) {
            try {
                String value = URLEncoder.encode(params.get(key),"UTF-8");
                if(sb.length() > 0){
                    sb.append("&");
                }
                sb.append(key + "=" + value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    public String getEncodedURL(){
        return this.baseUrl + "?" + this.getEncodedParams();
    }
}

