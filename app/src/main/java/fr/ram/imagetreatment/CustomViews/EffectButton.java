package fr.ram.imagetreatment.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import fr.ram.imagetreatment.R;

/**
 * Created by Rémi on 01/03/2017.
 */

/***
 * Custom button used in ImageTreatmentActivity for applying effects
 */
public class EffectButton extends FrameLayout {
    private ImageView icon;
    private TextView text;
    private TypedArray customParams;

    /***
     * Constructor of the class
     * @param context
     * @param attrs
     */
    public EffectButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /***
     * Initialize the view
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        // Inflates the view
        View view = inflate(getContext(), R.layout.effect_button_layout, null);
        addView(view);

        // Get the views
        icon = (ImageView) findViewById(R.id.icon);
        text = (TextView) findViewById(R.id.text);

        if (attrs != null) {
            // Recover the params of the view
            customParams = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EffectButton, 0, 0);
            // Set the icon and the text passed in XML args
            icon.setImageDrawable(customParams.getDrawable(R.styleable.EffectButton_effect_button_icon));
            text.setText(customParams.getString(R.styleable.EffectButton_effect_button_text));
        }
    }
}
