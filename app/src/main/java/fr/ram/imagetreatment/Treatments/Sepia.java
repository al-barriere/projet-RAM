package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 03/02/2017.
 */
public class Sepia extends Treatment {
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            int tmp_red = (red * 393 / 1000) + (green * 769 / 1000) + (blue * 189 / 1000);
            int tmp_blue = (red * 272 / 1000) + (green * 534 / 1000) + (blue * 131 / 1000);
            int tmp_green = (red * 349 / 1000) + (green * 686 / 1000) + (blue * 168 / 1000);

            tmp_red = ColorUtil.overFlowColor(tmp_red);
            tmp_green = ColorUtil.overFlowColor(tmp_green);
            tmp_blue = ColorUtil.overFlowColor(tmp_blue);

            pixels[i] = Color.rgb(tmp_red, tmp_green, tmp_blue);


        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        return bmp;
    }
}
