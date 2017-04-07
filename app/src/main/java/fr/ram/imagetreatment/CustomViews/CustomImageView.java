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

import fr.ram.imagetreatment.Enums.EffectModeEnum;

/**
 * Created by Rémi on 21/02/2017.
 */

public class CustomImageView extends AppCompatImageView {
    private boolean imageModified = false;
    private ScaleGestureDetector mScaleDetector;
    private TouchListener mTouchListener;
    private int imageWidth, imageHeight, screenWidth, screenHeight;
    private Matrix matrix = new Matrix();
    private int lastX = 0, lastY = 0;
    private float mScaleFactor;
    private float MIN_SCALE = 0.1f;
    private final float MAX_SCALE = 10.0f;
    private EffectModeEnum effectMode;

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

                matrix=setMatrixCenter();

                float[] matrixValues = new float[9];
                matrix.getValues(matrixValues);

                mScaleFactor = MIN_SCALE = matrixValues[0];
            }
        });

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mTouchListener = new TouchListener();
        effectMode = EffectModeEnum.EFFECT_ALL;
    }

    public Matrix setMatrixCenter() {
        scrollTo(0,0);
        RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
        RectF viewRect = new RectF(0, 0, screenWidth, screenHeight);
        mScaleFactor=0.f;
        matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);
        return matrix;
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

    public EffectModeEnum getEffectMode() {
        return effectMode;
    }

    public void setEffectMode(EffectModeEnum effectMode) {
        this.effectMode = effectMode;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float[] matrixValues = new float[9];
            matrix.getValues(matrixValues);

            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_SCALE, Math.min(mScaleFactor, MAX_SCALE));

            // MSCALE_X
            matrixValues[0] = mScaleFactor;
            // MSCALE_Y
            matrixValues[4] = mScaleFactor;
            // MTRANS_X
            matrixValues[2] = (screenWidth - imageWidth * mScaleFactor) / 2;
            // MTRANS_Y
            matrixValues[5] = (screenHeight - imageHeight * mScaleFactor) / 2;

            matrix.setValues(matrixValues);
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

                        if (!(getScrollX() + scrollX >= -(imageWidth * mScaleFactor - screenWidth) / 2))//blocage gauche
                            scrollX = 0;
                        if (!(getScrollY() + scrollY >= -(imageHeight * mScaleFactor - screenHeight) / 2))//blocage haut
                            scrollY = 0;
                        if (!(getScrollX() + scrollX <= (imageWidth * mScaleFactor - screenWidth) / 2))//blocage droite
                            scrollX = 0;
                        if (!(getScrollY() + scrollY <= (imageHeight * mScaleFactor - screenHeight) / 2))//blocage bas
                            scrollY = 0;

                        scrollBy(scrollX, scrollY);
                        lastX = x;
                        lastY = y;

                }
                return true;
            }
            return false;
        }
    }
}
