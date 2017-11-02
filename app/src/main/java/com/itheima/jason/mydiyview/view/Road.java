package com.itheima.jason.mydiyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.itheima.jason.mydiyview.R;

/**
 * Created by jason on 2017/7/3.
 */

public class Road extends View{

    private Paint paint;
    private Rect src;
    private Rect dst;
    private Rect lastSrc;
    private Rect lastDst;
    private Bitmap road;
    private int roadWidth;
    private Path path;

    public Road(Context context) {
        this(context,null);
    }

    public Road(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Road(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        src = new Rect();
        dst = new Rect();
        lastSrc = new Rect();
        lastDst = new Rect();
        path = new Path();
        road = BitmapFactory.decodeResource(getResources(), R.drawable.road);
        roadWidth = road.getWidth();
    }
    private int offset;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.addCircle(this.getWidth()/2,this.getHeight()/2,this.getWidth()/2,Path.Direction.CW);
        //画布的剪切:可以按照path去剪切出对应的图形
        canvas.clipPath(path);
        //如果变量+控件的宽度小于roadwidth,递进正常使用
        if(offset+this.getWidth()<=roadWidth){
            //参数2.src表示从源图片选择出的矩形区域
            //参数3.dst表示将多少矩形区域的数据绘制到画布上
            src.set(offset,0,this.getWidth()+offset,this.getHeight());
            dst.set(0,0,this.getWidth(),this.getHeight());
            canvas.drawBitmap(road, src, dst, paint);
        }else {
            //如果大于roadwidth,需要重新循环,截取
            src.set(offset,0,roadWidth,this.getHeight());
            //显示出来的,是x轴上0,src.width();
            dst.set(0,0,src.width(),this.getHeight());
            canvas.drawBitmap(road,src,dst,paint);
            //需要绘制的是总宽度减去已经绘制的
            lastSrc.set(0,0,this.getWidth()-src.width(),this.getHeight());
            //起点为,dst.width(),终点为getwidth()
            lastDst.set(dst.width(),0,getWidth(),getHeight());
            canvas.drawBitmap(road,lastSrc,lastDst,paint);
        }
        //每画一次,就做一个记录
        offset+=20;
        //增量,模与图片的宽度
        offset%=roadWidth;
        path.reset();
        invalidate();
    }
}
