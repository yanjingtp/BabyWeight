package com.yanjing.babyweight.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yanjing.babyweight.R;
import com.yanjing.babyweight.bean.WeightBean;
import com.yanjing.babyweight.utils.MyURL;
import com.yanjing.babyweight.utils.WeightDBUtil;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends Activity {

    @BindView(R.id.btn_go)
    Button btnGo;

    TimeThread timeThread = new TimeThread();
    private boolean isRun = true;

    private String json = "{" +
            "    \"weight\": [" +
            "        {" +
            "            \"AC\": \"69±16.5\"," +
            "            \"BDP\": \"25.2±2.5\"," +
            "            \"FL\": \"11.7±3.1\"," +
            "            \"week_num\": \"13\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"77.7±18.2\"," +
            "            \"BDP\": \"28.3±5.7\"," +
            "            \"FL\": \"13.8±4.8\"," +
            "            \"week_num\": \"14\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"91.3±15.6\"," +
            "            \"BDP\": \"32.3±5.1\"," +
            "            \"FL\": \"17.4±5.8\"," +
            "            \"week_num\": \"15\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"103.2±19.2\"," +
            "            \"BDP\": \"36.2±5.8\"," +
            "            \"FL\": \"21±5.1\"," +
            "            \"week_num\": \"16\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"114.9±16.2\"," +
            "            \"BDP\": \"39.7±4.4\"," +
            "            \"FL\": \"25.2±4.4\"," +
            "            \"week_num\": \"17\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"124.1±0\"," +
            "            \"BDP\": \"42.5±5.3\"," +
            "            \"FL\": \"27.1±4.6\"," +
            "            \"week_num\": \"18\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"135.9±23\"," +
            "            \"BDP\": \"45.2±5.3\"," +
            "            \"FL\": \"30.3±5\"," +
            "            \"week_num\": \"19\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"148±0\"," +
            "            \"BDP\": \"48.8±5.8\"," +
            "            \"FL\": \"33.5±4.7\"," +
            "            \"week_num\": \"20\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"156.2±18.4\"," +
            "            \"BDP\": \"52.2±4.2\"," +
            "            \"FL\": \"36.4±4\"," +
            "            \"week_num\": \"21\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"167±22.3\"," +
            "            \"BDP\": \"54.5±5.7\"," +
            "            \"FL\": \"38.2±4.7\"," +
            "            \"week_num\": \"22\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"179±18.5\"," +
            "            \"BDP\": \"58±4.4\"," +
            "            \"FL\": \"42.1±4.1\"," +
            "            \"week_num\": \"23\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"187.4±22.3\"," +
            "            \"BDP\": \"60.5±5\"," +
            "            \"FL\": \"43.6±5.1\"," +
            "            \"week_num\": \"24\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"196.4±22\"," +
            "            \"BDP\": \"63.9±7\"," +
            "            \"FL\": \"46.5±4.2\"," +
            "            \"week_num\": \"25\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"216.2±23\"," +
            "            \"BDP\": \"66.8±6.1\"," +
            "            \"FL\": \"48.7±4.1\"," +
            "            \"week_num\": \"26\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"218.1±21.2\"," +
            "            \"BDP\": \"69.8±5.7\"," +
            "            \"FL\": \"51±4.1\"," +
            "            \"week_num\": \"27\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"228.6±24.1\"," +
            "            \"BDP\": \"72.4±6.5\"," +
            "            \"FL\": \"53.5±5.5\"," +
            "            \"week_num\": \"28\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"237.1±15\"," +
            "            \"BDP\": \"75±6.5\"," +
            "            \"FL\": \"56.1±4.4\"," +
            "            \"week_num\": \"29\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"248.8±20.3\"," +
            "            \"BDP\": \"78.3±6.2\"," +
            "            \"FL\": \"57.7±4.7\"," +
            "            \"week_num\": \"30\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"257.8±23.2\"," +
            "            \"BDP\": \"80.6±6\"," +
            "            \"FL\": \"60.3±3.8\"," +
            "            \"week_num\": \"31\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"262±23.3\"," +
            "            \"BDP\": \"81.7±6.5\"," +
            "            \"FL\": \"64.3±4.9\"," +
            "            \"week_num\": \"32\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"277.8±23\"," +
            "            \"BDP\": \"85±4.7\"," +
            "            \"FL\": \"65.2±4.6\"," +
            "            \"week_num\": \"33\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"279.9±25.5\"," +
            "            \"BDP\": \"86.1±6.3\"," +
            "            \"FL\": \"66.2±4.3\"," +
            "            \"week_num\": \"34\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"287.4±28.8\"," +
            "            \"BDP\": \"87±5.5\"," +
            "            \"FL\": \"67.1±4.5\"," +
            "            \"week_num\": \"35\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"294.4±28.3\"," +
            "            \"BDP\": \"88.1±5.7\"," +
            "            \"FL\": \"69.5±4.7\"," +
            "            \"week_num\": \"36\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"301.4±21.7\"," +
            "            \"BDP\": \"90±6.3\"," +
            "            \"FL\": \"71±5.2\"," +
            "            \"week_num\": \"37\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"306.3±28.3\"," +
            "            \"BDP\": \"90.8±5.9\"," +
            "            \"FL\": \"72±4.3\"," +
            "            \"week_num\": \"38\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"313.4±31.2\"," +
            "            \"BDP\": \"92.1±5.9\"," +
            "            \"FL\": \"73.4±5.3\"," +
            "            \"week_num\": \"39\"" +
            "        }," +
            "        {" +
            "            \"AC\": \"314.9±27.9\"," +
            "            \"BDP\": \"92.8±5\"," +
            "            \"FL\": \"74±5.3\"," +
            "            \"week_num\": \"40\"" +
            "        }" +
            "    ]" +
            "}";

    private WeightDBUtil dbUtil;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        dbUtil = new WeightDBUtil(this);

        sp = getSharedPreferences("weight", MODE_PRIVATE);
        if (sp.getBoolean("isFirst", true)) {
            initJson();

        }


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

    private void initJson() {
        RequestQueue queue = Volley.newRequestQueue(SplashActivity.this);
        StringRequest request = new StringRequest(MyURL.weightInfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                WeightBean bean = gson.fromJson(response, WeightBean.class);

                dbUtil.save(bean);
                sp.edit().putBoolean("isFirst", false).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SplashActivity.this,"数据加载错误，请稍后重试……",Toast.LENGTH_SHORT).show();
                sp.edit().putBoolean("isFirst", true).commit();
            }
        });
        queue.add(request);

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
