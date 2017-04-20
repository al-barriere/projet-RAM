package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

import static android.graphics.Color.RGBToHSV;

/**
 * Created by AntoineB on 17-02-05.
 */

public class ColorFilter extends Treatment {
    /***
     * Return a Bitmap with every pixels in grey levels except those who contain the given color
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int red, blue, green, pixel, newColorValue;
        int min = 50;
        int color = args.getInt(BundleArgs.COLOR);
        int size = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        // Get the pixels of the photo
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < size; i++) {
            // Get the current pixel
            pixel = pixels[i];

            red = Color.red(pixel);
            green = Color.green(pixel);
            blue = Color.blue(pixel);

            // Get the HSV equivalent to the pixel RGB color
            RGBToHSV(red, green, blue, hsv);

            // If the current pixel does not contain more or less the selected color
            if (hsv[0] < (float) (color - min) || hsv[0] > (float) (color + min)) {
                // We set the given pixel to grey levels
                newColorValue = ColorUtil.pixelToGrey(red, green, blue);
                pixel = Color.rgb(newColorValue, newColorValue, newColorValue);
                pixels[i] = pixel;
            }
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
