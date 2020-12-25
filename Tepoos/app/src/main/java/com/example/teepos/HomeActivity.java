package com.example.teepos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.postinganbaru.DataItem;
import com.example.teepos.datasignup.postinganbaru.Response;
import com.example.teepos.db.Postingan;
import com.example.teepos.db.PostinganBaru;

import java.util.ArrayList;

import hari.bounceview.BounceView;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    private String token_login;
    private ImageView btn_home, btn_add, btn_profile;
    CompositeDisposable mDisposable;
    ArrayList<DataItem> list = new ArrayList();
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        App.setupFcm(this);
        preferences = getSharedPreferences("credential_login", Context.MODE_PRIVATE);
        token_login = preferences.getString("token_login", null);

        btn_home = (ImageView) findViewById(R.id.home);
        btn_add = (ImageView) findViewById(R.id.add_postingan);
        btn_profile = (ImageView) findViewById(R.id.profile);
        mDisposable = new CompositeDisposable();

        if(isNetworkConnected()){
            truncatePostinganBaru();

            getPostinganBaru();
        }

        loadFragment(new HomeFragment());
        btn_home.setColorFilter(Color.parseColor("#F0E68C"));
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeFragment();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add.setColorFilter(Color.parseColor("#F0E68C"));
                btn_profile.setColorFilter(Color.parseColor("#195190"));
                btn_home.setColorFilter(Color.parseColor("#195190"));
                loadFragment(new AddPostinganFragment());
            }
        });

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add.setColorFilter(Color.parseColor("#195190"));
                btn_profile.setColorFilter(Color.parseColor("#F0E68C"));
                btn_home.setColorFilter(Color.parseColor("#195190"));
                loadFragment(new ProfileFragment());
            }
        });
    }

    private void getPostinganBaru() {
        RetrofitHelper.server(HomeActivity.this).getPostinganBaru()
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        list.addAll(response.body().getData());
                        for (int i = 0; i < list.size(); i++) {
                            storePostinganBaru(list.get(i));
                        }
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {

                    }
                });
    }

    private void storePostinganBaru(DataItem dataItem) {
        PostinganBaru postinganBaru = new PostinganBaru();
        postinganBaru.setId_postingan(String.valueOf(dataItem.getId()));
        postinganBaru.setCaption(dataItem.getCaption());
        postinganBaru.setFoto(dataItem.getFoto());
        postinganBaru.setTgl_postingan(dataItem.getTglPostingan());
        postinganBaru.setNama(dataItem.getUser().getName());
        postinganBaru.setId_user(String.valueOf(dataItem.getIdUser()));

        Single.just(postinganBaru)
                .subscribeOn(Schedulers.io())
                .map(new Function<PostinganBaru, Object>() {
                    @Override
                    public Object apply(PostinganBaru postinganBaru) throws Throwable {
                        App.db(HomeActivity.this).postinganBaruDao().insert(postinganBaru);
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
//                        Toast.makeText(HomeActivity.this, "Sukses Menambahkan Postingan Baru", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(HomeActivity.this, "Gagal Menambahkan Postinga Baru", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void toHomeFragment(){
        btn_add.setColorFilter(Color.parseColor("#195190"));
        btn_profile.setColorFilter(Color.parseColor("#195190"));
        btn_home.setColorFilter(Color.parseColor("#F0E68C"));
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.commit();
    }

    private void truncatePostinganBaru(){
        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Throwable {
                        App.db(HomeActivity.this).postinganBaruDao().truncate();
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
//                        Toast.makeText(HomeActivity.this, "Sukses Menghapus", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(HomeActivity.this, "Gagal Menghapus", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}