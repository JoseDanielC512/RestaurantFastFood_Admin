package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Order;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Order_products;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Product;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.adapter.OrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Orders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Orders extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String id_customer;
    private Order order;
    private ArrayList<Order_products> products;
    private OrderAdapter adapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Orders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Orders.
     */
    // TODO: Rename and change types and number of parameters
    public static Orders newInstance(String param1, String param2) {
        Orders fragment = new Orders();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        id_customer = auth.getCurrentUser().getUid();





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        listenOrders();



        RecyclerView rvOrders = view.findViewById(R.id.rv_orders);
        adapter = new OrderAdapter(new ArrayList<>());
        rvOrders.setAdapter(adapter);
        rvOrders.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvOrders.setHasFixedSize(true);




        return view;
    }

    private void listenOrders() {
        Log.d("orders", "ingresa al método");

        db.collection("customers").document(id_customer).collection("orders").whereEqualTo("state", "Pendiente")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    Log.d("orders", "primera consulta");

                    if (!task.getResult().getDocuments().isEmpty()){
                        for (DocumentSnapshot document : task.getResult().getDocuments()) {
                            order = document.toObject(Order.class);
                            order.setId(document.getId());
                            db.collection("customers").document(id_customer).collection("orders").document(document.getId())
                                    .collection("order_products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    Log.d("orders", "segunda consulta");
                                    products = new ArrayList<>();
                                    if (task.isSuccessful()){
                                        if (!task.getResult().getDocuments().isEmpty()){
                                            for (DocumentSnapshot document : task.getResult().getDocuments()){
                                                Log.d("orders", document.toString());
                                                Order_products orderProducts = document.toObject(Order_products.class);
                                                orderProducts.setId(document.getId());
                                                db.collection("products").document(orderProducts.getId_product()).get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                Log.d("orders", "tercera consulta");
                                                                if (task.isSuccessful()){
                                                                    Product product = task.getResult().toObject(Product.class);
                                                                    product.setId(task.getResult().getId());
                                                                    orderProducts.setProduct(product);
                                                                    products.add(orderProducts);
                                                                    order.setProducts(products);
                                                                    adapter.setProducts(products);
                                                                    Log.d("orders", order.getProducts().toString());
                                                                }
                                                            }
                                                        });
                                            }
                                        }else {

                                            Log.d("orders", task.getResult().getDocuments().toString());
                                        }
                                    }else {
                                        Log.d("orders", "error");
                                    }
                                }
                            });
                            //products.add(product);
                        }
                    }
                }
            }
        });
    }
}