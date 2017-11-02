package com.itheima.jason.mydiyview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import com.itheima.jason.mydiyview.R;

/**
 * Created by jason on 2017/7/2.
 */

public class ArcMenu extends ViewGroup implements View.OnClickListener{

    private View child0;

    public ArcMenu(Context context) {
        this(context,null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private float radius=500f;
    private float h;
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.h=h;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        child0 = getChildAt(0);
        //初始化第一个点击控件的位置
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int he=dm.heightPixels;
        int height=he-child0.getMeasuredHeight()-10;
        child0.layout(0, (int) (h-child0.getMeasuredHeight()),child0.getMeasuredWidth(), (int) h);
        child0.setOnClickListener(this);
        int count= getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View child=getChildAt(i+1);
            int childwidth=child.getMeasuredWidth();
            int childheight=child.getMeasuredHeight();
            //5个控件,之间的弧度是PI/2/4
            double angle=  (Math.PI/2/(count-2)*i);
            int left= (int) (child0.getLeft()+radius*Math.sin(angle));
            int top= (int) (child0.getTop()-radius*Math.cos(angle));
            int right=left+childwidth;
            int bottom=top+childheight;
            child.layout(left,top,right,bottom);
            child.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //测量子视图
        int count = getChildCount();
        for (int i = 0;i<count;i++){
            getChildAt(i).measure(0,0);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl:
                animateOtherChild();
                rotateChild();
                break;
        }
    }

    private void rotateChild() {
        //设置第零个子控件的事件,以自身的中心为原点旋转
        RotateAnimation ra= new RotateAnimation
                (0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);
        child0.startAnimation(ra);
    }
    private boolean isRunning;
    //第一个子控件旋转,其余的平移加对自身旋转
    private void animateOtherChild() {
        //得到子控件的数量
        int count = getChildCount();
        //遍历
        for (int i = 0; i < count-1; i++) {
            //除了第一个控件以外的控件,从第二个子空间开始
            final View child=getChildAt(i+1);
            AnimationSet as= new AnimationSet(true);
            int left= child.getLeft();
           // float top= child.getTop();
            child.setVisibility(View.VISIBLE);
            //其他子控件从第一个子控件的位置,滑向自己的0,0的初始化位置,所以,距离应该是left的值,但
            //同时值也应该是负的(从负的位置到零).
            TranslateAnimation ta;
            if(!isRunning){
                ta = new TranslateAnimation
                            (Animation.ABSOLUTE, -left,
                                   Animation.ABSOLUTE, 0,
                                   Animation.ABSOLUTE, child0.getBottom()-child.getBottom(),
                                   Animation.ABSOLUTE, 0);
            }else {
                ta = new TranslateAnimation
                                (Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE, -left,
                                        Animation.ABSOLUTE, 0,
                                        Animation.ABSOLUTE, child0.getBottom()-child.getBottom());
            }
            ta.setAnimationListener(new AnimationSet.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                        if(isRunning==false){
                            child.clearAnimation();
                            child.setVisibility(INVISIBLE);
                        }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ta.setDuration(1500);
            //平移动画是一个一个先后执行的
            ta.setStartOffset(i*200);
            ta.setFillAfter(true);
            ta.setInterpolator(new OvershootInterpolator());
            RotateAnimation ra= new RotateAnimation
                    (0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
            ra.setDuration(2000);
            ra.setFillAfter(true);
            //注意,要先旋转再平移,不然每次的控件自身的坐标变化,自身中点也变化,平移中就不停的抖动
            as.addAnimation(ra);
            as.addAnimation(ta);
            as.setFillAfter(true);
            child.startAnimation(as);
        }
        isRunning=!isRunning;
    }
}
