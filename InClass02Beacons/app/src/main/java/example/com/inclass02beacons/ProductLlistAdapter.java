package example.com.inclass02beacons;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nitin on 6/15/2017.
 */

public class ProductLlistAdapter extends ArrayAdapter<Product> {
    Context mContext;
    int mResource;
    List<Product> products;

    public ProductLlistAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.products= objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listitem,parent,false);
            holder= new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.tvProductName);
            holder.discount = (TextView) convertView.findViewById(R.id.tvDiscount);
            holder.price = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.region = (TextView) convertView.findViewById(R.id.tvRegion);
            convertView.setTag(holder);
        }

        Product p = products.get(position);
        holder= (ViewHolder) convertView.getTag();
        holder.name.setText(p.getName());
        holder.discount.setText("" + p.getDiscountValue());
        holder.price.setText( p.getPrice() + "$");
        holder.region.setText("" + p.getRegionId());

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView price;
        TextView region;
        TextView discount;
    }
}