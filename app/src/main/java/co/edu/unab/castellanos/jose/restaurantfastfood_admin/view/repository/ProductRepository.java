package co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.repository;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Product;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.network.CallBackApp;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity.FormularioActivity;

public class ProductRepository {

    private static final String COLECCION_PRODUCTOS = "products";
    private FirebaseFirestore firestore;

    public ProductRepository(FormularioActivity formularioActivity) {

        firestore = FirebaseFirestore.getInstance();
    }

    public void eliminarProductoFS(Product misProductos, CallBackApp<Boolean> callBack){
        firestore.collection(COLECCION_PRODUCTOS).document(misProductos.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callBack.correct(true);

                }else{
                    callBack.correct(false);
                    callBack.error(task.getException());
                }
            }
        });
    }


    public void agregarProductosFS(Product misProductos, CallBackApp<Boolean> callBack){
        firestore.collection(COLECCION_PRODUCTOS).add(misProductos).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    callBack.correct(true);

                } else {
                    callBack.correct(false);
                    callBack.error(task.getException());
                }
            }
        });
    }
    public void editarProductosFS(Product misProductos,CallBackApp<Boolean> callBack){
        firestore.collection(COLECCION_PRODUCTOS).document(misProductos.getId()).set(misProductos).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    callBack.correct(true);

                } else {
                    callBack.correct(false);
                    callBack.error(task.getException());

                }

            }
        });
    }

}

