package com.example.teepos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.postingan.DataItem;
import com.example.teepos.datasignup.postingan.Response;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PostinganAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList listPosting = new ArrayList< DataItem >();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        swipeRefreshLayout = view.findViewById(R.id.reload_postingan);
        recyclerView = view.findViewById(R.id.postingan_list);
        adapter = new PostinganAdapter(listPosting, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataPostingan();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getDataPostingan();

    }

    private void getDataPostingan() {
        RetrofitHelper.server(getContext()).showPostingan()
        .enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                listPosting.clear();
                listPosting.addAll(response.body().getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}