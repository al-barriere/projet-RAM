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

    private void init(Context context, AttributeSet attrs) {
        // Recover the params of the view
        TypedArray customParams = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedButton, 0, 0);

        final int step = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_step, STEP);
        final int min = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_min, -ColorUtil.MAX_VALUE_COLOR_RGB);
        final int max = customParams.getInteger(R.styleable.MinMaxSeekBar_min_max_seekbar_max, ColorUtil.MAX_VALUE_COLOR_RGB);

        this.setMax((max - min) / step);
        this.setProgress((max - min) / 2);
        value = 0;

        this.setOnSeekBarChangeListener(
                new OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress,
                                                  boolean fromUser) {
                        setValue(min + (progress * step));
                    }
                }
        );
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
