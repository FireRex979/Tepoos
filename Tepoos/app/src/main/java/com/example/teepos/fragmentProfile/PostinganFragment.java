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

import com.example.teepos.DetailPostinganSendiriActivity;
import com.example.teepos.PostinganAdapter;
import com.example.teepos.R;
import com.example.teepos.adapter.Click;
import com.example.teepos.adapter.PosinganSendiriAdapter;
import com.example.teepos.datasignup.postingan.DataItem;
import com.example.teepos.db.App;
import com.example.teepos.db.Postingan;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PostinganFragment extends Fragment implements Click {
    RecyclerView recyclerView;
    PosinganSendiriAdapter adapter;
    ArrayList listPosting = new ArrayList<Postingan>();
    CompositeDisposable mDisposable;
    public PostinganFragment() {
        // Required empty public constructor
    }

    public static PostinganFragment newInstance(String param1, String param2) {
        //
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_postingan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDisposable = new CompositeDisposable();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = view.findViewById(R.id.list_postingan);
        adapter = new PosinganSendiriAdapter(listPosting, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    private void getDataPostingan() {

        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, List<Postingan>>() {
                    @Override
                    public List<Postingan> apply(Integer integer) throws Throwable {
                        return App.db(getContext()).postinganDao().getAll();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Postingan>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<Postingan> o) {
                        listPosting.clear();
                        listPosting.addAll(o);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), "Eror", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDataPostingan();
    }

    @Override
    public void onPostinganClick(Postingan data) {
        Intent intent = new Intent(getContext(), DetailPostinganSendiriActivity.class);
        intent.putExtra("caption", data.getCaption());
        intent.putExtra("foto", data.getFoto());
        intent.putExtra("id_postingan", data.getId_postingan());
        Toast.makeText(getContext(), data.getId_postingan(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}