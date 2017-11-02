package com.itheima.jason.mydiyview;

import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

/**
 * Created by jason on 2017/7/2.
 */

public class ViewActivty extends AppCompatActivity{

    private RelativeLayout rl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        rl = (RelativeLayout) findViewById(R.id.rl);

    }
}
