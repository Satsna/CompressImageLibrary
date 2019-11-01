package com.example.stasna;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Process;

import com.satsna.compressimage.compress.CompressHelper;

import java.util.List;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String curProcess = getProcessName(this, Process.myPid());
        if (!getPackageName().equals(curProcess)) {
            return;
        }
        initApplication();
    }

    /**
     * 初始化BaseApplication
     */
    private void initApplication() {
        initCompressHelper();

    }

    /**
     * 初始化图片压缩
     */
    private void initCompressHelper() {
        CompressHelper.init(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test/");
    }


    /**
     * 获取进程名
     *
     * @param cxt
     * @param pid
     * @return
     */
    public String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null && !runningApps.isEmpty()) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }
}
