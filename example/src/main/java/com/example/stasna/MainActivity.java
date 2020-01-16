package com.example.stasna;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.satsna.compressimage.compress.CompressCallBack;
import com.satsna.compressimage.compress.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public Context context = this;
    public static final String coppress_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test_img";
    String testImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test/test.jpg";
    List<String> imgPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CompressHelper.setIgnoreSize(900);
        CompressHelper.setCoppressPath(coppress_path);
    }

    public void test(View v) {
        Log.e("==========", testImagePath);
        if (new File(testImagePath).exists()) {
            Log.e("==========", "图片存在");
        } else {
            Log.e("==========", "图片不存在");
        }

        imgPathList.add(testImagePath);
        CompressHelper.getDefault(imgPathList, new CompressCallBack() {
            @Override
            public void onFinish(List<File> compressList) {
                Log.e("==========", "压缩完成=" + compressList.get(0).getAbsolutePath());
                Toast.makeText(context, "压缩完成", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e("==========", "压缩失败=" + e.getMessage());
            }
        });

    }
}
