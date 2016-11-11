package com.dmcc.data.dataservice.deamon;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.dmcc.data.dataservice.ProcessService;

/**
 * Created by wushange on 2016/11/11.
 */

public class DmccDataService extends Service {
    private MyBinder binder;
    private MyConn conn;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return binder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinder();
        if (conn == null)
            conn = new MyConn();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.btn_default);
        startForeground(250, builder.build());
        startService(new Intent(this, DmccDataNotionService.class));
        DmccDataService.this.bindService(new Intent(this, DmccDataHelperService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    class MyBinder extends ProcessService.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "I am DmccDataService";
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("Info", "DmccDataHelperService Connect successfully");
            ActivityManager activityManager = (ActivityManager) DmccDataService.this
                    .getSystemService(Context.ACTIVITY_SERVICE);


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
//            Toast.makeText(DmccDataService.this, "DmccDataHelperService  Be killed", Toast.LENGTH_SHORT).show();
            Log.i("Info", "DmccDataHelperService  Be killed");
            // 启动DmccDataService
            DmccDataService.this.startService(new Intent(DmccDataService.this, DmccDataHelperService.class));
            //绑定DmccDataService
            DmccDataService.this.bindService(new Intent(DmccDataService.this, DmccDataHelperService.class), conn, Context.BIND_IMPORTANT);
        }
    }

}
