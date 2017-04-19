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
 * You choose a color. It will be display and the others one will be change to the gray color
 */

public class ColorFilter extends Treatment {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int red, blue, green, rgb;
        int min = 50;
        int color = args.getInt(BundleArgs.COLOR);
        int size = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < size; i++) {
            rgb = pixels[i];

            red = Color.red(rgb);
            green = Color.green(rgb);
            blue = Color.blue(rgb);
            RGBToHSV(red, green, blue, hsv);
            if (hsv[0] < (float) color - min || hsv[0] > (float) color + min) {
                rgb = ColorUtil.pixelToGrey(red, green, blue);
                rgb = Color.rgb(rgb, rgb, rgb);
            } else {
                rgb = Color.rgb(red, green, blue);
            }
            pixels[i] = rgb;
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
