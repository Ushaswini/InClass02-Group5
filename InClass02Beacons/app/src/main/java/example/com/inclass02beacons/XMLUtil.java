package example.com.inclass02beacons;

import android.text.Html;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import example.com.inclass02beacons.Product;

/**
 * Created by Nitin on 6/8/2017.
 */

public class XMLUtil {

    static class ArticleSAXParser extends DefaultHandler {
        ArrayList<Product> arrayList;
        Product product;
        StringBuilder sb;

        public ArrayList<Product> getArrayList() {
            return arrayList;
        }

        static  ArrayList<Product> articleParser(InputStream in) throws IOException, SAXException {
            ArticleSAXParser parser = new ArticleSAXParser();
            Xml.parse(in, Xml.Encoding.valueOf("Cp1255"),parser);
            return parser.getArrayList();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            //super.startElement(uri, localName, qName, attributes);
            if(localName.equalsIgnoreCase("Discount")){
                product = new Product();
                //Log.d("demo","article create");
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            //super.endElement(uri, localName, qName);
            if(localName.equalsIgnoreCase("Discount")){
                arrayList.add(product);
                product = null;
            }else if(localName.equalsIgnoreCase("Name") && product!= null){
                product.setName(sb.toString().trim());
                //Log.d("demo",sb.toString().trim());
            }else if(localName.equalsIgnoreCase("Price") && product!= null){
                product.setPrice(Double.parseDouble(sb.toString().trim()));
            }else if(localName.equalsIgnoreCase("RegionId") && product!= null){
                product.setRegionId(Integer.parseInt(sb.toString().trim()));
            }else if(localName.equalsIgnoreCase("Photo") && product!= null){
                product.setImageUrl(sb.toString().trim());
            }else if(localName.equalsIgnoreCase("Id") && product!= null){
                product.setId(Integer.parseInt(sb.toString().trim()));
            }else if(localName.equalsIgnoreCase("Discountvalue") && product!= null){
                product.setDiscountValue(Double.parseDouble(sb.toString().trim()));
            }


            sb.setLength(0);
        }

        @Override
        public void startDocument() throws SAXException {
            //super.startDocument();
            arrayList = new ArrayList<Product>();
            sb = new StringBuilder();
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            //super.characters(ch, start, length);
            sb.append(ch,start,length);
        }
    }
}
