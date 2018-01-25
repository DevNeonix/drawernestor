package com.example.root.navigation.Activites;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.root.navigation.Fragments.CategoriaFragment;
import com.example.root.navigation.Fragments.InfoFragment;
import com.example.root.navigation.Fragments.OfertaFragment;
import com.example.root.navigation.Fragments.ProfileFragment;
import com.example.root.navigation.Fragments.RegistroFragment;
import com.example.root.navigation.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navview);

        setFragmentByDefault();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                boolean fragmentTransaction = false;
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.oferta:
                        fragment = new OfertaFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.categoria:
                        fragment = new CategoriaFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.informacion:
                        fragment = new InfoFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.Profile:
                        fragment = new ProfileFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.register:
                        fragment = new RegistroFragment();
                        fragmentTransaction = true;
                        break;
                    case R.id.cerrarsesion:
                        SharedPreferences preferences = getSharedPreferences("marcaideas", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("id", "");
                        editor.putString("fullname", "");
                        editor.putString("email", "");
                        editor.putString("fecha_nacimiento", "");
                        editor.putString("remember_token", "");
                        // editor.putString("created_at", data.getCreated_at().toString());
                        // editor.putString("updated_at", data.getUpdated_at().toString());
                        // editor.putString("deleted_at", data.getDeleted_at().toString());
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), LoginRegisterActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                }

                if (fragmentTransaction) {
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }

                return true;
            }
        });
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
    }

    private void setFragmentByDefault() {
        changeFragment(new OfertaFragment(), navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem menuItem) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
        menuItem.setChecked(true);
        getSupportActionBar().setTitle(menuItem.getTitle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // Abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long back_pressed = 0;

    @Override
    public void onBackPressed() {
        if (back_pressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Presiona dos veces para salir!!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}
