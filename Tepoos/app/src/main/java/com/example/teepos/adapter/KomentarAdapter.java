package com.example.teepos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teepos.R;
import com.example.teepos.datasignup.showPostingan.KomentarItem;

import java.util.ArrayList;
import java.util.List;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {
    List<KomentarItem> list = new ArrayList();

    public KomentarAdapter(List<KomentarItem> list) {
        this.list = list;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.komentar_list, parent, false);
        return new KomentarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KomentarItem m = list.get(position);
        holder.komenter_tv.setText(m.getKomentar());
        holder.nama_user_tv.setText(m.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_user_tv, komenter_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_user_tv = itemView.findViewById(R.id.nama_user_komentar);
            komenter_tv = itemView.findViewById(R.id.komentar);
        }
    }
}
