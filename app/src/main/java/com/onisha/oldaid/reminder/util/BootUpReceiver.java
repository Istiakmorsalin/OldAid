package com.onisha.oldaid.reminder.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.onisha.oldaid.reminder.activity.ReminderMainActivity;


/**
 * Created by onisha on 21-Aug-16.
 */
public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, ReminderMainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
