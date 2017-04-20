package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 09/02/2017.
 */

public class Contrast extends Treatment {
    /***
     *
     * Adapted from http://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int red, green, blue;
        int value = args.getInt(BundleArgs.VALUE);
        int size = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[size];
        double factor = (259 * (value + 255));
        factor = factor / (255 * (259 - value));
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < size; i++) {
            int pixel = pixels[i];
            double redF = factor * (Color.red(pixel) - 128);
            double greenF = factor * (Color.green(pixel) - 128);
            double blueF = factor * (Color.blue(pixel) - 128);
            red = (int) (redF + 128);
            green = (int) (greenF + 128);
            blue = (int) (blueF + 128);

            red = ColorUtil.overFlowColor(red);
            green = ColorUtil.overFlowColor(green);
            blue = ColorUtil.overFlowColor(blue);

            pixels[i] = Color.rgb(red, green, blue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
