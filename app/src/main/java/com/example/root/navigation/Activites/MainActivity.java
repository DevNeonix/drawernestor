package com.example.root.navigation.Activites;


import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.root.navigation.Fragments.CategoriaFragment;
import com.example.root.navigation.Fragments.InfoFragment;
import com.example.root.navigation.Fragments.OfertaFragment;
import com.example.root.navigation.Fragments.ProfileFragment;
import com.example.root.navigation.Fragments.RegistroFragment;
import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.RUser;
import com.example.root.navigation.Models.RespuestaGenerica;
import com.example.root.navigation.Models.User;
import com.example.root.navigation.R;
import com.example.root.navigation.services.API;
import com.example.root.navigation.services.LoginServices;
import com.example.root.navigation.services.RegisterUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        CambiarMenu();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                CambiarMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

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
                        editor.apply();
                        drawerLayout.closeDrawers();
                        CambiarMenu();
                        setFragmentByDefault();
                        break;
                    case R.id.m_login:
                        final Dialog dialog_login = new Dialog(MainActivity.this);
                        dialog_login.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_login.setCancelable(true);
                        dialog_login.setContentView(R.layout.dialog_login);
                        LinearLayout ll = dialog_login.findViewById(R.id.lllogin);
                        ll.getLayoutParams().width = findViewById(R.id.llmain).getWidth()-30;
                        ll.requestLayout();
                        dialog_login.show();

                        dialog_login.findViewById(R.id.dl_btnLogin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                EditText etUsername = dialog_login.findViewById(R.id.dl_etUsername);
                                EditText etPassword = dialog_login.findViewById(R.id.dl_etPassword);
                                final SharedPreferences preferences=getSharedPreferences("marcaideas",MODE_PRIVATE);

                                Retrofit myRetrofit = API.myRetrofit;
                                LoginServices myService = myRetrofit.create(LoginServices.class);
                                Call<User> response = myService.login(new Login(etUsername.getText().toString(), etPassword.getText().toString()));
                                response.enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {

                                        int status = response.code();
                                        if (status == 200) {
                                            User data = response.body();
                                            Toast.makeText(getApplicationContext(), "Bienvenido " + data.getFullname(), Toast.LENGTH_SHORT).show();
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.putString("id", data.getId() + "");
                                            editor.putString("fullname", data.getFullname());
                                            editor.putString("email", data.getEmail());
                                            editor.putString("fecha_nacimiento", data.getFecha_nacimiento());
                                            editor.putString("remember_token", data.getRemember_token());
                                            editor.putString("url_image", data.getUrl_image());
                                            editor.apply();
                                            dialog_login.dismiss();
                                            CambiarMenu();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Usuario o Password errado", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "No se pudo conectar con el servidor intentelo mas tarde", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });



                        break;
                    case R.id.m_crearcuenta:
                        final Dialog dialog_registro = new Dialog(MainActivity.this);
                        dialog_registro.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog_registro.setCancelable(true);
                        dialog_registro.setContentView(R.layout.dialog_registro);

                        LinearLayout llregistro = dialog_registro.findViewById(R.id.llregistro);
                        llregistro.getLayoutParams().width = findViewById(R.id.llmain).getWidth()-30;
                        llregistro.requestLayout();
                        dialog_registro.show();



                        final EditText input_name = dialog_registro.findViewById(R.id.dr_input_name);
                        final EditText input_email = dialog_registro.findViewById(R.id.dr_input_email);
                        final EditText input_password = dialog_registro.findViewById(R.id.dr_input_password);
                        Button btn_signup = dialog_registro.findViewById(R.id.dr_btn_signup);

                        Retrofit myRetrofit = API.myRetrofit;
                        final RegisterUser myService = myRetrofit.create(RegisterUser.class);

                        btn_signup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (input_email.getText().equals("") || input_name.getText().equals("") || input_password.getText().equals("")) {
                                    Toast.makeText(MainActivity.this, "Ingresa los datos correctamente.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Call<RespuestaGenerica> res = myService.register(new RUser(input_name.getText().toString(), input_email.getText().toString(), input_password.getText().toString()));
                                    res.enqueue(new Callback<RespuestaGenerica>() {
                                        @Override
                                        public void onResponse(Call<RespuestaGenerica> call, Response<RespuestaGenerica> response) {
                                            if (response.code() == 201) {
                                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                dialog_registro.dismiss();
                                            } else if (response.code() == 400) {
                                                Toast.makeText(MainActivity.this, "Este email ya esta registrado.", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<RespuestaGenerica> call, Throwable t) {
                                            Toast.makeText(MainActivity.this, "no se pudo establecer conexion con el servidor", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });



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

    private void CambiarMenu() {
        if (getSharedPreferences("marcaideas", MODE_PRIVATE).getString("id", "").equals("")) {
            navigationView.getMenu().findItem(R.id.cerrarsesion).setVisible(false);
            navigationView.getMenu().findItem(R.id.informacion).setVisible(false);
            navigationView.getMenu().findItem(R.id.Profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.m_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.m_crearcuenta).setVisible(true);

        } else {

            navigationView.getMenu().findItem(R.id.cerrarsesion).setVisible(true);
            navigationView.getMenu().findItem(R.id.informacion).setVisible(true);
            navigationView.getMenu().findItem(R.id.Profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.m_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.m_crearcuenta).setVisible(false);

        }
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
