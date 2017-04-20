package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 17/03/2017.
 */

public class InverseColor extends Treatment {
    /***
     * Return the input Bitmap with inversed colors
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            // Get the pixel values
            int pixel = pixels[i];
            int blue = Color.blue(pixel);
            int red = Color.red(pixel);
            int green = Color.green(pixel);

            // Inverse the color values
            red = ColorUtil.MAX_VALUE_COLOR_RGB - red;
            blue = ColorUtil.MAX_VALUE_COLOR_RGB - blue;
            green = ColorUtil.MAX_VALUE_COLOR_RGB - green;

            // Save the new value
            pixels[i] = Color.rgb(red, green, blue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
