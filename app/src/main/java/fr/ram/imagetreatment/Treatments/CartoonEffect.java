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
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int size = width * height;

        Sobel sobel = new Sobel();
        Bitmap sobelBitmap = sobel._compute(bmp, args);

        MedianFilter medianFilter = new MedianFilter();
        Bitmap medianBitmap = medianFilter._compute(bmp, args);

        int pixelsSobel[] = new int[size];
        int pixelsMedian[] = new int[size];
        int pixels[] = new int[size];

        sobelBitmap.getPixels(pixelsSobel, 0, width, 0, 0, width, height);
        medianBitmap.getPixels(pixelsMedian, 0, width, 0, 0, width, height);

        for (int i = 0; i < pixelsSobel.length; i++) {
            int pixelContour = pixelsSobel[i];
            int pixelImage = pixelsMedian[i];

            int b = Color.blue(pixelContour);
            int r = Color.red(pixelContour);
            int g = Color.green(pixelContour);

            if (r > 127 && g > 127 && b > 127) {//si le pixel est blanc, donc represente un contour on dessinele contour en noir
                pixels[i] = Color.BLACK;
            } else {//sinon on dessine le pixel de l'image
                pixels[i] = pixelImage;
            }
        }

        return ImageFile.createBitmapFromPixels(pixels, width, height);
    }
}