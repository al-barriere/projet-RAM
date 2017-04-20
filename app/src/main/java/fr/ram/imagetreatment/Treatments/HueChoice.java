package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by AntoineB on 17-02-05.
 */

public class HueChoice extends Treatment {
    /***
     * Return the input Bitmap with a modified hue
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int size = bmp.getWidth() * bmp.getHeight();
        int hue = args.getInt(BundleArgs.VALUE);
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        // Get the pixels of the Bitmap
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < size; i++) {
            // Get the HSV values of the pixel
            Color.colorToHSV(pixels[i], hsv);
            // Modify its hue
            hsv[0] = hue;
            // Apply its new HSV values
            pixels[i] = Color.HSVToColor(hsv);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
