package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by AntoineB on 17-02-05.
 * You choose what is the hue of your bitmap
 */

public class HueChoice extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();
        int size = bmp.getWidth() * bmp.getHeight();
        int hue = args.getInt("value");
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < size; i++) {
            Color.colorToHSV(pixels[i], hsv);
            hsv[0] = hue;
            pixels[i] = Color.HSVToColor(hsv);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        return bmp;
    }
}
