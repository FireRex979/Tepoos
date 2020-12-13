package com.example.teepos;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.teepos.datasignup.postingan.DataItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.concurrent.Task;

public class PostinganAdapter extends RecyclerView.Adapter<PostinganAdapter.ViewHolder> {

    List<DataItem> list = new ArrayList();
    Context context;
    public PostinganAdapter(List<DataItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.postingan_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem m = list.get(position);
        holder.nama_user_tv.setText(m.getUser().getName());
        holder.caption_tv.setText(m.getCaption());
        holder.tgl_postingan_tv.setText(m.getTglPostingan());
        holder.komentar_tv.setText(String.format("%d Komentar", m.getKomentar().size()));
        Glide.with(holder.komentar_tv.getContext()).load(m.getUser().getFotoProfile()).into(holder.foto_profile);
        Glide.with(holder.komentar_tv.getContext()).load(m.getFoto()).into(holder.foto_postingan);

        holder.postingan_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fungsi
                int id = m.getId();
                Intent toShowPostingan = new Intent(context, DetailPostinganActivity.class);
                toShowPostingan.putExtra("id", id);
                toShowPostingan.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(toShowPostingan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_user_tv, caption_tv, komentar_tv, tgl_postingan_tv;
        ImageView foto_profile, foto_postingan;
        LinearLayout postingan_show;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_user_tv = itemView.findViewById(R.id.nama_user);
            caption_tv = itemView.findViewById(R.id.caption);
            tgl_postingan_tv = itemView.findViewById(R.id.tgl_postingan);
            komentar_tv = itemView.findViewById(R.id.komentar);
            foto_postingan = itemView.findViewById(R.id.foto_postingan);
            foto_profile = itemView.findViewById(R.id.foto_profile);
            postingan_show = itemView.findViewById(R.id.postingan_item);
        }
    }

    public void showPostingan(DataItem list){

    }

}
