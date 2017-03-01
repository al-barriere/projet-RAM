package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.traitementimage.Util.ColorUtil;
import fr.ram.traitementimage.CustomView.CustomImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public class ShadesOfGray extends Treatment {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        super.compute(img, args);

        Bitmap bmp = img.getImageBitmap();
        calcul(bmp);

        img.setImageBitmap(bmp);
    }

    public void calcul(Bitmap bmp) {
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
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
    }
}
