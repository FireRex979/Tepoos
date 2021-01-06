package com.example.teepos.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.teepos.App;
import com.example.teepos.R;
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
        sendNotification(strTitle, strBody);
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("FCM", "onNewToken: ");
        App.asyncSendFcmToken(this, token);
    }

    private void sendNotification(String title, String body){
        String channelid = "default_channel_id";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.mipmap.ic_tepoos)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel =  new NotificationChannel(channelid, "channel", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0, notificationBuilder.build());
    }
}
