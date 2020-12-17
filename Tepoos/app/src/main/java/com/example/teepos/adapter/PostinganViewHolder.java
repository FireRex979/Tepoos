package com.example.teepos.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teepos.R;

public class PostinganViewHolder extends RecyclerView.ViewHolder {
        TextView nama_user_tv, caption_tv, tgl_postingan_tv;
        ImageView foto_profile, foto_postingan;
        LinearLayout postingan_show;
        SharedPreferences preferences;

        public PostinganViewHolder(@NonNull View itemView) {
            super(itemView);
            preferences = itemView.getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
            nama_user_tv = itemView.findViewById(R.id.nama_user);
            caption_tv = itemView.findViewById(R.id.caption);
            tgl_postingan_tv = itemView.findViewById(R.id.tgl_postingan);
            foto_postingan = itemView.findViewById(R.id.foto_postingan);
            foto_profile = itemView.findViewById(R.id.foto_profile);
            postingan_show = itemView.findViewById(R.id.postingan_item);
        }
    }