package com.example.teepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.GetDataUser;
import com.example.teepos.datasignup.ResponseLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String msg;
    private Button btn_login;
    public EditText email_et, password_et;
    private SharedPreferences preferences, data_profile;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("credential_login", Context.MODE_PRIVATE);
        data_profile = getSharedPreferences("profile", Context.MODE_PRIVATE);

        ImageView imageView = (ImageView) findViewById(R.id.bg_landing_page);
        Glide.with(this).load(R.drawable.bg_login_page)
                .centerCrop()
                .into(imageView);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_et = (EditText) findViewById(R.id.email);
                password_et = (EditText) findViewById(R.id.password);
                asyncSignUp(
                        email_et.getText().toString(),
                        password_et.getText().toString()
                );
            }
        });

    }

    private void asyncSignUp(String email, String password) {
        RetrofitHelper.server(this).login(
                email,
                password
        ).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                String token = response.body().getToken();
                editor = preferences.edit();
                editor.putString("token_login", token);
                editor.apply();
                asyncGetUserData();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void asyncGetUserData(){
        RetrofitHelper.server(this).getDataUser()
                .enqueue(new Callback<GetDataUser>() {
            @Override
            public void onResponse(Call<GetDataUser> call, Response<GetDataUser> response) {
                int id = response.body().getId();
                String nama = response.body().getName();
                String email = response.body().getEmail();
                String kelamin = response.body().getKelamin();
                String tgl_lahir = response.body().getTglLahir();
                String foto_profile = response.body().getFotoProfile();
                editor = data_profile.edit();
                editor.putInt("id", id);
                editor.putString("nama", nama);
                editor.putString("email", email);
                editor.putString("kelamin", kelamin);
                editor.putString("tgl_lahir", tgl_lahir);
                editor.putString("foto_profile", foto_profile);
                editor.apply();

                Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(toHome);
            }

            @Override
            public void onFailure(Call<GetDataUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Data Postingan tidak ditemukan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}