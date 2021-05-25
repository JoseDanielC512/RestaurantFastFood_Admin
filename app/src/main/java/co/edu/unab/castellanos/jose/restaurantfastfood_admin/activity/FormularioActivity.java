package co.edu.unab.castellanos.jose.restaurantfastfood_admin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.network.CallBackApp;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.repository.ProductRepository;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.entity.Product;

public class FormularioActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etType;
    private EditText etPrice;
    private EditText etPicture;
    private EditText etDescripcion;
    private Button btFormulario;
    private ProductRepository productoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        asociarElementos();

        productoRepository = new ProductRepository(FormularioActivity.this);

        Product misProductos = (Product) getIntent().getSerializableExtra("product");

        if(misProductos!=null){

            etType.setText(misProductos.getType());
            etName.setText(misProductos.getName());
            etPrice.setText(String.valueOf(misProductos.getPrice()));
            etPicture.setText(misProductos.getUrl_picture());
            etDescripcion.setText((misProductos.getDescription()));
            btFormulario.setText(R.string.bt_add_product);
        }

        btFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString();
                String type = etType.getText().toString();
                double price = Double.parseDouble(etPrice.getText().toString());
                String imagen = etPicture.getText().toString();
                String descripcion = etDescripcion.getText().toString();

                if(misProductos!=null){
                    misProductos.setName(name);
                    misProductos.setPrice(price);
                    misProductos.setDescription(descripcion);
                    misProductos.setUrl_picture(imagen);

                    productoRepository.editarProductosFS(misProductos, new CallBackApp<Boolean>() {
                        @Override
                        public void correcto(Boolean respuesta) {
                            if (respuesta){
                                Intent datos = new Intent();
                                datos.putExtra("product", misProductos);
                                setResult(RESULT_OK, datos);

                                finish();

                            }else{

                            }
                        }
                        @Override
                        public void error(Exception error) {

                        }
                    });

                }else{

                    Product misNuevosProductos = new Product(type, name, price, imagen, descripcion);
                    misNuevosProductos.setDescription(descripcion);

                    productoRepository.agregarProductosFS(misNuevosProductos, new CallBackApp<Boolean>() {
                        @Override
                        public void correcto(Boolean respuesta) {
                            if(respuesta){
                                Intent datos = new Intent();
                                datos.putExtra("product", misNuevosProductos);
                                setResult(RESULT_OK, datos);
                                finish();

                            }else{

                            }
                        }

                        @Override
                        public void error(Exception error) {

                        }
                    });


                }

            }
        });

    }

    private void asociarElementos(){
        etType = findViewById(R.id.et_product_type);
        etName = findViewById(R.id.et_product_name);
        etPrice = findViewById(R.id.et_product_price);
        etPicture = findViewById(R.id.et_product_picture);
        etDescripcion = findViewById(R.id.et_product_description);
        btFormulario = findViewById(R.id.bt_add_product);
    }
}