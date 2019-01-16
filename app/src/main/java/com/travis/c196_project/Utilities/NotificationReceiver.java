package com.travis.c196_project.Utilities;

import android.support.v4.app.NotificationCompat;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.travis.c196_project.Views.MainActivity;
import com.travis.c196_project.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        PendingIntent intent1;
        intent1 = PendingIntent.getActivity(
                context,
                0,
                new Intent(context, MainActivity.class),
                0);

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.baseline_announcement_black_18dp)
                .setContentInfo("You have a school-related event occurring today")
                .setDefaults(Notification.DEFAULT_ALL);

        mBuilder.setContentIntent(intent1);
        mBuilder.setAutoCancel(true);

        NotificationManager mManager;
        mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (mManager != null) {
            mManager.notify(1, mBuilder.build());
        }
    }
}