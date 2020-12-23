package com.example.teepos.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.teepos.App;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FcmReceiver extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d("FCM", "onMessageReceived: ");

        RemoteMessage.Notification dataFcmNotification = remoteMessage.getNotification();
        Map<String, String> dataFcm = remoteMessage.getData();

        String strTitle = dataFcmNotification.getTitle();
        String strBody = dataFcmNotification.getBody();

        String idPostingan = dataFcm.get("id_postingan");
        String nama = dataFcm.get("nama");

        Log.d("FCM", "strTitle: " + strTitle);
        Log.d("FCM", "strBody: " + strBody);
        Log.d("FCM", "idPostingan: " + idPostingan);
        Log.d("FCM", "nama: " + nama);

        // TODO: refresh recycler view
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("FCM", "onNewToken: ");
        App.asyncSendFcmToken(this, token);
    }
}
