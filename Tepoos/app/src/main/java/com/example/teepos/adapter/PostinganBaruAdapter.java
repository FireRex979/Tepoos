package com.example.teepos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teepos.DetailPostinganOfflineActivity;
import com.example.teepos.R;
import com.example.teepos.datasignup.postinganbaru.DataItem;
import com.example.teepos.db.PostinganBaru;

import java.util.ArrayList;
import java.util.List;

public class PostinganBaruAdapter extends RecyclerView.Adapter<PostinganBaruAdapter.ViewHolder> {
    List<PostinganBaru> list = new ArrayList();
    Context context;
    public PostinganBaruAdapter(List<PostinganBaru> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postingan_item, parent, false);
        return new PostinganBaruAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostinganBaru m = list.get(position);
        holder.nama_user_tv.setText(m.getNama());
        holder.caption_tv.setText(m.getCaption());
        holder.tgl_postingan_tv.setText(m.getTgl_postingan());
        Glide.with(holder.tgl_postingan_tv.getContext()).load(R.drawable.profile).into(holder.foto_profile);
        Glide.with(holder.tgl_postingan_tv.getContext()).load(R.drawable.upload_image).error(R.drawable.upload_image).into(holder.foto_postingan);
        holder.postingan_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPostinganOfflineActivity.class);
                intent.putExtra("nama", m.getNama());
                intent.putExtra("caption", m.getCaption());
                intent.putExtra("tgl_postingan", m.getTgl_postingan());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_user_tv, caption_tv, tgl_postingan_tv;
        ImageView foto_profile, foto_postingan;
        LinearLayout postingan_show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_user_tv = itemView.findViewById(R.id.nama_user);
            caption_tv = itemView.findViewById(R.id.caption);
            tgl_postingan_tv = itemView.findViewById(R.id.tgl_postingan);
            foto_postingan = itemView.findViewById(R.id.foto_postingan);
            foto_profile = itemView.findViewById(R.id.foto_profile);
            postingan_show = itemView.findViewById(R.id.postingan_item);
        }
    }
}
