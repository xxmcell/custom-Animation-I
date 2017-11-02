package com.itheima.jason.mydiyview;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by jason on 2017/7/4.
 */

public class RordActivty extends AppCompatActivity {

    private ImageView front;
    private ImageView back;
    private ImageView car_gas;
    private boolean isGasViisble;
    Handler handler= new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_mede);
        front = (ImageView) findViewById(R.id.front_while);
        back = (ImageView) findViewById(R.id.back_while);
        car_gas = (ImageView)findViewById(R.id.car_gas);
        WhileAnimation();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                car_gas.setVisibility(isGasViisble? View.INVISIBLE:View.VISIBLE);
                isGasViisble=!isGasViisble;
                handler.postDelayed(this,300);
            }
        },300);

    }

    private void WhileAnimation() {
        AnimationDrawable frontAnimation= (AnimationDrawable) front.getDrawable();
        frontAnimation.start();
        AnimationDrawable backAnimation= (AnimationDrawable) back.getDrawable();
        backAnimation.start();
    }
}
