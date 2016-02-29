package com.bazan.mobile10.sudachi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ponuki on 2016/02/26.
 */
public class BugGameActivity extends AppCompatActivity {

    //private static DatabaseC dbC;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(new BugGameView2(this));
        //setContentView(new SudachiGameView(this));
        setContentView(new BearGameView(this));
    }

//    public static void saveData(int score) {
//        dbC = new DatabaseC(MainActivity.getDbHelper());
//        //スコアの保存
//        String[] dataSet = new String[3];
//        if (dbC.readConfigValue("bugflag").equals("1")) {
//            dataSet = new String[]{"bug", String.valueOf(score), "2"};
//        } else if (dbC.readConfigValue("bearflag").equals("1")) {
//            dataSet = new String[]{"bear", String.valueOf(score), "2"};
//        } else if (dbC.readConfigValue("sudachiflag").equals("1")) {
//            dataSet = new String[]{"sudachi", String.valueOf(score), "2"};
//        }
//        dbC.insertScore(dataSet);
//    }
}
