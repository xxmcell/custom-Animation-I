package com.it.jason.mydiyview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.it.jason.mydiyview.R;

/**
 * Created by jason on 2017/7/2.
 */

public class WaterLoading extends TextView {

    private Matrix matrix;
    private BitmapShader shader;
    private int waveWidth;
    private int waveHeigt;

    public WaterLoading(Context context) {
        this(context,null);
    }

    public WaterLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置当前的控件文字字体颜色为红色,背景的白色先盖住了原来的颜色,白色过了就是透明的?(与文本的颜色无关)
        this.setTextColor(Color.GREEN);
        Typeface typeface= Typeface.createFromAsset(getResources().getAssets(),"waterloading.ttf");
        setTypeface(typeface);
        matrix = new Matrix();
    }
    private int h;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //着色器
        createShader();
        this.h=h;
    }
    private void createShader() {
        Drawable wave= getResources().getDrawable(R.drawable.wave);
        //指:图片的原始宽高
        waveWidth = wave.getIntrinsicWidth();
        waveHeigt = wave.getIntrinsicHeight();
        Bitmap bitmap= Bitmap.createBitmap(waveWidth, waveHeigt,Bitmap.Config.ARGB_8888);
        //创建一个画布,为了将wave的图片颜色数据写入到Bitmap中
        Canvas canvas= new Canvas(bitmap);
        //画布必须有一个颜色,否则无法将图片数据写入,实际上文字的颜色是画布上的颜色
        canvas.drawColor(Color.GRAY);
        //如果wave没有辩解,则canvas无法进行绘制
        wave.setBounds(0,0, waveWidth, waveHeigt);
        wave.draw(canvas);
        //CLAMP:使用原来的那张图片整体
        //REPEAT:将原来的图片复制无数份
        //MIRROR:镜像,将原来的图片镜像后,写入,再镜像...
        //图片,x,y轴
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.CLAMP);
        //利用着色器进行着色
        getPaint().setShader(shader);
        shaderX=0;
        shaderY=-waveHeigt /2;
    }
    private float shaderX;
    private float shaderY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shaderX+=6;
        shaderY+=0.8;
        if(shaderY>-waveHeigt/2+h/2){
            shaderY=-waveHeigt/2;
        }
        //让BitmapShader不断向下和向右移动
        matrix.setTranslate(shaderX,shaderY);
        //为着色器设置Matrix,就可以实现着色器的移动
        shader.setLocalMatrix(matrix);
        invalidate();
    }
}
