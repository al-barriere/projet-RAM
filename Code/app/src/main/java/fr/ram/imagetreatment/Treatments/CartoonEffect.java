package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 13/04/2017.
 */

public class CartoonEffect extends Treatment {
    private static int MIN_VALUE_WHITE = 127;

    /***
     * Return the cartoon version of the given Bitmap
     * It applies two filters : a Sobel and a Median
     * @param bmp The input Bitmap
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int size = width * height;

        // We create an image with the outline of the input Bitmap
        Sobel sobel = new Sobel();
        Bitmap sobelBitmap = sobel.render(bmp, args);

        // We apply a median filter on the input Bitmap and save it in a new Bitmap
        MedianFilter medianFilter = new MedianFilter();
        Bitmap medianBitmap = medianFilter.render(bmp, args);

        int pixelsSobel[] = new int[size];
        int pixelsMedian[] = new int[size];
        int pixels[] = new int[size];

        // We retrieve the pixels of the two generated images
        sobelBitmap.getPixels(pixelsSobel, 0, width, 0, 0, width, height);
        medianBitmap.getPixels(pixelsMedian, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixelsSobel.length; i++) {
            int pixelOutline = pixelsSobel[i];
            int pixelImage = pixelsMedian[i];

            int r = Color.red(pixelOutline);
            int g = Color.green(pixelOutline);
            int b = Color.blue(pixelOutline);

            // If the pixel is more of less white <=> If the pixel represents an outline
            if (r > MIN_VALUE_WHITE && g > MIN_VALUE_WHITE && b > MIN_VALUE_WHITE) {
                // We draw the outline
                pixels[i] = Color.BLACK;
            } else {
                // We draw the color in the median image
                pixels[i] = pixelImage;
            }
        }

        return ImageFile.createBitmapFromPixels(pixels, width, height);
    }
}