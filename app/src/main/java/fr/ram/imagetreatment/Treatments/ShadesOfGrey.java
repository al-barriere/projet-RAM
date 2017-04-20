package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 03/02/2017.
 */
public class ShadesOfGrey extends Treatment {
    /***
     * Return the grey levels version of the given Bitmap
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            int pixelColor = pixels[i];
            int red = Color.red(pixelColor);
            int green = Color.green(pixelColor);
            int blue = Color.blue(pixelColor);

            // Calculate the new value of the pixel
            int newColor = ColorUtil.pixelToGrey(red, green, blue);

            // Apply the new color
            pixels[i] = Color.rgb(newColor, newColor, newColor);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
