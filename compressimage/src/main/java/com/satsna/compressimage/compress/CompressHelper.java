package com.satsna.compressimage.compress;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
                if (compressCallBack != null) compressCallBack.onError(e);
            }
        };
        Luban.with(context).load(originalList).setTargetDir(COPPRESS_PATH).setCompressListener(listenter).launch();
    }
}
