package com.shop.oasaustre.shoppinglist.util;

/**
 * Created by oasaustre on 28/08/17.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import com.gelitenight.waveview.library.WaveView;
import java.util.ArrayList;
import java.util.List;

public class WaveHelper {
    private List<Animator> finishAanimators = new ArrayList();
    private List<Animator> initAnimators = new ArrayList();
    private AnimatorSet mAnimatorSet;
    private WaveView mWaveView;

    public WaveHelper(WaveView waveView) {
        this.mWaveView = waveView;
        initAnimation();
    }

    public void start() {
        this.mWaveView.setShowWave(true);
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.start();
        }
    }

    private void initAnimation() {
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(this.mWaveView, "waveShiftRatio", new float[]{0.0f, 1.0f});
        waveShiftAnim.setRepeatCount(-1);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        this.initAnimators.add(waveShiftAnim);
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(this.mWaveView, "waterLevelRatio", new float[]{0.2f, 0.2f});
        waterLevelAnim.setDuration(10000);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        this.initAnimators.add(waterLevelAnim);
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(this.mWaveView, "amplitudeRatio", new float[]{0.01f, 0.02f});
        amplitudeAnim.setRepeatCount(-1);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(5000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        this.initAnimators.add(amplitudeAnim);
        this.mAnimatorSet = new AnimatorSet();
        this.mAnimatorSet.playTogether(this.initAnimators);
    }

    public void finishAnimation() {
        ObjectAnimator waveShiftAnim = ObjectAnimator.ofFloat(this.mWaveView, "waveShiftRatio", new float[]{0.0f, 1.0f});
        waveShiftAnim.setRepeatCount(-1);
        waveShiftAnim.setDuration(1000);
        waveShiftAnim.setInterpolator(new LinearInterpolator());
        this.finishAanimators.add(waveShiftAnim);
        ObjectAnimator waterLevelAnim = ObjectAnimator.ofFloat(this.mWaveView, "waterLevelRatio", new float[]{0.2f, 1.0f});
        waterLevelAnim.setDuration(1200);
        waterLevelAnim.setInterpolator(new DecelerateInterpolator());
        this.finishAanimators.add(waterLevelAnim);
        ObjectAnimator amplitudeAnim = ObjectAnimator.ofFloat(this.mWaveView, "amplitudeRatio", new float[]{0.01f, 0.02f});
        amplitudeAnim.setRepeatCount(-1);
        amplitudeAnim.setRepeatMode(ValueAnimator.REVERSE);
        amplitudeAnim.setDuration(5000);
        amplitudeAnim.setInterpolator(new LinearInterpolator());
        this.finishAanimators.add(amplitudeAnim);
        this.mAnimatorSet.end();
        this.mAnimatorSet.playTogether(this.finishAanimators);
        this.mAnimatorSet.start();
    }

    public void cancel() {
        if (this.mAnimatorSet != null) {
            this.mAnimatorSet.end();
        }
    }
}
