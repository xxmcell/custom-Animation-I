package com.itheima.jason.mydiyview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by jason on 2017/7/3.
 */

public class Ball extends View {
    private int w;
    private int h;
    private Paint paint;
    private int length;

    public Ball(Context context) {
        this(context,null);
    }

    public Ball(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Ball(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        //画笔的风格,填满
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.h=h;
        this.w=w;
        length=Math.min(w,h);
        prepareAnimators();
    }
    private float radius=5;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
            paint.setAlpha(alpha);
            canvas.drawCircle(w/2,h/2,radius,paint);

    }
   // private float[] delays=new float[]{0,200,400};
    //透明度最大值就到255
    private int alpha=255;
    private void prepareAnimators(){

            ValueAnimator va = ValueAnimator.ofFloat(5,length/2-5);
            //float fraction =va.getAnimatedFraction();

            va.setDuration(2000);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.start();
            //添加动画改变过程中的更新监听
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //从getAnimatedValue中,来重新定义radius
                    radius= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            ValueAnimator va2=ValueAnimator.ofInt(alpha,0);
            va2.setDuration(2000);
            va2.setRepeatCount(ValueAnimator.INFINITE);
            va2.start();
            va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha= (int) animation.getAnimatedValue();
                    invalidate();
                }
            });

    }
}
