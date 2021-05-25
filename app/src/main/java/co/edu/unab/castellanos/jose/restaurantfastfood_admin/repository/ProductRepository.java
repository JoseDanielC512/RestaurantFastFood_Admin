package co.edu.unab.castellanos.jose.restaurantfastfood_admin.repository;

import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.activity.FormularioActivity;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.entity.Product;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.network.CallBackApp;

public class ProductRepository {

    private static final String COLECCION_PRODUCTOS = "product";
    private FirebaseFirestore firestore;

    public ProductRepository(FormularioActivity formularioActivity) {

        firestore = FirebaseFirestore.getInstance();

    }

    public void obtenerProductosFS(CallBackApp<List<Product>> callBack){
        List<Product> listadoProductos = new ArrayList<>();
        firestore.collection(COLECCION_PRODUCTOS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    listadoProductos.clear();
                    for(DocumentSnapshot documento: task.getResult()){
                        Product misProductos = documento.toObject(Product.class);
                        misProductos.setId(documento.getId());
                        listadoProductos.add(misProductos);

                    }
                    callBack.correcto(listadoProductos);
                }else{
                    Log.e("errorF5",task.getException().getMessage());
                    callBack.error(task.getException());
                }

            }
        });
    }
    public void eliminarProductoFS(Product misProductos, CallBackApp<Boolean> callBack){
        firestore.collection(COLECCION_PRODUCTOS).document(misProductos.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callBack.correcto(true);

                }else{
                    callBack.correcto(false);
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
                    callBack.correcto(true);

                } else {
                    callBack.correcto(false);
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
                    callBack.correcto(true);

                } else {
                    callBack.correcto(false);
                    callBack.error(task.getException());

                }

            }
        });
    }


    public void obtenerProductoIdFS(String id, CallBackApp<Product> callBack){

        firestore.collection(COLECCION_PRODUCTOS).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Product misProductos = task.getResult().toObject(Product.class);
                    misProductos.setId(id);
                    callBack.correcto(misProductos);

                }else{
                    callBack.error(task.getException());

                }
            }
        });

    }
}

