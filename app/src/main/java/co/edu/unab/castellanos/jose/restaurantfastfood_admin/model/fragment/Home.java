package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Category;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Product;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity.ListActivity;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.adapter.CategoryAdapter;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.adapter.ListAdapter;

public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Product> products;
    private FirebaseFirestore db;
    private ListAdapter adapter;
    private RecyclerView rvList;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment.Home newInstance(String param1, String param2) {
        co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment.Home fragment = new co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment.Home();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getProducts();
        rvList = view.findViewById(R.id.rv_list_home);
        adapter = new ListAdapter(new ArrayList<>());
        rvList.setAdapter(adapter);

        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);

        return view;
    }

    private void getProducts(){
        db = FirebaseFirestore.getInstance();
        products = new ArrayList<>();
        db.collection("products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Product product = document.toObject(Product.class);
                        product.setId(document.getId());
                        products.add(product);
                        adapter.setProducts(products);
                    }
                } else {
                    Log.w("Error getting documents", task.getException().getMessage());
                }
            }
        });
    }
}