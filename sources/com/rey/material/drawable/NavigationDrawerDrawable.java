package com.rey.material.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.rey.material.R;
import com.rey.material.drawable.LineMorphingDrawable;
import com.rey.material.drawable.ToolbarRippleDrawable;

/* loaded from: classes2.dex */
public class NavigationDrawerDrawable extends Drawable implements Drawable.Callback {
    public static final int STATE_ARROW = 1;
    public static final int STATE_DRAWER = 0;
    private LineMorphingDrawable mLineDrawable;
    private ToolbarRippleDrawable mRippleDrawable;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public NavigationDrawerDrawable(ToolbarRippleDrawable toolbarRippleDrawable, LineMorphingDrawable lineMorphingDrawable) {
        this.mRippleDrawable = toolbarRippleDrawable;
        this.mLineDrawable = lineMorphingDrawable;
        toolbarRippleDrawable.setCallback(this);
        this.mLineDrawable.setCallback(this);
    }

    public void switchIconState(int i, boolean z) {
        this.mLineDrawable.switchLineState(i, z);
    }

    public int getIconState() {
        return this.mLineDrawable.getLineState();
    }

    public boolean setIconState(int i, float f) {
        return this.mLineDrawable.setLineState(i, f);
    }

    public float getIconAnimProgress() {
        return this.mLineDrawable.getAnimProgress();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mRippleDrawable.draw(canvas);
        this.mLineDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mRippleDrawable.setAlpha(i);
        this.mLineDrawable.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mRippleDrawable.setColorFilter(colorFilter);
        this.mLineDrawable.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        this.mRippleDrawable.setBounds(i, i2, i3, i4);
        this.mLineDrawable.setBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mRippleDrawable.setDither(z);
        this.mLineDrawable.setDither(z);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        return this.mRippleDrawable.onStateChange(iArr);
    }

    /* loaded from: classes2.dex */
    public static class Builder {
        private LineMorphingDrawable mLineDrawable;
        private ToolbarRippleDrawable mRippleDrawable;

        public Builder() {
        }

        public Builder(Context context, int i) {
            this(context, null, 0, i);
        }

        public Builder(Context context, AttributeSet attributeSet, int i, int i2) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NavigationDrawerDrawable, i, i2);
            if (obtainStyledAttributes != null) {
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.NavigationDrawerDrawable_nd_ripple, 0);
                int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.NavigationDrawerDrawable_nd_icon, 0);
                if (resourceId > 0) {
                    ripple(new ToolbarRippleDrawable.Builder(context, resourceId).build());
                }
                if (resourceId2 > 0) {
                    line(new LineMorphingDrawable.Builder(context, resourceId2).build());
                }
                obtainStyledAttributes.recycle();
            }
        }

        public NavigationDrawerDrawable build() {
            return new NavigationDrawerDrawable(this.mRippleDrawable, this.mLineDrawable);
        }

        public Builder ripple(ToolbarRippleDrawable toolbarRippleDrawable) {
            this.mRippleDrawable = toolbarRippleDrawable;
            return this;
        }

        public Builder line(LineMorphingDrawable lineMorphingDrawable) {
            this.mLineDrawable = lineMorphingDrawable;
            return this;
        }
    }
}
