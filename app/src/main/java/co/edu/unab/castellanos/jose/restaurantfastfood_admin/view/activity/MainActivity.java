package co.edu.unab.castellanos.jose.restaurantfastfood_admin.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import co.edu.unab.castellanos.jose.restaurantfastfood_admin.R;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment.Home;
import co.edu.unab.castellanos.jose.restaurantfastfood_admin.model.fragment.Orders;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView btn_view;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        toolbar.setTitle(getString(R.string.home));
        getFragments(new Home());

        btn_view = findViewById(R.id.bottomNavigationView);

        btn_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        toolbar.setTitle(getString(R.string.home));
                        getFragments(new Home());
                        return true;
                    case R.id.orders:
                        toolbar.setTitle(getString(R.string.home));
                        getFragments(new Orders());
                        return true;
//                    case R.id.history:
//                        toolbar.setTitle(getString(R.string.home));
//                        getFragments(new History());
//                        return true;
                }
                return false;
            }
        });
    }

    private void getFragments(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}