package com.example.root.navigation.Activites;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
                }

                if (fragmentTransaction) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .commit();
                    item.setChecked(true);
                    getSupportActionBar().setTitle(item.getTitle());
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
}
