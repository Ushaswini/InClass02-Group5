package example.com.inclass02beacons;

/**
 * Created by Nitin on 9/12/2017.
 */

public class Product {

    String name, imageUrl;
    double discountValue, price;
    int id,regionId;

    public Product(){

    }
    public Product(String name, String imageUrl, double discountValue, double price, int id, int regionId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.discountValue = discountValue;
        this.price = price;
        this.id = id;
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
}
