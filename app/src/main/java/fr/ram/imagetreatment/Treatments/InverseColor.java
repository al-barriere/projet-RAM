package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 17/03/2017.
 */

public class InverseColor extends Treatment {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        super.compute(img, args);

        Bitmap bmp = img.getImageBitmap();
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            int tmp_red = 254-red;
            int tmp_blue = 254-blue;
            int tmp_green = 254-green;

            if (tmp_red <0 )
                tmp_red = 0;
            if (tmp_green < 0)
                tmp_green = 0;
            if (tmp_blue < 0)
                tmp_blue = 0;

            pixels[i] = Color.rgb(tmp_red, tmp_green, tmp_blue);


        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        img.setImageBitmap(bmp);
    }


}
