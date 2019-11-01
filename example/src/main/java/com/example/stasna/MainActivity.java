package com.example.stasna;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.satsna.compressimage.compress.CompressCallBack;
import com.satsna.compressimage.compress.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String coppress_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test_img";
    String testImagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test/test.jpg";
    List<String> imgPathList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test();
    }

    public void test() {
        String a=Environment.getExternalStorageDirectory().getAbsolutePath() + "/1test";
        File f=new File(a);
        if(f.exists()){
            File[] b = f.listFiles();
        }
        File[] b = f.listFiles();

        for (int i = 0; i < 20; i++) {
            imgPathList.add(testImagePath);
        }
        CompressHelper.getDefault(imgPathList, new CompressCallBack() {
            @Override
            public void onFinish(List<File> compressList) {
                Log.e("", "压缩完成");
            }
        });

    }
}
