package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity;

import java.io.Serializable;

public class Category implements Serializable {

    private String name, text, url_img, type;

    public Category(String type, String name, String text, String url_img) {
        this.type = type;
        this.name = name;
        this.text = text;
        this.url_img = url_img;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
