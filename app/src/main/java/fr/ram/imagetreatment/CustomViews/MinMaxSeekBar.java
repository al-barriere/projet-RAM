package fr.ram.imagetreatment.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;

import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by remi on 18/04/2017.
 */

/***
 * Custom class used to set min, max and step value to a SeekBar
 */
public class MinMaxSeekBar extends android.support.v7.widget.AppCompatSeekBar {
    private static int STEP = 1;
    private int value;

    public MinMaxSeekBar(Context context) {
        super(context);
        init(context, null);
    }

    public MinMaxSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MinMaxSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /***
     * Initializes the view
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // Retrieve the params of the view
        TypedArray customParams = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedButton, 0, 0);

        // Retrieve the view params
        final int step = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_step, STEP);
        final int min = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_min, -ColorUtil.MAX_VALUE_COLOR_RGB);
        final int max = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_max, ColorUtil.MAX_VALUE_COLOR_RGB);

        // Set the view attributes
        this.setMax((max - min) / step);
        this.setProgress((max - min) / 2);

        // When the MinMaxSeekBar is initialized, its situated exactly between min and max
        value = (min + max) / 2;

        // Set the action when the user touches the SeekBar
        this.setOnSeekBarChangeListener(
                new OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    // When the SeekBar progress is modified
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        // We calculate the real SeekBar value
                        value = min + (progress * step);
                    }
                }
        );
    }

    /***
     * @return The "real" value of the SeekBar (calculated depending on the min, max and step values)
     */
    public int getValue() {
        return value;
    }
}
