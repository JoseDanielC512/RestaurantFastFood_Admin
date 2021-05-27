package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private String id, idCustomer, date, state;
    private ArrayList<Order_products> products;

    public Order() {

    }

    public Order(String idCustomer, String date, String state) {
        this.idCustomer = idCustomer;
        this.date = date;
        this.state = state;
        this.products = null;
    }

    @Exclude
    public ArrayList<Order_products> getProducts() {
        return products;
    }

    @Exclude
    public void setProducts(ArrayList<Order_products> products) {
        this.products = products;
    }

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("id_customer")
    public String getIdCustomer() {
        return idCustomer;
    }

    @PropertyName("id_customer")
    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
