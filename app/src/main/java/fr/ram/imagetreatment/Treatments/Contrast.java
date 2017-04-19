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
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int red, green, blue;
        int value = args.getInt(BundleArgs.VALUE);
        int size = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[size];
        double factor = (259 * (value + 255));
        factor = factor / (255 * (259 - value));
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < size; i++) {
            int o = pixels[i];
            double redF = factor * (Color.red(o) - 128);
            double greenF = factor * (Color.green(o) - 128);
            double blueF = factor * (Color.blue(o) - 128);
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
