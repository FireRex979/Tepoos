package com.example.teepos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import android.widget.Toast;

import com.example.teepos.adapter.ClickPostinganBaru;
import com.example.teepos.adapter.PostinganBaruAdapter;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.postingan.DataItem;
import com.example.teepos.datasignup.postingan.Response;
import com.example.teepos.db.Postingan;
import com.example.teepos.db.PostinganBaru;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    PostinganAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList listPosting = new ArrayList< DataItem >();
    ArrayList listPostingLokal = new ArrayList<PostinganBaru>();
    PostinganBaruAdapter adapter_lokal;
    CompositeDisposable mDisposable;
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
        mDisposable = new CompositeDisposable();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        swipeRefreshLayout = view.findViewById(R.id.reload_postingan);
        recyclerView = view.findViewById(R.id.postingan_list);
        adapter = new PostinganAdapter(listPosting, getContext());
        adapter_lokal = new PostinganBaruAdapter(listPostingLokal, getContext());
        recyclerView.setLayoutManager(layoutManager);
        if(isNetworkConnected()){
            recyclerView.setAdapter(adapter);
        }else{
            recyclerView.setAdapter(adapter_lokal);
        }
//        recyclerView.setAdapter(adapter);
//        recyclerView.setAdapter(adapter_lokal);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkConnected()){
                    getDataPostingan();
                }else{
                    getDataPostinganLokal();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if(isNetworkConnected()){
            getDataPostingan();
        }else{
            getDataPostinganLokal();
        }
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

    private void getDataPostinganLokal(){
        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, List<PostinganBaru>>() {
                    @Override
                    public List<PostinganBaru> apply(Integer integer) throws Throwable {
                        return App.db(getContext()).postinganBaruDao().getAll();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PostinganBaru>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<PostinganBaru> o) {
                        listPostingLokal.clear();
                        listPostingLokal.addAll(o);
                        adapter_lokal.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), "Eror", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}