package com.app.eventbusservicetoactivity.Services;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.app.eventbusservicetoactivity.Activity.MainActivity;
import com.app.eventbusservicetoactivity.Model.EventModel;
import com.app.eventbusservicetoactivity.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MyService extends Service {
    private static final String CHANNEL_ID = "channel id";
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    public static final String COUNTDOWN_BR = "your_package_name.countdown_br";
    public static final String CHARGER_UNCHARGE = "battery_charger_uncharger";
    Intent bi = new Intent(COUNTDOWN_BR);
    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        EventModel eventModel=new EventModel();
        eventModel.setEventid("1");
        eventModel.setEventname("Home");
        eventModel.setEventmessage("this is home event");
        EventBus.getDefault().postSticky(eventModel);
        createAndShowForegroundNotification(this, 12);
        super.onCreate();
    }



    @Override
    public void onDestroy() {
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(EventModel event) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private void createAndShowForegroundNotification(Service yourService, int notificationId) {

        final NotificationCompat.Builder builder = getNotificationBuilder(yourService,
                "com.example.your_app.notification.CHANNEL_ID_FOREGROUND", // Channel id
                NotificationManagerCompat.IMPORTANCE_LOW); //Low importance prevent visual appearance for this notification channel on top
        builder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(yourService.getString(R.string.title)).setContentIntent(
                PendingIntent.getActivity(
                       getApplicationContext(),
                        0,
                        new Intent(getApplicationContext(), MainActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentText(yourService.getString(R.string.content));

        Notification notification = builder.build();
        yourService.startForeground(notificationId, notification);

    }

    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    @TargetApi(26)
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String description = context.getString(R.string.notifications_channel_description);
        final NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

        if (nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);
                nm.createNotificationChannel(nChannel);
            }
        }
    }


}
