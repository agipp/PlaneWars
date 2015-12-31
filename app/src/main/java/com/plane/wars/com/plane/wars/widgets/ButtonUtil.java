package com.plane.wars.com.plane.wars.widgets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * 按钮工具类
 */
public class ButtonUtil {
    public final int offset = 10;
    public Bitmap myBitmap = null;
    public int mPosX = 0;
    public int mPosY = 0;
    public int mWidth = 0;
    public int mHeight = 0;

    /**
     * 构造函数
     * 通过构造函数，确定按钮位置以及大小
     *
     * @param myBitmap
     * @param mPosX
     * @param mPosY
     */
    public ButtonUtil(Bitmap myBitmap, int mPosX, int mPosY) {
        this.myBitmap = myBitmap;
        this.mPosX = mPosX;
        this.mPosY = mPosY;
        this.mWidth = this.myBitmap.getWidth();
        this.mHeight = this.myBitmap.getHeight();
    }

    /**
     * 设置按钮图片，确定按钮大小
     *
     * @param bm
     */
    public void setButtonPic(Bitmap bm) {
        if (bm != null) {
            this.myBitmap = bm;
            this.mWidth = this.myBitmap.getWidth();
            this.mHeight = this.myBitmap.getHeight();
        }
    }

    /**
     * 绘制按钮，通过设定的坐标值
     *
     * @param canvas
     * @param paint
     */
    public void drawButton(Canvas canvas, Paint paint) {
        if (this.myBitmap != null) {
            canvas.drawBitmap(this.myBitmap, this.mPosX, this.mPosY, paint);
        }
    }

    /**
     * 检测是否按钮被触摸到
     *
     * @param x 触摸x
     * @param y 触摸y
     * @return
     */
    public boolean isClick(float x, float y) {
        boolean isClick = false;
        if (x >= this.mPosX - offset && x <= this.mPosX + this.mWidth + offset
                && y >= this.mPosY - offset && y <= this.mPosY + this.mHeight + offset) {
            isClick = true;
        }
        return isClick;
    }

}
