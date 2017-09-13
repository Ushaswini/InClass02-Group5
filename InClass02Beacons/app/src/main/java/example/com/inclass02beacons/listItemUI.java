package example.com.inclass02beacons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nitin on 6/8/2017.
 */

public class listItemUI extends LinearLayout {

    //public TextView tilte, price, artist, date;
    public TextView productName, price, discount, region;
    public ImageView productimg;

    public listItemUI(Context context) {
        super(context);
        inflateXML(context);
    }

    private void inflateXML(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listitem,this);
        this.productName = (TextView)findViewById(R.id.tvProductName);
        this.discount = (TextView)findViewById(R.id.tvDiscount);
        this.price = (TextView)findViewById(R.id.tvPrice);
        this.region = (TextView)findViewById(R.id.tvRegion);
        this.productimg = (ImageView)findViewById(R.id.imageView);
        //this.storyImage = (ImageView)findViewById(R.id.imageViewThumbnail);
    }
}
