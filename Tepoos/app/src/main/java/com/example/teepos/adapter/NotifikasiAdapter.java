package com.example.teepos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teepos.R;
import com.example.teepos.datasignup.getnotification.DataItem;

import java.util.ArrayList;
import java.util.List;

public class NotifikasiAdapter extends RecyclerView.Adapter<NotifikasiAdapter.ViewHolder> {
    List<DataItem> list = new ArrayList();
    ClickNotif itemClick;
    public NotifikasiAdapter(List<DataItem> list, ClickNotif click){
        this.list = list;
        this.itemClick = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifikasi_item, parent, false);
        return new NotifikasiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataItem m = list.get(position);
        holder.notif_tv.setText(m.getBody());
        holder.nama_tv.setText(m.getTitle());
        holder.tgl_tv.setText(m.getTglKomentar());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onNotifClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_tv, notif_tv, tgl_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_tv = itemView.findViewById(R.id.user_name);
            notif_tv = itemView.findViewById(R.id.body_notif);
            tgl_tv = itemView.findViewById(R.id.tgl_notif);
        }
    }
}
