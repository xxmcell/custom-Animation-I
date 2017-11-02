package com.itheima.jason.mydiyview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jason on 2017/7/3.
 */

public class Balls extends View {
    private int w;
    private int h;
    private Paint paint;
    private int length;

    public Balls(Context context) {
        this(context,null);
    }

    public Balls(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Balls(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.h=h;
        this.w=w;
        length=Math.min(w,h);
        prepareAnimators();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 3; i++) {
            paint.setAlpha(alpha[i]);
            paint.setColor(colors[i]);
            canvas.drawCircle(w/2,h/2,radius[i],paint);
        }
    }
   // private float[] delays=new float[]{0,200,400};
    //透明度最大值就到255
    private float []radius=new float[] {5,5,5};
    private int[] alpha=new int[]{255,255,255};
    private int[] colors=new int[]{Color.BLACK,Color.BLUE,Color.GRAY};
    private long[] delays=new long[]{0,200,400};
    private void prepareAnimators(){
        for (int i = 0; i < 3; i++) {
            ValueAnimator va = ValueAnimator.ofFloat(5,length/2-5);
            //float fraction =va.getAnimatedFraction();
            final int index=i;
            va.setDuration(2000);
            va.setStartDelay(delays[i]);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.start();
            //添加动画改变过程中的更新监听
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    //从getAnimatedValue中,来重新定义radius
                    radius[index]= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            va=ValueAnimator.ofInt(255,0);
            va.setDuration(2000);
            va.setStartDelay(delays[i]);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.start();
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alpha[index]= (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
    }
}
