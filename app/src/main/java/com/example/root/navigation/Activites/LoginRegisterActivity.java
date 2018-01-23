package com.example.root.navigation.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.Users;
import com.example.root.navigation.R;
import com.example.root.navigation.services.API;
import com.example.root.navigation.services.LoginServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginRegisterActivity extends AppCompatActivity {
    LinearLayout llLogin, llRegister;
    TextView tvRegister, tvIngresa;
    EditText etUsername, etPassword;
    CardView btnLogin;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        preferences = getSharedPreferences("marcaideas", MODE_PRIVATE);
        if (preferences.getString("id", "").equals("")) {

        } else {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        llLogin = findViewById(R.id.llLogin);
        llRegister = findViewById(R.id.llRegistro);
        tvIngresa = findViewById(R.id.tvIngresa);
        tvRegister = findViewById(R.id.tvRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        llLogin.setVisibility(View.GONE);
        llRegister.setVisibility(View.GONE);
        llLogin.setVisibility(View.VISIBLE);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.GONE);
                llRegister.setVisibility(View.VISIBLE);
            }
        });
        tvIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llLogin.setVisibility(View.VISIBLE);
                llRegister.setVisibility(View.GONE);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit myRetrofit = API.myRetrofit;
                LoginServices myService = myRetrofit.create(LoginServices.class);
                Call<Users> response = myService.login(new Login(etUsername.getText().toString(),etPassword.getText().toString()));
                response.enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        int status = response.code();
                        if (status == 201){
                            Users data = response.body();
                            Toast.makeText(getApplicationContext(), "Bienvenido " + data.getFullname(),Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("id", data.getId() + "");
                            editor.putString("fullname", data.getFullname());
                            editor.putString("email", data.getEmail());
                            editor.putString("fecha_nacimiento", data.getFecha_nacimiento());
                            editor.putString("remember_token", data.getRemember_token());
                            // editor.putString("created_at", data.getCreated_at().toString());
                            // editor.putString("updated_at", data.getUpdated_at().toString());
                            // editor.putString("deleted_at", data.getDeleted_at().toString());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {
                            Toast.makeText(getApplicationContext(), "Usuario o Password errado",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No se pudo conectar con el servidor intentelo mas tarde",Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }

}

