package fr.ram.imagetreatment.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import fr.ram.imagetreatment.R;

/**
 * Created by Rémi on 21/02/2017.
 */

public class CustomImageView extends AppCompatImageView {
    private boolean imageModified = false;
    private float mScaleFactor = 1.f;
    private int lastX = 0, lastY = 0;
    private ScaleGestureDetector mScaleDetector;
    private TouchListener mTouchListener;
    private float MIN_SCALE = 0.1f;
    private final float MAX_SCALE = 5.0f;
    private int imageWidth, imageHeight, screenWidth, screenHeight;
    private Matrix matrix = new Matrix();

    public CustomImageView(Context context) {
        super(context);
        init(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setImageMatrix(matrix);
    }

    private void init(Context context) {
        post(new Runnable() {
            @Override
            public void run() {
                imageWidth = getDrawable().getIntrinsicWidth();
                imageHeight = getDrawable().getIntrinsicHeight();
                screenWidth = getWidth();
                screenHeight = getHeight();

                RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
                RectF viewRect = new RectF(0, 0, screenWidth, screenHeight);

                matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);

                float[] matrixValues = new float[9];
                matrix.getValues(matrixValues);
                mScaleFactor = MIN_SCALE = matrixValues[0];
            }
        });

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mTouchListener = new TouchListener();
    }

    public void setImageModified(boolean imageModified) {
        this.imageModified = imageModified;
    }

    public boolean getImageModified() {
        return imageModified;
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable) (getDrawable())).getBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mScaleDetector.onTouchEvent(ev);
        mTouchListener.onTouch(this, ev);
        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_SCALE, Math.min(mScaleFactor, MAX_SCALE));
            matrix.setScale(mScaleFactor, mScaleFactor);
            invalidate();
            return true;
        }
    }

    private class TouchListener implements OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final int action = event.getAction();
            if (event.getPointerCount() == 1) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getX();
                        lastY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final int x = (int) event.getX();
                        final int y = (int) event.getY();

                        int scrollX = lastX - x;
                        int scrollY = lastY - y;

                        if (-getScrollX() - scrollX > 0 || imageWidth * mScaleFactor - getScrollX() - scrollX < screenWidth)
                            scrollX = 0;

                        if (-getScrollY() - scrollY > 0 || imageHeight * mScaleFactor - getScrollY() - scrollY < screenHeight)
                            scrollY = 0;

                        if (scrollX != 0 || scrollY != 0) {
                            lastX = x;
                            lastY = y;
                            scrollBy(scrollX, scrollY);
                        }
                        break;
                }
                return true;
            }
            return false;
        }
    }
}
