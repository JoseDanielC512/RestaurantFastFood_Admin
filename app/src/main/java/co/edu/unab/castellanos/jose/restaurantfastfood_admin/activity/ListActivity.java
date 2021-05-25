package co.edu.unab.castellanos.jose.restaurantfastfood_admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.adapter.ListAdapter;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.entity.Category;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.entity.Product;

public class ListActivity<FirebaseAuth> extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView rvList;
    private ArrayList<Product> products;
    private ListAdapter adapter;
    private static final int CODIGO_AGREGAR_PRODUCTO = 200;

    public void irAgregarProducto(View v){
        Intent i = new Intent(ListActivity.this, FormularioActivity.class);
        startActivityForResult(i, CODIGO_AGREGAR_PRODUCTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);
        rvList = findViewById(R.id.rv_list);

        products = new ArrayList<>();
        adapter = new ListAdapter(products);


        Category category = (Category) getIntent().getSerializableExtra("category");


        adapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product obj, int position) {
//                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
//                intent.putExtra("product", obj);
//                startActivity(intent);
//                finish();
            }
        });

        getProducts(category.getType());

        rvList.setAdapter(adapter);
        rvList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvList.setHasFixedSize(true);
    }

    private void getProducts(String type){

        db = FirebaseFirestore.getInstance();
        db.collection("products").whereEqualTo("type", type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        products.add(product);
                    }
                    adapter.setProducts(products);
                } else {
                    Log.w("Error getting documents", task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        products.clear();
        finish();
    }
}