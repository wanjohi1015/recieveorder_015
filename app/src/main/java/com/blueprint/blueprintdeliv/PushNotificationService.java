package com.blueprint.blueprintdeliv;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class PushNotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("token", s);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("DeviceTokens").document().set(tokenData);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = Objects.requireNonNull(remoteMessage.getNotification()).getTitle();
        String body = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID = "HEADS_UP_NOTIFICATIONS";
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "MyNotification",
                NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_cart_icon)
                .setAutoCancel(true);
        NotificationManagerCompat.from(this).notify(1, notification.build());

    }

}
