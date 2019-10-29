package com.satsna.compressimage.compress;

import java.io.File;
import java.util.List;

/**
 * 图片压缩回调方法
 */
public class CompressCallBack {

    /**
     * 图片全部压缩完成
     *
     * @param compressList
     */
    public void onFinish(List<File> compressList) {

    }

    /**
     * 压缩之前
     */
    public void onStart() {

    }

    /**
     * 压缩成功
     *
     * @param file 压缩成功的文件
     */
    public void onSuccess(File file) {

    }

    /**
     * 压缩异常
     *
     * @param e
     */
    public void onError(Throwable e) {

    }
}
