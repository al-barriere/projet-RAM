package fr.ram.traitementimage.CustomViews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Rémi on 21/02/2017.
 */

public class CustomImageView extends ImageView {
    private boolean imageModified = false;

    public CustomImageView(Context context) {
        super(context);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImageModified(boolean imageModified) {
        this.imageModified = imageModified;
    }

    public boolean getImageModified() {
        return imageModified;
    }

    public Bitmap getImageBitmap() {
        return ((BitmapDrawable)(getDrawable())).getBitmap();
    }
}
