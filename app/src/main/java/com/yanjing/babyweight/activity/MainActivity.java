package com.yanjing.babyweight.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yanjing.babyweight.R;
import com.yanjing.babyweight.bean.WeightBean;
import com.yanjing.babyweight.utils.WeightDBUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_week)
    TextView tvWeek;
    @BindView(R.id.et_BDP)
    EditText etBDP;
    @BindView(R.id.tv_this_BDP)
    TextView tvThisBDP;
    @BindView(R.id.et_AC)
    EditText etAC;
    @BindView(R.id.tv_this_AC)
    TextView tvThisAC;
    @BindView(R.id.et_FL)
    EditText etFL;
    @BindView(R.id.tv_this_FL)
    TextView tvThisFL;
    @BindView(R.id.btn_result)
    Button btnResult;

    private SharedPreferences sp;
    private WeightDBUtil dbUtil;
    private List<WeightBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tvWeek.setOnClickListener(new MyOnClickLister());
        btnResult.setOnClickListener(new MyOnClickLister());

        sp = getSharedPreferences("week", MODE_PRIVATE);
        dbUtil = new WeightDBUtil(this);

        String week = sp.getString("current_week", "13");
        tvWeek.setText("第" + week + "周");

        list = dbUtil.getData(week);
        if (list.size() > 0) {
            tvThisBDP.setText("当周参考值:" + list.get(0).getBDP().replace("||", "±") + "mm");
            tvThisAC.setText("当周参考值:" + list.get(0).getAC().replace("||", "±") + "mm");
            tvThisFL.setText("当周参考值:" + list.get(0).getFL().replace("||", "±") + "mm");
        }


    }

    private class MyOnClickLister implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_week:
                    //创建输入弹出窗
                    final AlertDialog dialogEdit = new AlertDialog.Builder(MainActivity.this).setPositiveButton("确定", null)
                            .setNegativeButton("取消", null).create();
                    View editView = View.inflate(MainActivity.this, R.layout.layout_dialog_edit, null);
                    final EditText etWeek = editView.findViewById(R.id.et_week);
                    dialogEdit.setView(editView);

                    dialogEdit.setCancelable(false);
                    dialogEdit.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            Button btnPositive = dialogEdit.getButton(AlertDialog.BUTTON_POSITIVE);
                            Button btnNegative = dialogEdit.getButton(AlertDialog.BUTTON_NEGATIVE);

                            btnPositive.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String week = etWeek.getText().toString();
                                    if (!week.matches("\\d+") || Integer.parseInt(week) > 40 || Integer.parseInt(week) < 13) {
                                        Toast.makeText(MainActivity.this, "请输入正确的孕周数", Toast.LENGTH_SHORT).show();
                                    } else {
                                        tvWeek.setText("第" + week + "周");
                                        sp.edit().putString("current_week", week).commit();

                                        //刷新显示参考值
                                        list.clear();
                                        list.addAll(dbUtil.getData(week));
                                        if (list.size() > 0) {
                                            tvThisBDP.setText("当周参考值:" + list.get(0).getBDP().replace("||", "±") + "mm");
                                            tvThisAC.setText("当周参考值:" + list.get(0).getAC().replace("||", "±") + "mm");
                                            tvThisFL.setText("当周参考值:" + list.get(0).getFL().replace("||", "±") + "mm");
                                        }

                                        dialogEdit.dismiss();
                                    }

                                }
                            });

                            btnNegative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogEdit.dismiss();
                                }
                            });
                        }
                    });

                    dialogEdit.show();


                    break;
                case R.id.btn_result:
                    String BDP = etBDP.getText().toString();    //双顶径
                    String AC = etAC.getText().toString();      //腹围
                    String FL = etFL.getText().toString();      //股骨长

                    if (!BDP.matches("\\d+\\.?\\d*") || !AC.matches("\\d+\\.?\\d*") || !FL.matches("\\d+\\.?\\d*")) {
                        Toast.makeText(MainActivity.this, "请输入正确的值", Toast.LENGTH_SHORT).show();
                    } else {

                        double result = 1.07 * Math.pow(Double.parseDouble(BDP), 3) + 0.3 * Math.pow(Double.parseDouble(AC), 2) * Double.parseDouble(FL);

                        //创建输出结果窗
                        final AlertDialog dialogShow = new AlertDialog.Builder(MainActivity.this).setPositiveButton("确定", null).create();
                        View dialogView = View.inflate(MainActivity.this, R.layout.layout_dialog_show, null);
                        TextView tvWeight = dialogView.findViewById(R.id.tv_weight);
                        dialogShow.setView(dialogView);

                        dialogShow.setCancelable(false);
                        dialogShow.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                //确定按键
                                Button btnPositive = dialogShow.getButton(AlertDialog.BUTTON_POSITIVE);
                                btnPositive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialogShow.dismiss();
                                    }
                                });
                            }
                        });

                        tvWeight.setText(String.format("%.2f", result * 2 / 1000 / 1000) + "斤/" + String.format("%.2f", result / 1000 / 1000) + "公斤");
                        dialogShow.show();
                    }


                    break;
            }

        }
    }
}
