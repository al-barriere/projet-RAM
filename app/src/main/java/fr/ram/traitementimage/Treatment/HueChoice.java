package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by AntoineB on 17-02-05.
 * You choose what is the hue of your bitmap
 */

public class HueChoice implements Treatment {

    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int size = bmp.getWidth() * bmp.getHeight();
        //////
        int hue=4;
        //////
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0;i<size;i++){
            Color.colorToHSV(pixels[i],hsv);
            hsv[0] = hue;
            pixels[i] = Color.HSVToColor(hsv);
        }
        bmp.setPixels(pixels, 0, img.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
}
