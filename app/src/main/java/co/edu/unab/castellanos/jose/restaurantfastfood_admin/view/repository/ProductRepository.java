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

    private static final String COLECCION_PRODUCTOS = "product";
    private FirebaseFirestore firestore;

    public ProductRepository(FormularioActivity formularioActivity) {

        firestore = FirebaseFirestore.getInstance();
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

}

