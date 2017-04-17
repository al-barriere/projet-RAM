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
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            int newColor = ColorUtil.pixelToGrey(red, green, blue);

            pixels[i] = Color.rgb(newColor, newColor, newColor);
        }
        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
