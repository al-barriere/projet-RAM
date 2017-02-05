package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by AntoineB on 17-02-05.
 * You choose what is the hue of your bitmap
 */

public class HueChoice extends Treatment {

    /*
    Apply the effect of "The choice of the hue"
     */
    public static void applyFilter(Bitmap img, int hue){
        int size = img.getWidth() * img.getHeight();
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for(int i=0;i<size;i++){
            Color.colorToHSV(pixels[i],hsv);
            hsv[0] = hue;
            pixels[i] = Color.HSVToColor(hsv);
        }
        img.setPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
    }
}
