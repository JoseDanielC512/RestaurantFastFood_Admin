package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity;

import com.google.firebase.firestore.Exclude;

public class Order_products {

    private String id, id_order, id_product;
    private int amount, price;
    private Product product;

    public Order_products() {

    }

    public Order_products(String id_order, String id_product, int amount, int price, Product product) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.amount = amount;
        this.price = price;
        this.product = product;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Exclude
    public Product getProduct() {
        return product;
    }

    @Exclude
    public void setProduct(Product product) {
        this.product = product;
    }
}
