package com.google.appinventor.components.runtime;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.google.appinventor.components.runtime.util.HoneycombUtil;
import com.google.appinventor.components.runtime.util.SdkLevel;

/* loaded from: classes.dex */
public class ScaledFrameLayout extends ViewGroup {
    private static final int MATRIX_SAVE_FLAG = 1;
    private int mLeftWidth;
    private int mRightWidth;
    private float mScale;
    private final Rect mTmpChildRect;
    private final Rect mTmpContainerRect;

    public ScaledFrameLayout(Context context) {
        super(context);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mScale = 1.0f;
    }

    public ScaledFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaledFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mScale = 1.0f;
        setClipChildren(false);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(android.graphics.Canvas canvas) {
        canvas.save();
        canvas.scale(this.mScale, this.mScale);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        int[] scaledLocation = {(int) (location[0] * this.mScale), (int) (location[1] * this.mScale)};
        Rect scaledDirty = new Rect((int) (dirty.left * this.mScale), (int) (dirty.top * this.mScale), (int) (dirty.right * this.mScale), (int) (dirty.bottom * this.mScale));
        invalidate(scaledDirty);
        return super.invalidateChildInParent(scaledLocation, scaledDirty);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ev.setLocation(ev.getX() * (1.0f / this.mScale), ev.getY() * (1.0f / this.mScale));
        super.dispatchTouchEvent(ev);
        return true;
    }

    public void setScale(float scale) {
        this.mScale = scale;
        updatePadding(getWidth(), getHeight());
    }

    private void updatePadding(int width, int height) {
        int paddingRight = (int) ((width * (this.mScale - 1.0f)) / this.mScale);
        int paddingBottom = (int) ((height * (this.mScale - 1.0f)) / this.mScale);
        setPadding(0, 0, paddingRight, paddingBottom);
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updatePadding(w, h);
    }

    @Override // android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        this.mLeftWidth = 0;
        this.mRightWidth = 0;
        int maxHeight = 0;
        int childState = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                this.mLeftWidth += Math.max(0, child.getMeasuredWidth());
                maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
                if (SdkLevel.getLevel() >= 11) {
                    childState = HoneycombUtil.combineMeasuredStates(this, childState, HoneycombUtil.getMeasuredState(child));
                }
            }
        }
        int maxWidth = 0 + this.mLeftWidth + this.mRightWidth;
        int maxHeight2 = Math.max(maxHeight, getSuggestedMinimumHeight());
        int maxWidth2 = Math.max(maxWidth, getSuggestedMinimumWidth());
        if (SdkLevel.getLevel() >= 11) {
            setMeasuredDimension(HoneycombUtil.resolveSizeAndState(this, maxWidth2, widthMeasureSpec, childState), HoneycombUtil.resolveSizeAndState(this, maxHeight2, heightMeasureSpec, childState << 16));
        } else {
            setMeasuredDimension(resolveSize(maxWidth2, widthMeasureSpec), resolveSize(maxHeight2, heightMeasureSpec));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int leftPos = getPaddingLeft();
        int parentTop = getPaddingTop();
        int parentBottom = (bottom - top) - getPaddingBottom();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                this.mTmpContainerRect.left = leftPos;
                this.mTmpContainerRect.right = leftPos;
                leftPos = this.mTmpContainerRect.right;
                this.mTmpContainerRect.top = parentTop;
                this.mTmpContainerRect.bottom = parentBottom;
                Gravity.apply(51, width, height, this.mTmpContainerRect, this.mTmpChildRect);
                child.layout(this.mTmpChildRect.left, this.mTmpChildRect.top, this.mTmpChildRect.right, this.mTmpChildRect.bottom);
            }
        }
    }
}
