package co.edu.unab.castellanos.jose.restaurantfastfood_admin.network;

public interface CallBackApp<T> {

    void correcto(T respuesta);
    void error(Exception error);

}
