package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;


/**
 * Created by AntoineB on 17-02-05.
 */

public class Exposure extends Treatment {
    /***
     * Return the given Bitmap with a modified exposure
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The given Bitmap
     */
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int red, green, blue;
        int value = args.getInt(BundleArgs.VALUE);
        int size = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[size];

        // Get the pixels of the input Bitmap
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < size; i++) {
            // Get the pixel value
            int pixel = pixels[i];

            // Change the exposure of the pixel
            red = Color.red(pixel) + value;
            green = Color.green(pixel) + value;
            blue = Color.blue(pixel) + value;

            // If the new pixel value is lower than 0 or higher than 255, set it respectively to 0 or 255
            red = ColorUtil.overFlowColor(red);
            green = ColorUtil.overFlowColor(green);
            blue = ColorUtil.overFlowColor(blue);

            // Save the new pixel value
            pixels[i] = Color.rgb(red, green, blue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
