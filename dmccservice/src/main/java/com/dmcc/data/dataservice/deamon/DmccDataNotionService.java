package com.dmcc.data.dataservice.deamon;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by wushange on 2016/11/11.
 */

public class DmccDataNotionService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.btn_default);
        startForeground(250, builder.build());
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                stopForeground(true);
                NotificationManager manager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.cancel(250);
                stopSelf();
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }
}
