package com.example.teepos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import com.example.teepos.datasignup.postinganSendiri.DataItem;
import com.example.teepos.db.Postingan;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private String msg;
    private Button btn_login;
    public EditText email_et, password_et;
    private SharedPreferences preferences, data_profile;
    private SharedPreferences.Editor editor;
    ArrayList<DataItem> list = new ArrayList();
    CompositeDisposable mDisposable;
    ProgressDialog progressDoalog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDisposable = new CompositeDisposable();
        preferences = getSharedPreferences("credential_login", Context.MODE_PRIVATE);
        data_profile = getSharedPreferences("profile", Context.MODE_PRIVATE);
        email_et = (EditText) findViewById(R.id.email);
        password_et = (EditText) findViewById(R.id.password);
        ImageView imageView = (ImageView) findViewById(R.id.bg_landing_page);
        Glide.with(this).load(R.drawable.bg_login_page)
                .centerCrop()
                .into(imageView);
        progressDoalog = new ProgressDialog(LoginActivity.this);
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Sedang Sinkronisasi Data Anda ...");
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                if(email.isEmpty()){
                    email_et.setError("Email tidak boleh kosong!");
                }else if(password.isEmpty()){
                    password_et.setError("Password tidak boleh kosong!");
                }else{
                    if(isNetworkConnected()){
                        asyncSignUp(
                                email_et.getText().toString(),
                                password_et.getText().toString()
                        );
                    }else{
                        Toast.makeText(LoginActivity.this, "Tidak ada koneksi internet, Login Gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void asyncSignUp(String email, String password) {
        progressDoalog.show();
        RetrofitHelper.server(this).login(
                email,
                password
        ).enqueue(new Callback<com.example.teepos.datasignup.login.Response>() {
            @Override
            public void onResponse(Call<com.example.teepos.datasignup.login.Response> call, Response<com.example.teepos.datasignup.login.Response> response) {
                if(response.code() <= 400){
                    String token = response.body().getAccessToken();
                    editor = preferences.edit();
                    editor.putString("token_login", token);
                    editor.apply();
                    asyncGetUserData();
                }else{
                    Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_SHORT).show();
                    progressDoalog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<com.example.teepos.datasignup.login.Response> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal Login : "+t, Toast.LENGTH_SHORT).show();
                progressDoalog.dismiss();
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

                getDataPostinganUser();

                Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(toHome);
            }

            @Override
            public void onFailure(Call<GetDataUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataPostinganUser() {
        RetrofitHelper.server(this).getPostinganSendiri()
                .enqueue(new Callback<com.example.teepos.datasignup.postinganSendiri.Response>() {
                    @Override
                    public void onResponse(Call<com.example.teepos.datasignup.postinganSendiri.Response> call, Response<com.example.teepos.datasignup.postinganSendiri.Response> response) {
                        list.addAll(response.body().getData());
                        for (int i = 0; i < list.size(); i++) {
                            simpanSqlite(list.get(i));
                        }
                        progressDoalog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<com.example.teepos.datasignup.postinganSendiri.Response> call, Throwable t) {
                        progressDoalog.dismiss();
                        Toast.makeText(LoginActivity.this, "Gagal Memuat Data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void simpanSqlite(DataItem data) {
        Postingan postingan = new Postingan();
        postingan.setId_postingan(String.valueOf(data.getId()));
        postingan.setId_user(String.valueOf(data.getIdUser()));
        postingan.setCaption(data.getCaption());
        postingan.setFoto(data.getFoto());
        postingan.setTgl_postingan(data.getTglPostingan());

        Single.just(postingan)
                .subscribeOn(Schedulers.io())
                .map(new Function<Postingan, Object>() {
                    @Override
                    public Object apply(Postingan postingan) throws Throwable {
                        App.db(LoginActivity.this).postinganDao().insert(postingan);
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Object>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Object o) {
//                        Toast.makeText(LoginActivity.this, "Sukses Menambahkan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(LoginActivity.this, "Gagal Menambahkan", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}