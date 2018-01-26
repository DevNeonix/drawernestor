package com.example.root.navigation.Activites;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.navigation.Models.Login;
import com.example.root.navigation.Models.RespuestaGenerica;
import com.example.root.navigation.Models.User;
import com.example.root.navigation.R;
import com.example.root.navigation.services.API;
import com.example.root.navigation.services.LoginServices;
import com.example.root.navigation.services.RegisterUser;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                final Dialog dialog = new Dialog(LoginRegisterActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialogspiner);

                dialog.show();

                Retrofit myRetrofit = API.myRetrofit;
                LoginServices myService = myRetrofit.create(LoginServices.class);
                Call<User> response = myService.login(new Login(etUsername.getText().toString(), etPassword.getText().toString()));
                response.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        dialog.dismiss();
                        int status = response.code();
                        if (status == 201) {
                            User data = response.body();
                            Toast.makeText(getApplicationContext(), "Bienvenido " + data.getFullname(), Toast.LENGTH_SHORT).show();
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

                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o Password errado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "No se pudo conectar con el servidor intentelo mas tarde", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });
    }

    private void dialogBinding(final Dialog dialog) {
        EditText input_name = dialog.findViewById(R.id.input_name);
        EditText input_email = dialog.findViewById(R.id.input_email);
        EditText input_password = dialog.findViewById(R.id.input_password);
        Button btn_signup = dialog.findViewById(R.id.btn_signup);
        TextView tvIngresa = dialog.findViewById(R.id.tvIngresa);
        tvIngresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Retrofit myRetrofit = API.myRetrofit;
        RegisterUser myService = myRetrofit.create(RegisterUser.class);
        final Call<RespuestaGenerica> response = myService.register(new User(0,
                input_name.getText().toString(),
                input_email.getText().toString(),
                new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                "",
                null,
                null,
                null,
                "",
                input_password.getText().toString()
        ));
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 response.enqueue(new Callback<RespuestaGenerica>() {
                    @Override
                    public void onResponse(Call<RespuestaGenerica> call, Response<RespuestaGenerica> response) {
                        Log.e("status Servicio", response.code() +"");
                        if (response.code() == 401) {
                            //
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else  if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Este Usuario ya esta Registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RespuestaGenerica> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No se pudo establecer conexion", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}




