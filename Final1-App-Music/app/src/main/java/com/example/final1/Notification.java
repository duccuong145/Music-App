package com.example.final1;

import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;



public abstract class Notification extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final String CHANNEL_NAME = "My Channel";


    private static final int NOTIFICATION_ID = 1;


    private static final int PERMISSION_REQUEST_CODE = 112;

    private Handler handler = new Handler();


    private Runnable runnable = new Runnable() {
        @SuppressLint("MissingPermission")
        @Override
        public void run() {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(Notification.this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Music Mp3")
                    .setContentText("Hello")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            Intent intent = new Intent(Notification.this, Notification.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(Notification.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


            builder.setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Notification.this);


            notificationManager.notify(NOTIFICATION_ID, builder.build());
            handler.removeCallbacksAndMessages(null);

        }
    };


    public void requestNotificationPermission() {

        if (Build.VERSION.SDK_INT >= 33) {
            // Check if the permission is already granted
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            } else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, POST_NOTIFICATIONS)) {

                } else {

                    ActivityCompat.requestPermissions(this, new String[] {POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestNotificationPermission();
        createNotificationChannel();
    }

    @Override
    protected void onStop() {
        super.onStop();


        handler.postDelayed(runnable, 5000);
    }


    private void createNotificationChannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);

            
            notificationManager.createNotificationChannel(channel);
        }
    }
}

