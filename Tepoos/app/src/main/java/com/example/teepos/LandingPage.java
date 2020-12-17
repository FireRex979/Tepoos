package com.example.teepos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LandingPage extends AppCompatActivity {
    private Button btn_login;
    private TextView btn_sign_up;
    private String token;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        preferences = getSharedPreferences("credential_login", Context.MODE_PRIVATE);
        token = preferences.getString("token_login", null);
        if(!TextUtils.isEmpty(token)){
            Intent toHome = new Intent(LandingPage.this, HomeActivity.class);
            toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(toHome);
        }
        btn_login = (Button) findViewById(R.id.btn_login_page);
        btn_sign_up = (TextView) findViewById(R.id.btn_sign_up);

        ImageView imageView = (ImageView) findViewById(R.id.bg_landing_page);
        Glide.with(this).load(R.drawable.bg_landing_page)
                .centerCrop()
                .into(imageView);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginPage();
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterPage();
            }
        });
    }

    private void toLoginPage(){
        Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(toLogin);
    }

    private void toRegisterPage(){
        Intent toRegister = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(toRegister);
    }
}