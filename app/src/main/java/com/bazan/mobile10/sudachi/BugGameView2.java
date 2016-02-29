package com.bazan.mobile10.sudachi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class BugGameView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    //虫のでかさ
    private final float BUG_L_SCALE = 0.5F;
    //虫の最大出現数
    private int bugMaxCount = 5;
    //虫の発生スピード
    private int bugMakeSpeed = 10;


    private final static int S_TITLE = 0;
    private final static int S_PLAY = 1;
    private final static int S_GAMEOVER = 2;
    //初期化時のシーン、シーン、スコア
    private int init = S_TITLE;
    private int scene = S_TITLE;
    private int score;

    //追加ペース
    private int tick;
    private long gameoverWait;

    private static int w;
    private static int h;

    private int touchPointX;
    private int touchPointY;

    private SurfaceHolder holder;
    private Thread thread;
    private Graphics g;

    private List<Point> bugL = new ArrayList<Point>();
    private List<Bug> bugs = new ArrayList<Bug>();

    private class Bug {
        int x;
        int y;
        int life;

        private Bug(int x, int y) {
            this.x = x;
            this.y = y;
            this.life = 3;
        }
    }

    private Bitmap[] bmp = new Bitmap[5];
    private final int BACKGROUND = 0;
    private final int COMMENT = 1;
    private final int BUGL = 2;
    private final int BUG = 3;
    private final int GAMEOVER = 4;

    public BugGameView2(Activity activity) {
        super(activity);

        bmp[BACKGROUND] = readBitmap(activity, "background_hare_yoru");
        bmp[COMMENT] = readBitmap(activity, "background_ame_yoru");
        bmp[BUGL] =    readBitmap(activity, "fruit");
        bmp[BUG] = readBitmap(activity, "fruit");
        bmp[GAMEOVER] = readBitmap(activity, "fruit");
        holder = getHolder();
        holder.addCallback(this);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        w = point.x;
        h = point.y;
        Log.e("w and h", "w:" + w + "h:" + h);
        //todo 虫のコントロール
        //放置した時間で初期虫の発生数と発生スピード変化させる？
        //初期の虫の数
        int initBugCount = 3;
        for (int i = 0; i < initBugCount; i++) {
            bugL.add(new Point(bugPointX(), bugPointY()));
        }

        g = new Graphics(w, h, holder);

    }
    @Override
    public void run() {
        while(thread != null){
            //初期化
            initGame();
            //動く座標大きさのセット
            if(scene == S_PLAY){
                playMove();
            }
            //画面の描画
            //動いた後の画像を描画する
            playDraw();
            //描画遅延
            try {
                Thread.sleep(30);
            } catch (Exception e) {

            }
        }
    }

    private void playDraw() {
        g.lock();
        drawBackground();
        drawBugL();
        drawBug();
        drawMessage();
        g.unlock();
    }

    private void drawMessage() {
        if (scene == S_TITLE) {
            //todo 画像はめ込む
            g.drawBitmapM(bmp[COMMENT], w / 2, h / 2, 0.1F);
        } else if (scene == S_GAMEOVER) {
            //todo 画像はめ込む
            g.drawBitmapM(bmp[GAMEOVER], w / 2, h / 2, 0.5F);
        }
    }

    private void drawBug() {
        for (int i = bugs.size() - 1; i >= 0; i--) {
            Bug bug = bugs.get(i);
            //todo やられた虫の描画に画像の切り替え
            g.drawBitmap(bmp[BUG], bug.x, bug.y ,BUG_L_SCALE);
        }
    }

    private void drawBugL() {
        for (int i = 0; i < bugL.size(); i++) {
            Point point = bugL.get(i);
            //Rect src = new Rect(0, 0, w / 10, h / 10);
            g.drawBitmap(bmp[BUGL], point.x, point.y,BUG_L_SCALE);
        }
    }

    private void drawBackground() {
        g.drawBitmapF(bmp[BACKGROUND], 0, 0, w, h);
        if (scene == S_TITLE) {
            //todo ここでコメントを入れなくもいいdrawmassageでもOK
            //g.drawBitmap(bmp[COMMENT], 0, 0);
        } else if (scene == S_GAMEOVER) {
            //todo クリア時に背景を変えたい場合ここ
        }
    }

    private void playMove() {
        //虫を出現させる
        makeBug();
        //虫をタッチで虫消える
        bugShot();
        //虫がやられる
        lifeBug();
        //ゲーム終了判定
        chackGameFin();
    }

    private void chackGameFin() {
        if(bugL.size() == 0){
            init = S_GAMEOVER;
        }
    }

    private void bugShot() {
        for (int j = bugL.size() - 1; j >= 0; j--) {
            Point pos1 = bugL.get(j);
            if (pos1.x < touchPointX && touchPointX < pos1.x + (bmp[BUGL].getWidth()*BUG_L_SCALE) &&
                    pos1.y < touchPointY && touchPointY < pos1.y + (bmp[BUGL].getHeight()*BUG_L_SCALE)) {
                //爆発
                bugs.add(new Bug(pos1.x, pos1.y));
                bugL.remove(j);
                score++;
                break;
            }
        }
    }

    private void lifeBug() {
        for (int i = bugs.size() - 1; i >= 0; i--) {
            Bug bug = bugs.get(i);
            bug.life--;
            if (bug.life < 0) {
                bugs.remove(i);
            }
        }
    }

    private void makeBug() {
        tick++;
        if (tick > bugMakeSpeed) {
            tick = 0;
            if(bugL.size() < bugMaxCount){
                bugL.add(new Point(bugPointX(), bugPointY()));
            }
        }
    }


    private void initGame() {
        if (init >= 0) {
            scene = init;
            if (scene == S_TITLE) {
                bugs.clear();
                score = 0;
            } else if (scene == S_GAMEOVER) {
                gameoverWait = System.currentTimeMillis();
                //この下コメントアウトでやっつけた虫の画像を残しつつゲーム終了コメントを表示できる
                bugs.clear();
            }
            init = -1;
        }
    }






    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    /**
     * BUG出現場所
     * @return
     */
    private int bugPointX() {
        int pointX = 0;
        int tempX = (int) (w * 0.5);
        pointX = (int) (SudachiKun.rand(tempX) + (w * 0.1));
        return pointX;
    }

    private int bugPointY() {
        int pointY = 0;
        int tempY = (int) (h * 0.5);
        pointY = (int) (SudachiKun.rand(tempY) + (h * 0.1));
        return pointY;
    }

    private static Bitmap readBitmap(Context context, String str) {
        int resID = context.getResources().getIdentifier(str, "drawable", context.getPackageName());
        return BitmapFactory.decodeResource(context.getResources(), resID);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) (event.getX() * w / getWidth());
        int touchY = (int) (event.getY() * h / getHeight());
        int touchAction = event.getAction();

        if (touchAction == MotionEvent.ACTION_DOWN) {
            if (scene == S_TITLE) {
                init = S_PLAY;
            } else if (scene == S_PLAY) {
                touchPointX = touchX;
                touchPointY = touchY;
            } else if (scene == S_GAMEOVER) {
                //ゲームオーバーのあと1秒以上たたないとスタート画面に戻れない
                if (gameoverWait + 1000 < System.currentTimeMillis()) {
                    //todo insert score
                    //BugGameActivity.saveData(score);
                    //todo 画面遷移を描く
                }
            }
        } else if (touchAction == MotionEvent.ACTION_MOVE) {
            if (scene == S_PLAY) {
                touchPointX = touchX;
                touchPointY = touchY;
            }
        }else if (touchAction == MotionEvent.ACTION_UP) {
            //アクションムーブとセットでつかう手を放した時にポイントを初期化しないと
            //同じ座標を指定し続けるので虫が勝手に消える
            if (scene == S_PLAY) {
                touchPointX = 0;
                touchPointY = 0;
            }
        }
        return true;
    }
}
