package com.example.teepos.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    private static Retrofit sRetrofit = null;

    private static String TAG = "retrofit";

    private static Retrofit getInstance(IRetrofitHelper callback) {
        if (sRetrofit == null) {
            // Interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String strResponse) {
                    Log.d(TAG, strResponse);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(createHeaderInterceptor(callback))
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.64:8000/api/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            sRetrofit = retrofit;
        }

        return sRetrofit;
    }

    private static Interceptor createHeaderInterceptor(IRetrofitHelper callback) {
        return new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;

                newRequest = request.newBuilder()
                        .addHeader("Authorization", "Bearer " + callback.token())
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(newRequest);
            }
        };
    }

    public static ApiService server(Context context) {
        ApiService server = getInstance(new IRetrofitHelper() {
            @Override
            public String token() {
                SharedPreferences preferences = context.getSharedPreferences("credential_login", Context.MODE_PRIVATE);
                String token = preferences.getString("token_login", "");
                return token;
            }
        }).create(ApiService.class);
        return server;
    }
}
