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

public class RoundedButton extends FrameLayout {
    private ImageView icon;
    private TextView text;
    private TypedArray customParams;

    public RoundedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = inflate(getContext(), R.layout.rounded_button_layout, null);
        addView(view);

        icon = (ImageView) findViewById(R.id.icon);
        text = (TextView) findViewById(R.id.text);

        if (attrs != null) {
            customParams = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedButton, 0, 0);
            icon.setImageDrawable(customParams.getDrawable(R.styleable.RoundedButton_rounded_button_icon));
            text.setText(customParams.getString(R.styleable.RoundedButton_rounded_button_text));
        }
    }
}
