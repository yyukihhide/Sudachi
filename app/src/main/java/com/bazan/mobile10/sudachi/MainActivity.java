package com.bazan.mobile10.sudachi;

import android.content.Intent;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private LinearLayout backgroundLayout;
    private ImageView tree;
    private ImageView bug;
    private FloatingActionButton fab;

    private SudachiKun sudachiKun;


    private int w;
    private int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        w = point.x;
        h = point.y;

        backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);
        backgroundLayout.setBackgroundResource(R.drawable.background_yuki_yoru);
        tree = (ImageView) findViewById(R.id.maimvtree);
        tree.setScaleX(0.5F);
        tree.setScaleY(0.5F);
        tree.setBackgroundResource(R.drawable.tree);
        bug = (ImageView) findViewById(R.id.maimvbug);
        bug.setScaleX(0.2F);
        bug.setScaleY(0.2F);
        bug.setBackgroundResource(R.drawable.tree2);


        fab = (FloatingActionButton) findViewById(R.id.mafab);
        fab.setOnClickListener(this);

//        sudachiKun = new SudachiKun(this);
//        sudachiKun.setOnTouchListener(this);
//        LinearLayout.LayoutParams sudachiParam = new LinearLayout.LayoutParams(dp2px(200), dp2px(200));
        //addContentView(sudachiKun, sudachiParam);


    }

    /**
     * dpの値をpxに変換する
     *
     * @param dp
     * @return px
     */
    private int dp2px(int dp) {
        float scale = getResources().getDisplayMetrics().density;
        int px = (int) (dp * scale);
        return px;
    }

    /**
     * pxの値をdpに変換する
     *
     * @param px
     * @return dp
     */
    private int px2dp(int px) {
        float scale = getResources().getDisplayMetrics().density;
        int dp = (int) (px / scale);
        return dp;
    }


    private float x;
    private float y;
    private float vx;
    private float vy;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String currAction = "";
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currAction = "down";
                break;
            case MotionEvent.ACTION_MOVE:
                currAction = "move";
                break;
            case MotionEvent.ACTION_UP:
                currAction = "up";
                break;
            case MotionEvent.ACTION_CANCEL:
                currAction = "cancel";
                break;
        }
        Log.e("evetnt", currAction);

        int touchPointCount = event.getPointerCount();
        for (int i = 0; i < touchPointCount; i++) {
            Log.e(currAction + " touch[" + i + "]", "(" + event.getX(i) + "," + event.getY(i) + ")");

        }

        vx = event.getX(0);
        vy = event.getY(0);
        x = x - ((h / 4) / 2) + vx;
        y = y - ((h / 2) / 2) + vy;
        sudachiKun.setTranslationX(x);
        sudachiKun.setTranslationY(y);


        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == fab){
            Intent intent = new Intent(this, BugGameActivity.class);
            startActivity(intent);
        }
    }
}
