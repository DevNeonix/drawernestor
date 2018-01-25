package com.example.root.navigation.Activites;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.User;
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
        tvRegister = findViewById(R.id.tvRegister);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(LoginRegisterActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_registro);


                dialogBinding(dialog);
                dialog.show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit myRetrofit = API.myRetrofit;
                LoginServices myService = myRetrofit.create(LoginServices.class);
                Call<User> response = myService.login(new Login(etUsername.getText().toString(),etPassword.getText().toString()));
                response.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        int status = response.code();
                        if (status == 201){
                            User data = response.body();
                            Toast.makeText(getApplicationContext(), "Bienvenido " + data.getFullname(),Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("id", data.getId() + "");
                            editor.putString("fullname", data.getFullname());
                            editor.putString("email", data.getEmail());
                            editor.putString("fecha_nacimiento", data.getFecha_nacimiento());
                            editor.putString("remember_token", data.getRemember_token());
                            editor.putString("url_image", data.getUrl_image());
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
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No se pudo conectar con el servidor intentelo mas tarde",Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }

    private void dialogBinding(final Dialog dialog) {
        TextView tvIngresa = dialog.findViewById(R.id.tvIngresa);
        tvIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


}

