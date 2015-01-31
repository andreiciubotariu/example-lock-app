package com.lockapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class LockWidgetProvider extends AppWidgetProvider {

    @Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int x = 0; x < appWidgetIds.length; x++) {
            int appWidgetId = appWidgetIds[x];

            Intent i = new Intent(context, LockDeviceReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_lock);
            views.setOnClickPendingIntent(R.id.widget_imageview, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
