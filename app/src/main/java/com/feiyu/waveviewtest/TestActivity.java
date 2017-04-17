package com.feiyu.waveviewtest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestActivity extends AppCompatActivity {


    private Button begin;
    private LWaveLoadingView waveProgressbar;
    private static final int one = 0X0001;
    private int progress = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progress++;
            switch (msg.what) {
                case one:
                    if (progress <= 100) {
                        waveProgressbar.setCurrent(progress, progress + "%");
                        sendEmptyMessageDelayed(one, 100);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        MyView myView = (MyView) findViewById(R.id.myview);
//        myView.startAnim();
        waveProgressbar = (LWaveLoadingView) findViewById(R.id.wave_progressbar);
        waveProgressbar.setCurrent(0, progress + "%"); // 77, "788M/1024M"
        waveProgressbar.setMaxProgress(100);
        waveProgressbar.setText("#66FFFFFF", 41);//"#FFFF00", 41
//        waveProgressbar.setWaveColor("#55ffffff"); //"#5b9ef4"

        waveProgressbar.setWave(10, 60);
        waveProgressbar.setmWaveSpeed(10);//The larger the value, the slower the vibration

        begin = (Button) findViewById(R.id.btn);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessageDelayed(one, 1000);
            }
        });
    }

}
