package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Remi on 10/02/2017.
 */

public class Gaussien extends Convolution {
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int size = width * height;
        int[] pixels = new int[size];
        int[] newPixels = new int[size];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int maskSize = b.getInt("mask_size");
        double[][] mask = new double[maskSize][maskSize];
        int alpha = 10;

        int tempColorRGB;
        float[] tempColorHSV = new float[3];

        for (int i = 0; i < maskSize; i++) {
            int x = i - maskSize / 2;
            for (int j = 0; j < maskSize; j++) {
                int y = j - maskSize / 2;
                mask[i][j] = Math.exp(-((Math.pow(x, 2) + Math.pow(y, 2) / 2 * Math.pow(alpha, 2))));
            }
        }

        for (int i = 0; i < newPixels.length; i++) {
            if (i <= width || i >= width * (height - 1) || i % width == 0 || i % width == width - 1) {
                newPixels[i] = pixels[i];
            } else {
                Color.colorToHSV(pixels[i], tempColorHSV);
            }

        }
        bmp.setPixels(newPixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
}
