package com.yanjing.babyweight.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yanjing.babyweight.MainActivity;
import com.yanjing.babyweight.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends Activity {

    @BindView(R.id.btn_go)
    Button btnGo;

    TimeThread timeThread = new TimeThread();
    private boolean isRun = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        btnGo.getBackground().setAlpha(100);
        btnGo.setText("跳过3");

        timeThread.start();   //启动线程

        // 自动跳转到主页面
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isRun) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        }, 3200);

        //手动跳转
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                isRun = false;  //停止倒计时线程
                finish();
            }
        });


    }

    //    每秒显示一次
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            btnGo.setText("跳过" + String.valueOf(msg.what));
        }
    };


    //    倒计时线程
    class TimeThread extends Thread {

        @Override
        public void run() {
            super.run();
            for (int i = 2; i >= 0; i--) {
                //  休眠1秒
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Message msg = Message.obtain();
                msg.what = i;
                myHandler.sendMessage(msg);

                if (!isRun) {
                    break;
                }

            }


        }
    }


}
