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
     *
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return
     */
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            red = ColorUtil.overFlowColor(ColorUtil.MAX_VALUE_COLOR_RGB - red);
            blue = ColorUtil.overFlowColor(ColorUtil.MAX_VALUE_COLOR_RGB - blue);
            green = ColorUtil.overFlowColor(ColorUtil.MAX_VALUE_COLOR_RGB - green);

            pixels[i] = Color.rgb(red, green, blue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
