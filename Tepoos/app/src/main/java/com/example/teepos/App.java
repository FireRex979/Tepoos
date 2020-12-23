package com.example.teepos;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.room.Room;

import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.storeKomentar.Response;
import com.example.teepos.db.AppDatabase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;

public class App extends Application {

    private static AppDatabase db = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setupFcm(this);
    }

    public static AppDatabase db(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "db_tepos").build();
        }
        return db;
    }

    public static void setupFcm(Context context) {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String token) {
                RetrofitHelper.server(context).storeTokenFcm(
                        token
                ).enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        Log.d("FCM", "onResponse: ");
                        asyncSendFcmToken(context, token);
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        Log.d("FCM", "onFailure: ");
                    }
                });
            }
        });
    }

    public static void asyncSendFcmToken(Context context, String token) {
        RetrofitHelper.server(context).storeTokenFcm(
                token
        ).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.e("FCM", "onResponse: ");
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e("FCM", "onFailure: ", t);
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
