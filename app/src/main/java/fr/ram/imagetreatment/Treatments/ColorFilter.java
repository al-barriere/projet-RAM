package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.CustomViews.CustomImageView;

import static android.graphics.Color.RGBToHSV;

/**
 * Created by AntoineB on 17-02-05.
 * You choose a color. It will be display and the others one will be change to the gray color
 */

public class ColorFilter extends Treatment {

    @Override
    public void compute(CustomImageView img, Bundle args) {
        super.compute(img, args);

        Bitmap bmp = img.getImageBitmap();

        int red, blue, green, rgb;
        int min = 50;
        int color = args.getInt("color");
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

        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
}
