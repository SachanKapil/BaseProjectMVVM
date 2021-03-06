package com.baseprojectmvvm.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.baseprojectmvvm.R;


public class RoundCornersImageView extends AppCompatImageView {

    public RoundCornersImageView(Context context) {
        super(context);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundCornersImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float radius = getContext().getResources().getDimension(R.dimen._4sdp);
        Path path = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    public void clearOverlay() {
        clearColorFilter();
    }

    public void putOverlay() {
        setColorFilter(ContextCompat.getColor(
                getContext(),
                R.color.black_30_alpha),
                PorterDuff.Mode.SRC_OVER
        );
    }

    public void putDarkOverlay() {
        setColorFilter(ContextCompat.getColor(
                getContext(),
                R.color.black_60_alpha),
                PorterDuff.Mode.SRC_OVER
        );
    }
}
