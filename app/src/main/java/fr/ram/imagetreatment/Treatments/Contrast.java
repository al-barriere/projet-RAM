package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Maxime on 09/02/2017.
 */

public class Contrast extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();
        //  Bitmap greyBitmap = bmp.copy(bmp.getConfig(), true);
        int imageSize = bmp.getWidth() * bmp.getHeight();
        int pixels[] = new int[imageSize];
        //int pixelsGrey[] = new int[imageSize];
        int pixelsNew[] = new int[imageSize];
        int min = 0, max = 255;
        int lut[] = new int[256];
        int color[][] = new int[imageSize][3];
        int colorGrey[] = new int[imageSize];
        int red, green, blue;
        int diff;
        int tempColor;

        //ShadesOfGray shadesOfGray = new ShadesOfGray();
        // shadesOfGray._compute(greyBitmap);

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        // greyBitmap.getPixels(pixelsGrey, 0, greyBitmap.getWidth(), 0, 0, greyBitmap.getWidth(), greyBitmap.getHeight());

        for (int i = 0; i < imageSize; i++) {
            color[i][0] = Color.red(pixels[i]);
            color[i][1] = Color.green(pixels[i]);
            color[i][2] = Color.blue(pixels[i]);

            colorGrey[i] = Color.red(pixels[i]);

            tempColor = colorGrey[i];
            if (tempColor < min) {
                min = tempColor;
            } else if (tempColor > max) {
                max = tempColor;
            }
        }

        diff = (max - min > 0) ? max - min : 1;
        for (int i = 0; i <= 255; i++) {
            lut[i] = (255 * (i - min)) / diff;
        }

        for (int i = 0; i < imageSize; i++) {
            red = lut[color[i][0]];
            green = lut[color[i][1]];
            blue = lut[color[i][2]];
            pixelsNew[i] = Color.rgb(red, green, blue);
        }

        bmp.setPixels(pixelsNew, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        return bmp;
    }
}
