package com.cxf.demo;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.cxf.moudule_common.Retrofit.RetrofitUtil;
import com.start.smartpos.aidl.ServiceProvider;
import com.start.smartpos.aidl.device.devicemanager.DeviceManagerProvider;

public class MyApplication extends Application {


    private static Context mContext;



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;


    }

    public static Context getmContext() {
        return mContext;
    }

}
