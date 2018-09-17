package com.onisha.oldaid.reminder.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;

import com.onisha.oldaid.R;
import com.onisha.oldaid.reminder.activity.ShowReminderMessage;

import java.util.Locale;

/**
 * Created by onisha on 17-Aug-16.
 */
public class TimeAlarm extends BroadcastReceiver {

    private NotificationManager nm;
    private String event;
    private Notification noti;

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle extras = intent.getExtras();
        event = extras.getString("event");

        Bitmap bigIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // when you click on the notification
        Intent intent2 = new Intent(context, ShowReminderMessage.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent2.putExtra("ReminderMsg", event);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 1, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.small_notify_icon)
                // .setLargeIcon(bigIcon)
                .setColor(46254)
                .setAutoCancel(true)
                .setContentTitle("Reminder")
                .setContentText(event)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent intent1 = new Intent(context, TTS.class);
        intent1.putExtra("ReminderMsg", event);
        context.startService(intent1);

        notificationManager.notify(0, notificationBuilder.build());




    }
}

