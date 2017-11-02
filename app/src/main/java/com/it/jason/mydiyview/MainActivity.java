package com.it.jason.mydiyview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private View wifi;
    private View music;
    private View waterloading;
    private RelativeLayout rl;
    private Button bt_arc;
    private Button bt_road;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifi = findViewById(R.id.wifi);
        music = findViewById(R.id.music);
        waterloading = findViewById(R.id.waterloading);
        bt_arc = (Button) findViewById(R.id.bt_arc);
        bt_road = (Button) findViewById(R.id.bt_road);
        bt_arc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewActivty.class);
                startActivity(intent);
            }
        });
        bt_road.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RordActivty.class);
                startActivity(intent);
            }
        });
    }
}
