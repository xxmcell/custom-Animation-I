package com.itheima.jason.mydiyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jason on 2017/7/2.
 */

public class WifView extends View {

    private Paint paint;

    public WifView(Context context) {
        this(context,null);
    }

    public WifView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public WifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //在第一次运行时,执行的延迟
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
                //每次执行时,执行延迟
                handler.postDelayed(this,1000);
            }
        },1000);
    }
    private Handler handler=new Handler();
    /**
     *测量控件的长宽
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        baseLength = Math.min(w,h);
    }
    //基准的长宽
    private int baseLength;
    private float signalSize=4f;
    private float  showExcestSignalSize =4f;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        showExcestSignalSize++;
        if(showExcestSignalSize>signalSize){
            showExcestSignalSize=1;
        }
        canvas.save();
        //绘制一个矩形,
        RectF rectf;
        canvas.translate(0,baseLength/signalSize);
        paint.setStrokeWidth(6);
        //计算一个基准圆的半径
        float baseRadius= baseLength/2/signalSize;
        //每循环一次,换一次画的位置
        for (int i =0;i<signalSize;i++) {
            /**
             * showExcestSignalSize的作用就是让下面的for循环反着循环,从效果上完成从下向上的wifi效果,当i满足
             * 所给的条件时,才能进行画图
             */
            if (i>=signalSize-showExcestSignalSize) {
                float radius = baseRadius * i;
                rectf = new RectF(radius, radius, baseLength - radius, baseLength - radius);
                //除了最里面实心的,在这个demo里面,是i<3
                if (i < signalSize - 1) {
                    //绘制一个弧形
                    paint.setStyle(Paint.Style.STROKE);//空心的
                    //画一个弧形,属性,矩形,起始点,要走的角度,
                    canvas.drawArc(rectf, -135, 90, false, paint);
                } else {
                    //绘制一个扇形,也是实心的弧形
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawArc(rectf, -135, 90, true, paint);
                }
            }
        }
        canvas.restore();
    }
}
