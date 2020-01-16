package com.satsna.compressimage.compress;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import com.satsna.compressimage.util.FileUtils;
import com.satsna.compressimage.util.PermissionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 图片压缩帮助类
 * 使用前请在Application中调用CompressHelper.init方法初始化
 */
public class CompressHelper {

    //图片压缩文件夹
    public static String COPPRESS_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/compress/";

    //全局ApplicationContext;
    private static Context context;
    //压缩后的图片集合
    private List<File> compressList = new ArrayList<>();
    //未压缩的图片集合
    private List<String> originalList;
    //压缩监听
    CompressCallBack compressCallBack;
    //是否出现失败,多张图片如果有一个失败,这认为全部失败,直接回调onError方法,由于onError方法会回调多次,故加此变量,仅限回调一次
    private boolean error = false;
    private static boolean autoClear = true;//是否自动清除压缩缓存文件
    //100K以下不进行压缩
    private static int ignoreSize = 100;

    public CompressHelper() {
    }

    public CompressHelper(List<String> originalList, CompressCallBack compressCallBack) {
        this.originalList = originalList;
        this.compressCallBack = compressCallBack;
        compress();
    }

    /**
     * Application中初始化
     *
     * @param context
     */
    public static void init(Context context) {
        CompressHelper.context = context;

        File file = new File(COPPRESS_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (autoClear) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (PermissionUtils.isGranted(context, permissions)) {
                FileUtils.deleteAllInDir(COPPRESS_PATH);
            }
        }

    }

    /**
     * Application中初始化
     *
     * @param context
     * @param compressPath 指定压缩文件夹
     */
    public static void init(Context context, String compressPath) {
        CompressHelper.COPPRESS_PATH = compressPath;
        init(context);
    }

    /**
     * Application中初始化
     *
     * @param context
     * @param compressPath 指定压缩文件夹
     * @param autoClear    是否自动清除压缩缓存
     */
    public static void init(Context context, String compressPath, boolean autoClear) {
        CompressHelper.COPPRESS_PATH = compressPath;
        CompressHelper.autoClear = autoClear;
        init(context);
    }


    public static void getDefault(List<String> originalList, CompressCallBack compressCallBack) {
        new CompressHelper(originalList, compressCallBack);
    }

    public static void getDefault(String filePath, CompressCallBack compressCallBack) {
        List<String> originalList = new ArrayList<>();
        originalList.add(filePath);
        new CompressHelper(originalList, compressCallBack);
    }

    public void compress() {
        compressList.clear();

        OnCompressListener listenter = new OnCompressListener() {
            @Override
            public void onStart() {
                if (compressCallBack != null) compressCallBack.onStart();
            }

            @Override
            public void onSuccess(File file) {
                if (compressCallBack != null) compressCallBack.onSuccess(file);

                compressList.add(file);
                //判断是否全部完成压缩
                if (compressList.size() == originalList.size()) {
                    if (compressCallBack != null) compressCallBack.onFinish(compressList);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (!error && compressCallBack != null) {
                    error = true;
                    compressCallBack.onError(e);
                }
            }
        };
        Luban.with(context).ignoreBy(ignoreSize).load(originalList).setTargetDir(COPPRESS_PATH).setCompressListener(listenter).launch();
    }

    public static boolean isAutoClear() {
        return autoClear;
    }

    public static void setAutoClear(boolean autoClear) {
        CompressHelper.autoClear = autoClear;
    }

    public static int getIgnoreSize() {
        return ignoreSize;
    }

    public static void setIgnoreSize(int ignoreSize) {
        CompressHelper.ignoreSize = ignoreSize;
    }


    public static String getCoppressPath() {
        return COPPRESS_PATH;
    }

    public static void setCoppressPath(String coppressPath) {
        COPPRESS_PATH = coppressPath;
    }
}
