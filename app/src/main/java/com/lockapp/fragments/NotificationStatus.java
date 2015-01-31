package com.lockapp.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.lockapp.LockDeviceReceiver;
import com.lockapp.R;

public class NotificationStatus {
    private static final int ID = 1, REQUEST_CODE = 123;
    private static NotificationStatus mInstance;

    private boolean mHasNotification = false;
    private NotificationStatus() {
    }

    private static void checkInstance() {
        if (mInstance == null) {
            mInstance = new NotificationStatus();
        }
    }
    public static boolean hasNotification() {
        checkInstance();
        return mInstance.mHasNotification;
    }

    public static void cancelNotification(Context context) {
        checkInstance();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ID);
        mInstance.mHasNotification = false;
    }

    public static void createNotification(Context context) {
        checkInstance();
        Intent lockIntent = new Intent (context, LockDeviceReceiver.class);
        PendingIntent pendIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, lockIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Lock Device")
                .setContentText("Touch to lock now")
                .setOngoing(true)
                .setShowWhen(false)
                .setSmallIcon (R.drawable.ic_stat_lock)
                .setContentIntent(pendIntent)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .build();
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify (ID, notification);
        mInstance.mHasNotification = true;
    }
}