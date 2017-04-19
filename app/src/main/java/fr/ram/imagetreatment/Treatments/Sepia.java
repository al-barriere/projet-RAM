package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 03/02/2017.
 */
public class Sepia extends Treatment {
    /***
     * Return the sepia version of the given Bitmap
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            // We retrieve the pixel value
            int pixelValue = pixels[i];
            int red = Color.red(pixelValue);
            int green = Color.green(pixelValue);
            int blue = Color.blue(pixelValue);

            // Get the sepia values
            int[] pixelsSepia = ColorUtil.pixelToSepia(red, green, blue);
            int newRed = pixelsSepia[0];
            int newGreen = pixelsSepia[1];
            int newBlue = pixelsSepia[2];

            // If the values are too high we set it to 255
            newRed = ColorUtil.overFlowColor(newRed);
            newGreen = ColorUtil.overFlowColor(newGreen);
            newBlue = ColorUtil.overFlowColor(newBlue);

            // We save the new pixel value
            pixels[i] = Color.rgb(newRed, newGreen, newBlue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
