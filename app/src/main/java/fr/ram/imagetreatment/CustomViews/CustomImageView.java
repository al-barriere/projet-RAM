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
                // Get the Bitmap dimensions
                imageWidth = getDrawable().getIntrinsicWidth();
                imageHeight = getDrawable().getIntrinsicHeight();
                // Get the ImageView dimensions
                screenWidth = getWidth();
                screenHeight = getHeight();

                // Center the Bitmap on the ImageView
                matrix = setMatrixCenter();

                float[] matrixValues = new float[9];
                matrix.getValues(matrixValues);

                // Save the scale factor of the image
                mScaleFactor = MIN_SCALE = matrixValues[0];
            }
        });

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mTouchListener = new TouchListener();
    }

    /***
     * Return a matrif of the image centered on the screen, reset its scroll and rescale it
     * @return The matrix of the scaled image
     */
    public Matrix setMatrixCenter() {
        scrollTo(0, 0);
        RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
        RectF viewRect = new RectF(0, 0, screenWidth, screenHeight);
        mScaleFactor = 0.f;
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

    /***
     * Private class used to manage the pinch to zoom effect
     */
    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // Get the previous values of the image matrix
            float[] matrixValues = new float[9];
            matrix.getValues(matrixValues);

            // Calculate the new image scale factor
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(MIN_SCALE, Math.min(mScaleFactor, MAX_SCALE));

            // Change the scale factor (zoom)
            // MSCALE_X
            matrixValues[0] = mScaleFactor;
            // MSCALE_Y
            matrixValues[4] = mScaleFactor;

            // Translate the image for centering it
            // MTRANS_X
            matrixValues[2] = (screenWidth - imageWidth * mScaleFactor) / 2;
            // MTRANS_Y
            matrixValues[5] = (screenHeight - imageHeight * mScaleFactor) / 2;

            matrix.setValues(matrixValues);
            invalidate();

            return true;
        }
    }

    /***
     * Private classe used to manage the image scroll
     */
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

                        // Calculate the translation size
                        int scrollX = lastX - x;
                        int scrollY = lastY - y;

                        // Scroll block
                        // Left
                        if (!(getScrollX() + scrollX >= -(imageWidth * mScaleFactor - screenWidth) / 2))
                            scrollX = 0;
                        // Right
                        if (!(getScrollX() + scrollX <= (imageWidth * mScaleFactor - screenWidth) / 2))
                            scrollX = 0;
                        // Top
                        if (!(getScrollY() + scrollY >= -(imageHeight * mScaleFactor - screenHeight) / 2))
                            scrollY = 0;
                        // Bottom
                        if (!(getScrollY() + scrollY <= (imageHeight * mScaleFactor - screenHeight) / 2))
                            scrollY = 0;

                        // Scroll the image
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
