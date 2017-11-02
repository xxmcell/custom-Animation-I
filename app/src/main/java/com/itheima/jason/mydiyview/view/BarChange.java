package com.itheima.jason.mydiyview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jason on 2017/7/3.
 */

public class BarChange extends View {

    private Paint paint;

    public BarChange(Context context) {
        this(context,null);
    }

    public BarChange(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BarChange(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        prepaerAnimators();
    }
    private float[] scales=new float[]{1.0f,1.0f,1.0f,1.0f,1.0f};
    @Override
    protected void onDraw(Canvas canvas) {
        //画出5个条条
        super.onDraw(canvas);
        int x= getWidth()/11;
        int y = getHeight()/2;
        for (int i = 0; i < 5; i++) {
            //移动画布来完成
            canvas.save();
            //每个小条中间隔着一个多条的距离
            canvas.translate((float)((1.5+i*2)*x),y);//1,3,5,7.......
            //利用画布缩放来实现高度的变化
            canvas.scale(1.0f,scales[i]);
            //个小条的宽高,就是以矩形为中心的点,左右,上下的距离.
            RectF rectF=new RectF(-x/2,-y/2,x/2,y/2);
            //画一个边角为5的矩形
            canvas.drawRoundRect(rectF,5,5,paint);
            canvas.restore();
        }
    }
    private void prepaerAnimators(){
        //通过来更改延迟的数字,来完成不同的效果
        long[] dalays= new long []{0,100,200,300,400};
        for (int i = 0; i < 5; i++) {
            ValueAnimator va = ValueAnimator.ofFloat(1,0.5f);
            va.setDuration(600);
            va.setRepeatCount(ValueAnimator.INFINITE);
            va.setRepeatMode(ValueAnimator.REVERSE);
            va.setStartDelay(dalays[i]);
            va.start();
            final int index=i;
            //变化的监听
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scales[index]= (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
        }
    }
}
