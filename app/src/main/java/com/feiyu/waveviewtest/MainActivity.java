package com.feiyu.waveviewtest;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

import com.gelitenight.waveview.library.WaveView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WaveView waveView;
    private List<Animator> animators = new ArrayList<>();
    private AnimatorSet animatorSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waveView = (WaveView) findViewById(R.id.waveview);
        int mBorderColor = Color.parseColor("#B0b7d28d");
        int mBorderColor2 = Color.parseColor("#B0b7d28d");
        waveView.setWaveColor(mBorderColor,mBorderColor2);
        waveView.setBorder(10,mBorderColor);
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
                waveView, "waterLevelRatio", 0f, 0.5f);
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        waterLevelAnim.start();
        // horizontal animation.
// wave waves infinitely.
//        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(
//                waveView, "waveShiftRatio", 0f, 1f);
//        waveShiftAnim.setRepeatCount(ValueAnimator.INFINITE);
//        waveShiftAnim.setDuration(1000);
//        waveShiftAnim.setInterpolator(new LinearInterpolator());
//        animators.add(waveShiftAnim);
//// vertical animation.
//// water level increases from 0 to center of WaveView
//        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(
//                waveView, "waterLevelRatio", 0f, 0.5f);
//        waterLevelAnim.setDuration(10000);
//        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
//        animators.add(waterLevelAnim);
//
//// amplitude animation.
//// wave grows big then grows small, repeatedly
//        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(
//                waveView, "amplitudeRatio", 0f, 0.05f);
//        amplitudeAnim.setRepeatCount(ValueAnimator.INFINITE);
//        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
//        amplitudeAnim.setDuration(5000);
//        amplitudeAnim.setInterpolator(new LinearInterpolator());
//        animators.add(amplitudeAnim);
//
//        animatorSet = new AnimatorSet();
//        animatorSet.playTogether(animatorSet);
//
//        animatorSet.start();
    }
}
