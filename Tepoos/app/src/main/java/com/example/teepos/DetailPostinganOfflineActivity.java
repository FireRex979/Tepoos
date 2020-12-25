package com.example.teepos;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailPostinganOfflineActivity extends AppCompatActivity {
    private TextView nama_user_tv, caption_tv, tgl_postingan_tv;
    private ImageView foto_profile_iv, foto_postingan_iv, btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_postingan_offline);
        nama_user_tv = findViewById(R.id.nama_user);
        btn_back = findViewById(R.id.btn_back);
        caption_tv = findViewById(R.id.caption);
        tgl_postingan_tv = findViewById(R.id.tgl_postingan);
        foto_postingan_iv = findViewById(R.id.foto_postingan);
        foto_profile_iv = findViewById(R.id.foto_profile);

        String nama = getIntent().getStringExtra("nama");
        String caption = getIntent().getStringExtra("caption");
        String tgl_postingan = getIntent().getStringExtra("tgl_postingan");

        caption_tv.setText(caption);
        tgl_postingan_tv.setText(tgl_postingan);
        nama_user_tv.setText(nama);

        Glide.with(DetailPostinganOfflineActivity.this).load(R.drawable.upload_image).into(foto_postingan_iv);
        Glide.with(DetailPostinganOfflineActivity.this).load(R.drawable.profile).into(foto_profile_iv);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}