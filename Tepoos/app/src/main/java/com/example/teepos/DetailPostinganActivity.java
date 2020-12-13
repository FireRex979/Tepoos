package com.example.teepos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teepos.adapter.KomentarAdapter;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.showPostingan.KomentarItem;
import com.example.teepos.datasignup.showPostingan.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class DetailPostinganActivity extends AppCompatActivity {
    private TextView nama_user_tv, caption_tv, tgl_postingan;
    private ImageView foto_profile_iv, foto_postingan_iv;
    private EditText komentar_et;
    private Button btn_komentar;
    public int id_postingan;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    KomentarAdapter adapter;
    ArrayList list_komentar = new ArrayList<KomentarItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_postingan);

        recyclerView = findViewById(R.id.komentar_list);
        adapter = new KomentarAdapter(list_komentar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        nama_user_tv = findViewById(R.id.nama_user);
        caption_tv = findViewById(R.id.caption);
        tgl_postingan = findViewById(R.id.tgl_postingan);
        foto_postingan_iv = findViewById(R.id.foto_postingan);
        foto_profile_iv = findViewById(R.id.foto_profile);
        komentar_et = findViewById(R.id.komentar_field);
        btn_komentar = findViewById(R.id.btn_store_komentar);

        int id = getIntent().getIntExtra("id", 0);
        getDataPostingan(id);

        btn_komentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeKomentar();
            }
        });
    }

    private void storeKomentar() {
        int id_user = sharedPreferences.getInt("id", 0);
        String komentar = komentar_et.getText().toString().trim();
        RetrofitHelper.server(this).storeKomentar(
                id_user,
                komentar,
                id_postingan
        ).enqueue(new Callback<com.example.teepos.datasignup.storeKomentar.Response>() {
            @Override
            public void onResponse(Call<com.example.teepos.datasignup.storeKomentar.Response> call, retrofit2.Response<com.example.teepos.datasignup.storeKomentar.Response> response) {
                Toast.makeText(DetailPostinganActivity.this, "Komentar Berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<com.example.teepos.datasignup.storeKomentar.Response> call, Throwable t) {
                Toast.makeText(DetailPostinganActivity.this, "Komentar gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataPostingan(int id) {
        RetrofitHelper.server(this).showPostingan(
                id
        ).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                id_postingan = response.body().getPostingan().getId();
                nama_user_tv.setText(response.body().getPostingan().getUser().getName());
                caption_tv.setText(response.body().getPostingan().getCaption());
                tgl_postingan.setText(response.body().getPostingan().getTglPostingan());
                Glide.with(DetailPostinganActivity.this).load(response.body().getPostingan().getFoto()).into(foto_postingan_iv);
                Glide.with(DetailPostinganActivity.this).load(response.body().getPostingan().getUser().getFotoProfile()).into(foto_profile_iv);
                list_komentar.clear();
                list_komentar.addAll(response.body().getKomentar());
                Log.d("Cek Komentar", response.body().getKomentar().get(0).getKomentar());
                adapter.notifyDataSetChanged();
                recyclerView.requestLayout();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(DetailPostinganActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}