package com.example.administrator.httplearn;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.subsciber.IProgressDialog;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.denglu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denglu();
            }
        });

        findViewById(R.id.shangchuan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shangchuan();
            }
        });

    }

    private void denglu() {
        LoginModel model = new LoginModel();
        model.setRegion("86");
        model.setPhone("15210505062");
        model.setPassword("test123");

        EasyHttp.post("/user/login")
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(model)//这种方式会自己把对象转成json提交给服务器
                .execute(new HttpCallBack<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginModel) {
                        Log.d("xxxxxx", "onSuccess");
                    }

                    @Override
                    public void onError(ApiException e) {
                        Log.d("xxxxxx", "onError");
                    }
                });
    }

    private ProgressDialog dialog;
    private IProgressDialog mProgressDialog = new IProgressDialog() {
        @Override
        public Dialog getDialog() {
            if (dialog == null) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
                dialog.setMessage("正在上传...");
                // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
                dialog.setTitle("文件上传");
                dialog.setMax(100);
            }
            return dialog;
        }
    };

    private void shangchuan() {
//        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/1.jpg");
//
//        if (!file.exists()) {
//            return;
//        }
//
////        ProgressResponseCallBack progressResponseCallBack = new UIProgressResponseCallBack() {
////            @Override
////            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
////                int progress = (int) (bytesRead * 100 / contentLength);
////                HttpLog.i("progress=" + progress);
////                ((ProgressDialog) mProgressDialog.getDialog()).setProgress(progress);
////                if (done) {//完成
////                    ((ProgressDialog) mProgressDialog.getDialog()).setMessage("上传完整");
////                }
////            }
////        };
//
        ProgressResponseCallBack progressResponseCallBack = new ProgressResponseCallBack() {
            @Override
            public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {
                int progress = (int) (bytesWritten * 100 / contentLength);
                HttpLog.i("progress=" + progress);

                Log.d("xxxxxx", "progress:" + progress);
            }
        };
//
//        Log.d("xxxxxx", "fileName:" + file.getName() + "  " + "fileSize:" + file.length());
//
//        EasyHttp.post("/file_upload")
//                .baseUrl("http://192.168.0.80:8082")
//                .params("img", file, file.getName(), progressResponseCallBack)
//                .execute(new HttpCallBack<LoginResponse>() {
//                    @Override
//                    public void onSuccess(LoginResponse loginModel) {
//                        Log.d("xxxxxx", "onSuccess");
//                    }
//
//                    @Override
//                    public void onError(ApiException e) {
//                        Log.d("xxxxxx", "onError");
//                    }
//                });


        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/1.jpg");
//        UIProgressResponseCallBack mUIProgressResponseCallBack = new UIProgressResponseCallBack() {
//            @Override
//            public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
//                int progress = (int) (bytesRead * 100 / contentLength);
////                HttpLog.i("progress=" + progress);
//                Log.d("xxxxxx", "progress=" + progress);
//            }
//        };
        EasyHttp.post("/file_upload")
                .baseUrl("http://192.168.0.80:8082")
                .params("img", file, file.getName(), progressResponseCallBack)//这个key，img，要跟nodejs后台的一个值对应起来
                .execute(new HttpCallBack<String>() {
                    @Override
                    public void onSuccess(String message) {
                        Log.d("xxxxxx", "onSuccess:" + message);
                    }

                    @Override
                    public void onError(ApiException e) {
                        Log.d("xxxxxx", "onError");
                    }
                });


    }
}
