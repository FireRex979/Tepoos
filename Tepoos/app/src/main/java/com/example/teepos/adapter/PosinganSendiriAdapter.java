package com.example.teepos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teepos.R;
import com.example.teepos.db.Postingan;

import java.util.ArrayList;
import java.util.List;

public class PosinganSendiriAdapter extends RecyclerView.Adapter<PostinganViewHolder> {
    List<Postingan> list = new ArrayList();
    Click itemClick;

    public PosinganSendiriAdapter(List<Postingan> list, Click click) {
        this.list = list;
        this.itemClick = click;
    }

    @NonNull
    @Override
    public PostinganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postingan_sendiri, parent, false);
        return new PostinganViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostinganViewHolder holder, int position) {
        Postingan m = list.get(position);
        holder.nama_user_tv.setText(holder.preferences.getString("nama", null));
        holder.caption_tv.setText(m.getCaption());
        holder.tgl_postingan_tv.setText(m.getTgl_postingan());
        Glide.with(holder.foto_profile.getContext()).load("http://192.168.1.64:8000/api/user/get-foto-profile/"+holder.preferences.getInt("id", 0)).error(R.drawable.profile).into(holder.foto_profile);
        Glide.with(holder.foto_postingan.getContext()).load(m.getFoto()).error(R.drawable.upload_image).into(holder.foto_postingan);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onPostinganClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
