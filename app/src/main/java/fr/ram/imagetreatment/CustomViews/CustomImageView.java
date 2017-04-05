package fr.ram.imagetreatment.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.effect.Effect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import fr.ram.imagetreatment.Enums.EffectModeEnum;
import fr.ram.imagetreatment.R;

/**
 * Created by Rémi on 21/02/2017.
 */

public class CustomImageView extends FrameLayout {
    private boolean imageModified = false;
    private ScaleGestureDetector mScaleDetector;
    private TouchListener mTouchListener;
    private int imageWidth, imageHeight, screenWidth, screenHeight;
    private Matrix matrix = new Matrix();
    private int lastX = 0, lastY = 0;
    private float mScaleFactor = 1.f;
    private float MIN_SCALE = 0.1f;
    private final float MAX_SCALE = 10.0f;
    private Bitmap effectBitmap;
    private ImageView principalImageView;
    private ImageView effectImageView;
    private Canvas effectCanvas;
    private EffectModeEnum effectMode = EffectModeEnum.EFFECT_ALL;
    private Paint paint;

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
        principalImageView.setImageMatrix(matrix);
    }

    private void init(Context context) {
        View view = inflate(getContext(), R.layout.custom_imageview_layout, null);
        addView(view);

        principalImageView = (ImageView) findViewById(R.id.principalImageView);
        effectImageView = (ImageView) findViewById(R.id.effectImageView);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(40);
        paint.setColor(Color.RED);
        paint.setAlpha(80);

        post(new Runnable() {
            @Override
            public void run() {
                imageWidth = principalImageView.getDrawable().getIntrinsicWidth();
                imageHeight = principalImageView.getDrawable().getIntrinsicHeight();
                screenWidth = getWidth();
                screenHeight = getHeight();

                RectF drawableRect = new RectF(0, 0, imageWidth, imageHeight);
                RectF viewRect = new RectF(0, 0, screenWidth, screenHeight);

                matrix.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER);

                float[] matrixValues = new float[9];
                matrix.getValues(matrixValues);

                mScaleFactor = MIN_SCALE = matrixValues[0];

                effectBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
                effectBitmap = effectBitmap.copy(Bitmap.Config.ARGB_8888, true);
                effectImageView.setImageBitmap(effectBitmap);
                effectCanvas = new Canvas(effectBitmap);
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
        return ((BitmapDrawable) (principalImageView.getDrawable())).getBitmap();
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.principalImageView.setImageBitmap(imageBitmap);
    }

    public EffectModeEnum getEffectMode() {
        return effectMode;
    }

    public void setEffectMode(EffectModeEnum effectMode) {
        if (screenWidth > 0 && screenHeight < 0) {
            effectCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            effectImageView.setImageResource(R.color.transparent);
            effectImageView.invalidate();
        }

        this.effectMode = effectMode;
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
            if (effectMode == EffectModeEnum.EFFECT_SELECTION) {
                /*setImageBitmap(img);
                invalidate();*/

                effectCanvas.drawPoint(event.getX(), event.getY(), paint);
                effectImageView.setImageBitmap(effectBitmap);
                effectImageView.invalidate();
                return true;
            } else if (effectMode == EffectModeEnum.EFFECT_ALL) {
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
                            final int probableScrollX = -getScrollX() - scrollX;
                            final int probableScrollY = -getScrollY() - scrollY;

                            if (probableScrollX > 0 || probableScrollX + imageWidth * mScaleFactor < screenWidth) {
                                scrollX = 0;
                            }

                            if (probableScrollY > 0 || probableScrollY + imageHeight * mScaleFactor < screenHeight) {
                                scrollY = 0;
                            }

                            if (scrollX != 0 || scrollY != 0) {
                                lastX = x;
                                lastY = y;
                                scrollBy(scrollX, scrollY);
                            }
                    }
                    effectImageView.setImageBitmap(effectBitmap);
                    effectImageView.invalidate();
                    return true;
                }
            }
            return  false;
        }
    }
}
