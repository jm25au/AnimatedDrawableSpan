package mobi.jamesm.animateddrawablespan;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.style.DynamicDrawableSpan;

/**
 * Created by James McMahon on 17/01/2017.
 */

public class AnimatedDrawableSpan extends DynamicDrawableSpan {

    private AnimationDrawable mDrawable;
    private UpdateListener mListener;

    private final int mDrawableSize;
    private int mFrameCount;
    private int mFrameNumber = 0;
    private int mAnimationDelay;

    public AnimatedDrawableSpan(AnimationDrawable pDrawable, int pVerticalAlignment, int pSize, final int pAnimationDelay, UpdateListener pListener) {
        super(pVerticalAlignment);
        mDrawable = pDrawable;
        mListener = pListener;
        mFrameCount = mDrawable.getNumberOfFrames();
        mAnimationDelay = pAnimationDelay;
        mDrawableSize = pSize;

        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                if (mFrameNumber == mFrameCount - 1) {
                    mFrameNumber = 0;
                }
                mListener.update();
                mFrameNumber++;
                mHandler.postDelayed(this, mAnimationDelay);
            }
        });
    }

    @Override
    public Drawable getDrawable() {
        Drawable drawable = mDrawable.getFrame(mFrameNumber);
        drawable.setBounds(0, 0, mDrawableSize, mDrawableSize);
        return drawable;
    }

    @Override
    public int getSize(Paint pPaint, CharSequence pText, int pStart, int pEnd, Paint.FontMetricsInt pFontMetrics) {
        Rect rect = new Rect(0, 0, mDrawableSize, mDrawableSize);
        if (pFontMetrics != null) {
            pFontMetrics.ascent = -rect.bottom;
            pFontMetrics.descent = 0;
            pFontMetrics.top = pFontMetrics.ascent;
            pFontMetrics.bottom = 0;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas pCanvas, CharSequence pText, int pStart, int pEnd, float pX, int pTop, int pY, int pBottom, Paint pPaint) {
        Drawable drawable = getDrawable();
        pCanvas.save();

        int transY = pBottom - drawable.getBounds().bottom;
        if (mVerticalAlignment == ALIGN_BASELINE) {
            transY -= pPaint.getFontMetricsInt().descent;
        }

        pCanvas.translate(pX, transY);
        drawable.draw(pCanvas);
        pCanvas.restore();
    }

    interface UpdateListener {
        void update();
    }
}

