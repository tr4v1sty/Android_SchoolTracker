package com.travis.c196_project.Utilities;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.travis.c196_project.Views.MainActivity;
import com.travis.c196_project.R;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "Notification set", Toast.LENGTH_SHORT).show();

        createNotification(context);

    }

    private void createNotification(Context context) {

        PendingIntent mIntent;
        mIntent = PendingIntent.getActivity(context,
                0,
                new Intent(context, MainActivity.class),
                0);

        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.baseline_announcement_black_18dp)
                .setContentTitle("Alert From WGU")
                .setContentText("Important Date Event")
                .setContentInfo("Please check the scheduler for an important date event.")
                .setDefaults(Notification.DEFAULT_ALL);

        mBuilder.setContentIntent(mIntent);
        mBuilder.setAutoCancel(true);

        NotificationManager mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mManager.notify(1, mBuilder.build());

    }
}