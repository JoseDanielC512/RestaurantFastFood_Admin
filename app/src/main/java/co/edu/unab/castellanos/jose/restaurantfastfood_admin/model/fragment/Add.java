package co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.entity.Product;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.network.CallBackApp;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.repository.ProductRepository;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Add#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Add extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText etName;
    private EditText etType;
    private EditText etPrice;
    private EditText etPicture;
    private EditText etDescripcion;
    private Button btFormulario;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Add() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Add.
     */
    // TODO: Rename and change types and number of parameters
    public static Add newInstance(String param1, String param2) {
        Add fragment = new Add();
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

        View view = inflater.inflate(R.layout.fragment_add, container, false);
        asociarElementos(view);

        String name = etName.getText().toString();
        String type = etType.getText().toString();
        String price = etPrice.getText().toString();
        String image = etPicture.getText().toString();
        String descripcion = etDescripcion.getText().toString();
        int price2 = Integer.parseInt(price);

        Product producto = new Product(type, name, price2, descripcion, image);

        btFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductRepository productoRepository = new ProductRepository();
                productoRepository.agregarProductosFS(producto, new CallBackApp<Boolean>() {
                    @Override
                    public void correct(Boolean respuesta) {
                        if (respuesta){
                            Toast.makeText(getContext(), "Producto Creado...", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "No se pudo crear el producto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void error(Exception error) {

                    }
                });
            }
        });


        return view;


    }

    private void asociarElementos(View view){
        etType = view.findViewById(R.id.et_product_type);
        etName = view.findViewById(R.id.et_product_name);
        etPrice = view.findViewById(R.id.et_product_price);
        etPicture = view.findViewById(R.id.et_product_picture);
        etDescripcion = view.findViewById(R.id.et_product_descripcion);
        btFormulario = view.findViewById(R.id.bt_add_product);
    }


}