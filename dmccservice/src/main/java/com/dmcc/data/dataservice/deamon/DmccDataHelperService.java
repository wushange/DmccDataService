package com.dmcc.data.dataservice.deamon;

import android.annotation.TargetApi;
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

public class DmccDataHelperService extends Service {
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
        DmccDataHelperService.this.bindService(new Intent(this, DmccDataService.class), conn, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    class MyBinder extends ProcessService.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return "I am DmccDataHelperService";
        }
    }

    class MyConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("Info", "DmccDataHelperService Connect successfully" + DmccDataHelperService.this.getApplicationInfo().processName);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
//            Toast.makeText(DmccDataHelperService.this, "DmccDataService Be killed", Toast.LENGTH_SHORT).show();
            Log.i("Info", "DmccDataService Be killed");
            // Start DmccDataService
            DmccDataHelperService.this.startService(new Intent(DmccDataHelperService.this, DmccDataService.class));
            //Bind DmccDataService
            DmccDataHelperService.this.bindService(new Intent(DmccDataHelperService.this, DmccDataService.class), conn, Context.BIND_IMPORTANT);
        }
    }
}
