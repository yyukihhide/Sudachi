package com.bazan.mobile10.sudachi;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;


public class SudachiKun extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    //
    private Bitmap[] image = new Bitmap[3];
    private SurfaceHolder holder;
    private Thread thread;

    private int px = 0;
    private int py = 0;
    //そくど
    private int vx = 6;
    private int vy = 6;

    private Context mContext;

    public SudachiKun(Context context) {
        super(context);
        mContext = context;
        //画像の読み込み
        Resources r = getResources();
        image[0] = BitmapFactory.decodeResource(r, R.drawable.sudachikun1);
        image[1] = BitmapFactory.decodeResource(r, R.drawable.sudachikun2);
        image[2] = BitmapFactory.decodeResource(r, R.drawable.sudachikun3);
        //サーフェイスフォルダーの準備
        holder = getHolder();
        holder.addCallback(this);


        //ビューの透過 これでViewのバックグラウンドを見ることができる
        //下のビューのバックグラウンドが見える
        holder.setFormat(PixelFormat.TRANSLUCENT);
        //最前面に描画する これでViewのバックグラウンドの前に出てくる
        //バックグラウンドの下に隠れて見えないのを前に出す
        setZOrderOnTop(true);


    }

    //サーフェイス生成
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    //サーフェイス変更
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

    }

    //サーフェイスの破棄
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
        ((Activity) mContext).finish();
    }

    //ループ処理
    public void run() {
        while (thread != null) {
            long nextTime = System.currentTimeMillis() + 100;
            onTick();
            try {
                //スリープする
                Thread.sleep(nextTime - System.currentTimeMillis());
            } catch (Exception e) {

            }
        }
    }

    int anim = 0;
    Paint paint = new Paint();

    private void onTick() {
        //ダブルバッファリング
        Canvas canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        //前の描画を残さない
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

//        paint.setAntiAlias(true);
//        paint.setTextSize(50);
//        paint.setColor(Color.BLUE);
//        anim += 30;
//        canvas.drawColor(Color.parseColor("#ffff00ff"));
//        canvas.drawText("abcde" + anim, 0, 0 + anim, paint);

        Rect src = new Rect(0, 0, image[0].getWidth(), image[0].getHeight());
        Rect dst = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(image[rand(3)], src, dst, null);

        holder.unlockCanvasAndPost(canvas);
        //move
        if (px < 0 || getWidth() < px) {
            vx = -vx;
        }
        if (py < 0 || getHeight() < py) {
            vy = -vy;
        }
        px += vx;
        py += vy;
    }

    //乱数の取得
    private static Random rand = new Random();

    public static int rand(int num) {
        return (rand.nextInt() >>> 1) % num;
    }


}
