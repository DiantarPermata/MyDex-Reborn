package com.github.mikephil.charting.jobs;

import android.animation.ValueAnimator;
import android.view.View;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

/* loaded from: classes.dex */
public class AnimatedMoveViewJob extends AnimatedViewPortJob {
    private static ObjectPool<AnimatedMoveViewJob> pool;

    static {
        ObjectPool<AnimatedMoveViewJob> create = ObjectPool.create(4, new AnimatedMoveViewJob(null, 0.0f, 0.0f, null, null, 0.0f, 0.0f, 0L));
        pool = create;
        create.setReplenishPercentage(0.5f);
    }

    public static AnimatedMoveViewJob getInstance(ViewPortHandler viewPortHandler, float f, float f2, Transformer transformer, View view, float f3, float f4, long j) {
        AnimatedMoveViewJob animatedMoveViewJob = pool.get();
        animatedMoveViewJob.mViewPortHandler = viewPortHandler;
        animatedMoveViewJob.xValue = f;
        animatedMoveViewJob.yValue = f2;
        animatedMoveViewJob.mTrans = transformer;
        animatedMoveViewJob.view = view;
        animatedMoveViewJob.xOrigin = f3;
        animatedMoveViewJob.yOrigin = f4;
        animatedMoveViewJob.animator.setDuration(j);
        return animatedMoveViewJob;
    }

    public static void recycleInstance(AnimatedMoveViewJob animatedMoveViewJob) {
        pool.recycle((ObjectPool<AnimatedMoveViewJob>) animatedMoveViewJob);
    }

    public AnimatedMoveViewJob(ViewPortHandler viewPortHandler, float f, float f2, Transformer transformer, View view, float f3, float f4, long j) {
        super(viewPortHandler, f, f2, transformer, view, f3, f4, j);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob, android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.pts[0] = this.xOrigin + ((this.xValue - this.xOrigin) * this.phase);
        this.pts[1] = this.yOrigin + ((this.yValue - this.yOrigin) * this.phase);
        this.mTrans.pointValuesToPixel(this.pts);
        this.mViewPortHandler.centerViewPort(this.pts, this.view);
    }

    @Override // com.github.mikephil.charting.jobs.AnimatedViewPortJob
    public void recycleSelf() {
        recycleInstance(this);
    }

    @Override // com.github.mikephil.charting.utils.ObjectPool.Poolable
    protected ObjectPool.Poolable instantiate() {
        return new AnimatedMoveViewJob(null, 0.0f, 0.0f, null, null, 0.0f, 0.0f, 0L);
    }
}
