package com.bazan.mobile10.sudachi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.BitSet;


public class Graphics {
    private SurfaceHolder holder;
    private Paint paint;

    private Canvas canvas;

    private int originX;
    private int originY;

    public Graphics(int w, int h, SurfaceHolder holder) {
        this.holder = holder;
        this.holder.setFormat(PixelFormat.RGBA_8888);
        this.holder.setFixedSize(w, h);

        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void lock() {
        canvas = holder.lockCanvas();
        if (canvas == null) {
            return;
        }
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.translate(originX, originY);
    }

    public void unlock() {
        if (canvas == null) {
            return;
        }
        holder.unlockCanvasAndPost(canvas);
    }

    public void setOrigin(int x, int y) {
        originX = x;
        originY = y;
    }

    public int getOriginX() {
        return originX;
    }

    public int getOriginY() {
        return originY;
    }


    public void setColor(int color) {
        paint.setColor(color);
    }

    public void setTextSize(int fontSize) {
        paint.setTextSize(fontSize);
    }

    public Paint.FontMetrics getFontMetrics() {
        return paint.getFontMetrics();
    }

    public int measureText(String string) {
        return (int) paint.measureText(string);
    }

    public void drawAnaume(int x, int y, int dx, int dy) {
        paint.setStyle(Paint.Style.FILL);
        Rect src = new Rect(x, y, dx, dy);
        canvas.drawRect(src, paint);
    }


    public void drawBitmap(Bitmap bitmap, int x, int y) {
        if (canvas == null) {
            return;
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Rect src = new Rect(0, 0, w, h);
        Rect dst = new Rect(x, y, x + w, y + h);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void drawBitmap(Bitmap bitmap, int x, int y, float scale) {
        if (canvas == null) {
            return;
        }
        int w = (int) (bitmap.getWidth() * scale);
        int h = (int) (bitmap.getHeight() * scale);
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(x, y, x + w, y + h);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void drawBitmapF(Bitmap bitmap, int x, int y, int width, int height) {
        if (canvas == null) {
            return;
        }
        //todo ここで比率を。。。
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(x, y, width, height);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    /**
     * 中心の位置を指定する
     *
     * @param bitmap
     * @param x
     * @param y
     * @param scale
     */
    public void drawBitmapM(Bitmap bitmap, int x, int y, float scale) {
        if (canvas == null) {
            return;
        }
        int w = (int) (bitmap.getWidth() * scale);
        int h = (int) (bitmap.getHeight() * scale);
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dst = new Rect(x - w, y - h, x + w, y + h);
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public void drawText(String str, int x, int y) {
        if (canvas == null) {
            return;
        }
        canvas.drawText(str, x, y, paint);
    }
}
