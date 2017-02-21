package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.traitementimage.Treatment.Treatment;

/**
 * Created by Maxime on 10/02/2017.
 */

public abstract class Convolution implements Treatment {

    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int maskSize = b.getInt("mask_size");
        double[][] mask = (double[][]) b.get("mask");

        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int[] pixelMoyenneur = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int red, blue, green, x_pixelMatrix, y_pixelMatrix;
        float redF, blueF, greenF;
        for (int i = 0; i < pixelMoyenneur.length; i++) {
            redF = blueF = greenF = 0;
            x_pixelMatrix = i % width;
            y_pixelMatrix = i / width;

            if (i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < maskSize / 2 || i % width >= width - (maskSize / 2)) {
                redF = Color.red(pixels[i]);
                greenF = Color.green(pixels[i]);
                blueF = Color.blue(pixels[i]);
            } else {
                int cpti = 0;
                int cptj = 0;
                for (int x = x_pixelMatrix - (maskSize / 2); x <= x_pixelMatrix + (maskSize / 2); x++) {
                    for (int y = y_pixelMatrix - (maskSize / 2); y <= y_pixelMatrix + (maskSize / 2); y++) {
                        redF += Color.red(pixels[x + y * width]) * mask[cpti][cptj];
                        greenF += Color.green(pixels[x + y * width]) * mask[cpti][cptj];
                        blueF += Color.blue(pixels[x + y * width]) * mask[cpti][cptj];

                        cptj++;
                    }
                    cpti++;
                    cptj = 0;
                }
            }

            red = (int) redF;
            green = (int) greenF;
            blue = (int) blueF;
            pixelMoyenneur[i] = Color.rgb(red, green, blue);
        }

        bmp.setPixels(pixelMoyenneur, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
}
