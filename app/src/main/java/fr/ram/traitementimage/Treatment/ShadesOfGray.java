package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public class ShadesOfGray implements Treatment {

    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            int newColor = (red * 30 / 100) + (green * 59 / 100) + (blue * 11 / 100);

            pixels[i] = Color.rgb(newColor, newColor, newColor);

        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());


        img.setImageBitmap(bmp);
    }
}
