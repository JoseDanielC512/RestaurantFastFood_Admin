package co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Order;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Order_products;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Product;

public class DetailActivity extends AppCompatActivity {

    private Product product;
    private TextView name, price, description;
    private ImageView picture;
    private EditText amount;
    private Button doOrder;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String id_order;
    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        associateElements();
        mAuth = FirebaseAuth.getInstance(); // Instancia de FirebaseAuth para autenticar users
        db = FirebaseFirestore.getInstance(); // Access a Cloud Firestore instance from your Activity
        

        product = (Product) getIntent().getSerializableExtra("product");

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));
        description.setText(product.getDescription());
        Glide.with(getApplicationContext()).load(product.getUrl_picture()).into(picture);

        doOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
    }

    private void associateElements() {
        name = findViewById(R.id.tv_name_detail);
        price = findViewById(R.id.tv_price_detail);
        description = findViewById(R.id.tv_description_detail);
        picture = findViewById(R.id.iv_picture_detail);
        doOrder = findViewById(R.id.btn_edit_order);
    }

    private void createOrder(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String id_customer = currentUser.getUid();
        String id_product = product.getId();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(calendar.getTime());

        order = new Order(id_customer, date, "Pendiente");
        String amount = this.amount.getText().toString();
        int total_amount = Integer.parseInt(amount);
        double price = product.getPrice();
        double total_price = price*total_amount;

        db.collection("customers").document(id_customer).collection("orders").whereEqualTo("state", "Pendiente").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().size() > 0){

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            id_order = document.getId();
                        }
                        createOrderProduct(id_customer, id_product, total_amount, (int) total_price);

                    }else{
                        db.collection("customers").document(id_customer).collection("orders").add(order).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()){

                                    id_order = task.getResult().getId();

                                    createOrderProduct(id_customer, id_product, total_amount, (int) total_price);

                                }else {
                                    Log.w("Error getting documents", task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void createOrderProduct(String id_customer, String id_product, int amount, int total_price){
        db.collection("customers").document(id_customer).collection("orders").document(id_order).collection("order_products")
                .add(new Order_products(id_order, id_product, amount, total_price, product)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Toast.makeText(co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity.DetailActivity.this, "Pedido Creado!", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Log.w("Error getting documents", task.getException().getMessage());
                }
            }
        });
    }
}