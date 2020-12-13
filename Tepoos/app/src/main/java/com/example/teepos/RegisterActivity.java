package com.example.teepos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teepos.api.ApiService;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.ResponseSignUp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText tgl_lahir;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        preferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        ImageView imageView = (ImageView) findViewById(R.id.bg_landing_page);
        Glide.with(this).load(R.drawable.bg_login_page)
                .centerCrop()
                .into(imageView);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        tgl_lahir = (EditText) findViewById(R.id.tgl_lahir);
        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        Button btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nama_et = (EditText) findViewById(R.id.nama);
                EditText email_et = (EditText) findViewById(R.id.email);
                EditText tgl_lahir_et = (EditText) findViewById(R.id.tgl_lahir);
                RadioGroup genderGroup = (RadioGroup) findViewById(R.id.kelamin);
                int selectedId = genderGroup.getCheckedRadioButtonId();
                RadioButton genderButton = (RadioButton) findViewById(selectedId);
                EditText password_et = (EditText) findViewById(R.id.password);
                asyncSignUp(
                        nama_et.getText().toString(),
                        email_et.getText().toString(),
                        tgl_lahir_et.getText().toString(),
                        genderButton.getText().toString(),
                        password_et.getText().toString()
                );
            }
        });
    }

    private void asyncSignUp(String nama, String email, String tgl_lahir, String kelamin, String password) {
        RetrofitHelper.server(this).signUp(
                email,
                nama,
                tgl_lahir,
                kelamin,
                password
        ).enqueue(new Callback<ResponseSignUp>() {
            @Override
            public void onResponse(Call<ResponseSignUp> call, Response<ResponseSignUp> response) {

                Intent toLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                String msg = "Register Berhasil, silahkan login.";
                toLogin.putExtra("msg", msg);
                startActivity(toLogin);
            }

            @Override
            public void onFailure(Call<ResponseSignUp> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal Register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tgl_lahir.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}