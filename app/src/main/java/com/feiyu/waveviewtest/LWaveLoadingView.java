package com.feiyu.waveviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by LHD on 2016/7/13.
 */
public class LWaveLoadingView extends View {

    private  Paint paint;


    private int width;
    private int height;

    private Bitmap backgroundBitmap;
//    private Bitmap backgroundBitmap2;

    private Path mPath;
    private Paint mPathPaint;
    private Path mPath2;
    private Paint mPathPaint2;

    private float mWaveHight = 20f;
    private float mWaveHalfWidth = 100f;
    private String mWaveColor = "#5be4ef";
    private int mWaveSpeed = 30;
    private int mWaveSpeed2 = 50;

    private Paint mTextPaint;
    private String currentText = "";
    private String mTextColor = "#FFFFFF";
    private int mTextSize = 41;

    private int maxProgress = 100;
    private int currentProgress = 0;
    private float CurY;

    private float distance = 0;
    private float distance2 = 100;
    private int RefreshGap = 10;

    private static final int INVALIDATE = 0X777;

    private Canvas canvas1;
    private Bitmap finalBmp1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INVALIDATE:
                    invalidate();
                    sendEmptyMessageDelayed(INVALIDATE, RefreshGap);
                    break;
            }
        }
    };


    public LWaveLoadingView(Context context) {
        this(context, null, 0);
    }

    public LWaveLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LWaveLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Init();
    }

    private void Init() {
        /**
         * 获得背景
         */
//        if (null == getBackground()) {
//            throw new IllegalArgumentException(String.format("background is null."));
//        } else {
//            backgroundBitmap = getBitmapFromDrawable(getBackground());
//        }
        backgroundBitmap = getBitmapFromDrawable(getResources().getDrawable(R.drawable.c1));
//        backgroundBitmap2 = getBitmapFromDrawable(getResources().getDrawable(R.drawable.white));
        //初始化绘制水波纹的路径和画笔
        mPath = new Path();
        mPath2 = new Path();
        mPathPaint = new Paint();
        mPathPaint2 = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint2.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);
        mPathPaint2.setStyle(Paint.Style.FILL);
        //初始化绘制文字的画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //不断通知自己重绘，让水波纹流动起来
        handler.sendEmptyMessageDelayed(INVALIDATE, 100);

        paint = new Paint();
        paint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

                width = MeasureSpec.getSize(widthMeasureSpec);
        CurY = height = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundBitmap != null) {
            canvas.drawBitmap(createImage(), 0, 0, null);
        }
    }

    private Bitmap createImage() {
        //第一个波浪线
        finalBmp1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas1 = new Canvas(finalBmp1);
        //缩放图片
        int min = Math.min(width, height);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap, min, min, false);
//        backgroundBitmap2 = Bitmap.createScaledBitmap(backgroundBitmap2, min, min, false);
        //设置交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        //画图片
        canvas1.drawBitmap(backgroundBitmap, 0, 0, paint);


        //设置水波纹颜色和文字颜色
        mPathPaint.setColor(Color.parseColor("#55ffffff"));
        mPathPaint2.setColor(Color.parseColor("#88ffffff"));

        mTextPaint.setColor(Color.parseColor(mTextColor));
        mTextPaint.setTextSize(mTextSize);

        //绘制波浪
        //根据进度的百分比计算当前水位的高度
        float CurMidY = height * (maxProgress - currentProgress) / maxProgress;
        if (CurY > CurMidY) {
            CurY = CurY - (CurY - CurMidY) / 10;
        }
        mPath.reset();
        mPath2.reset();
        mPath.moveTo(0 - distance, CurY);
        mPath2.moveTo(0 - distance2, CurY);

        int waveNum = width / ((int) mWaveHalfWidth * 4) + 1;
        int multiplier = 0;
        for (int i = 0; i < waveNum * 3; i++) {
            mPath.quadTo(mWaveHalfWidth * (multiplier + 1) -  distance, CurY - 2*mWaveHight, mWaveHalfWidth * (multiplier + 2) - distance, CurY);
            mPath.quadTo(mWaveHalfWidth * (multiplier + 3) -  distance, CurY + 2*mWaveHight, mWaveHalfWidth * (multiplier + 4) - distance, CurY);
            mPath2.quadTo(mWaveHalfWidth * (multiplier + 1) - distance2, CurY + mWaveHight, mWaveHalfWidth * (multiplier + 2) - distance2, CurY);
            mPath2.quadTo(mWaveHalfWidth * (multiplier + 3) - distance2, CurY - mWaveHight, mWaveHalfWidth * (multiplier + 4) - distance2, CurY);
            multiplier += 4;
        }
        distance += mWaveHalfWidth / mWaveSpeed;
        distance = distance % (mWaveHalfWidth * 4);
        distance2 += mWaveHalfWidth / mWaveSpeed2;
        distance2 = distance2 % (mWaveHalfWidth * 4);

        mPath.lineTo(width, height);
        mPath.lineTo(0, height);
        mPath.close();
        mPathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas1.drawPath(mPath, mPathPaint);

        mPath2.lineTo(width, height);
        mPath2.lineTo(0, height);
        mPath2.close();
        mPathPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas1.drawPath(mPath2, mPathPaint2);

//        canvas1.drawBitmap(backgroundBitmap2, 0, 0, paint);

        //画文字
        canvas1.drawText(currentText, width / 2, height / 2, mTextPaint);
        return finalBmp1;
    }

    //将Drawable转换成Bitmap
    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }


    /**
     * 下面是对外提供的方法
     *
     * @param currentProgress
     * @param currentText
     */
    public void setCurrent(int currentProgress, String currentText) {
        this.currentProgress = currentProgress;
        this.currentText = currentText;
    }


    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }


    public void setText(String mTextColor, int mTextSize) {
        this.mTextColor = mTextColor;
        this.mTextSize = mTextSize;
    }

    public void setWave(float mWaveHight, float mWaveWidth) {
        this.mWaveHight = mWaveHight;
        this.mWaveHalfWidth = mWaveWidth / 2;
    }


    public void setWaveColor(String mWaveColor) {
        this.mWaveColor = mWaveColor;
    }

    public void setmWaveSpeed(int mWaveSpeed) {
        this.mWaveSpeed = mWaveSpeed;
    }
}
