package com.yanjing.babyweight.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yanjing.babyweight.bean.WeightBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YanJing on 2017/08/20 0020.
 */

public class WeightDBUtil {
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    public WeightDBUtil(Context context) {
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context);
    }

    //新增数据
    public void save(WeightBean bean) {
        SQLiteDatabase db = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < bean.getWeight().size(); i++) {
            contentValues.put("week_num", bean.getWeight().get(i).getWeek_num());
            contentValues.put("BDP", bean.getWeight().get(i).getBDP());
            contentValues.put("AC", bean.getWeight().get(i).getAC());
            contentValues.put("FL", bean.getWeight().get(i).getFL());
            db.insert("weight", null, contentValues);
        }
        db.close();

    }


    //查询数据
    public List<WeightBean> getData(String week_num) {
        SQLiteDatabase db = mySQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("weight", new String[]{"BDP", "AC", "FL"}, "week_num=?", new String[]{week_num}, null, null, null);
        List<WeightBean> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                WeightBean bean = new WeightBean();
                String BDP = cursor.getString(cursor.getColumnIndex("BDP"));
                String AC = cursor.getString(cursor.getColumnIndex("AC"));
                String FL = cursor.getString(cursor.getColumnIndex("FL"));

                bean.setWeek_num(week_num);
                bean.setBDP(BDP);
                bean.setAC(AC);
                bean.setFL(FL);
                list.add(bean);
            }
        }

        return list;
    }

}
