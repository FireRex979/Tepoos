package com.example.teepos.fragmentProfile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.teepos.DetailPostinganActivity;
import com.example.teepos.DetailPostinganSendiriActivity;
import com.example.teepos.R;
import com.example.teepos.adapter.Click;
import com.example.teepos.adapter.ClickNotif;
import com.example.teepos.adapter.NotifikasiAdapter;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.ReadNotif;
import com.example.teepos.datasignup.getnotification.DataItem;
import com.example.teepos.datasignup.getnotification.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class NotifikasiFragment extends Fragment implements ClickNotif {
    RecyclerView recyclerView;
    NotifikasiAdapter adapter;
    ArrayList list_notif = new ArrayList<DataItem>();
    public NotifikasiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifikasi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotifikasiAdapter(list_notif, this);
        recyclerView = view.findViewById(R.id.notif_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        getNotification();
    }

    private void getNotification() {
        RetrofitHelper.server(getContext()).getNotif(

        ).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                list_notif.clear();
                list_notif.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
                recyclerView.requestLayout();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNotifClick(DataItem m) {
        int id = m.getId();
        RetrofitHelper.server(getContext()).readNotif(
                id
        ).enqueue(new Callback<ReadNotif>() {
            @Override
            public void onResponse(Call<ReadNotif> call, retrofit2.Response<ReadNotif> response) {
                if(response.body().isSuccess()){
                    Intent intent = new Intent(getContext(), DetailPostinganActivity.class);
                    intent.putExtra("id", m.getIdPostingan());
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Postingan Tidak Ada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReadNotif> call, Throwable t) {
                Toast.makeText(getContext(), "Postingan Tidak Ada", Toast.LENGTH_SHORT).show();
            }
        });
    }
}