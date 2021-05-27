package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Category;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity.ListActivity;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.adapter.CategoryAdapter;

public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<Category> categories;

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

        fillCategories();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView rvCategories = view.findViewById(R.id.rv_categories);
        CategoryAdapter adapter = new CategoryAdapter(categories);

        adapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category obj, int position) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("category", obj);
                startActivity(intent);
            }
        });

        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setHasFixedSize(true);

        return view;
    }

    private void fillCategories(){
        categories = new ArrayList<>();
        if (categories.isEmpty()){
            categories.add(new Category("hamburguesa", "Hamburguesas", "Lorem Ipsum", "https://d3jv0cqma81l17.cloudfront.net/articulos/1534/imagen_destacadas/original/5038_burger-master-bucaramanga-2019.jpg?1573812560"));
            categories.add(new Category("perro", "Perros", "Lorem Ipsum", "https://juegoscocinarpasteleria.org/wp-content/uploads/2020/04/C%C3%B3mo-hervir-perros-calientes.jpg"));
            categories.add(new Category("salchipapa", "Salchipapas", "Lorem Ipsum", "https://abrecht-group.com/wp-content/uploads/2017/03/5a134daf63fca.jpeg"));
            categories.add(new Category("choripapa", "Choripapas", "Lorem Ipsum", "https://media.domicompras.com/aliados/970/productos/4.png"));
            categories.add(new Category("otros", "Otros", "Lorem Ipsum", "https://foodbattlecolombia.com/wp-content/uploads/2019/12/FEDERICO.jpeg"));
        }
    }
}