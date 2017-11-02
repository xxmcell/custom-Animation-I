package com.it.jason.mydiyview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jason on 2017/7/2.
 */

public class musicLoading extends View {

    private Paint paint;
    private float length;

    public musicLoading(Context context) {
        this(context,null);
    }

    public musicLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public musicLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        length = Math.min(w,h);
    }
    private float smallRadius=4f;
    private float startAngle1=0;
    private float endAngle2=180;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startAngle1+=5f;
        endAngle2+=5f;
        //绘制2个圆
        paint.setStrokeWidth(2);
        canvas.drawCircle(length/2,length/2,length/2-smallRadius,paint);//大
        paint.setStrokeWidth(5);
        canvas.drawCircle(length/2,length/2,smallRadius,paint);//小
        //绘制2边的4个弧形
        RectF rectF1= new RectF(length/2-length/3,length/2-length/3,length/2+length/3,length/2+length/3);
        canvas.drawArc(rectF1,startAngle1,80,false,paint);
        canvas.drawArc(rectF1,endAngle2,80,false,paint);
        RectF rectF2=new RectF(length/2-length/4,length/2-length/4,length/2+length/4,length/2+length/4);
        canvas.drawArc(rectF2,startAngle1,80,false,paint);
        canvas.drawArc(rectF2,endAngle2,80,false,paint);
        if(!isStart){
            invalidate();
        }
    }
    private boolean isStart;
    //当自定义控件脱离窗体,即将销毁的时候
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isStart=true;
    }
}
