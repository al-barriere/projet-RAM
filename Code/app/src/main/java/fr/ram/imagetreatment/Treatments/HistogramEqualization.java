package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 09/02/2017.
 */

public class HistogramEqualization extends Treatment {
    /***
     * Return the given Bitmap with its contrast ajusted by histogram equalization
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        Bitmap greyImage = bmp;
        int size = bmp.getWidth() * bmp.getHeight();
        int pixelsColor[] = new int[size];
        int pixelsColorRGB[][] = new int[size][3];
        int pixelsColorNew[] = new int[size];
        int pixelsShadesOfGrey[] = new int[size];
        int[] currentColor;
        int histogramShadesOfGrey[] = new int[ColorUtil.MAX_VALUE_COLOR_RGB + 1];
        int cumulateHistogramShadesOfGrey[] = new int[ColorUtil.MAX_VALUE_COLOR_RGB + 1];
        int tempColor;
        int red, green, blue;

        // Get the pixels of the input Bitmap
        bmp.getPixels(pixelsColor, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        // Save the pixel colors into a two dimension array
        for (int i = 0; i < size; i++) {
            pixelsColorRGB[i][0] = Color.red(pixelsColor[i]);
            pixelsColorRGB[i][1] = Color.green(pixelsColor[i]);
            pixelsColorRGB[i][2] = Color.blue(pixelsColor[i]);
        }

        // Apply the grey levels effect on a new Bitmap
        ShadesOfGrey sod = new ShadesOfGrey();
        greyImage = sod.render(greyImage, null);

        // Get the grey levels pixels
        greyImage.getPixels(pixelsShadesOfGrey, 0, greyImage.getWidth(), 0, 0, greyImage.getWidth(), greyImage.getHeight());

        // Calculate the histogram of the grey levels image
        for (int i = 0; i <= ColorUtil.MAX_VALUE_COLOR_RGB; i++) {
            histogramShadesOfGrey[i] = 0;
        }
        for (int i = 0; i < size; i++) {
            tempColor = Color.red(pixelsShadesOfGrey[i]);
            histogramShadesOfGrey[tempColor]++;
        }

        // Calculate the cumulate histogram of the grey levels image
        cumulateHistogramShadesOfGrey[0] = histogramShadesOfGrey[0];
        for (int i = 1; i <= ColorUtil.MAX_VALUE_COLOR_RGB; i++) {
            cumulateHistogramShadesOfGrey[i] = cumulateHistogramShadesOfGrey[i - 1] + histogramShadesOfGrey[i];
        }

        for (int i = 0; i < size; i++) {
            // Get the current color of the pixel
            currentColor = pixelsColorRGB[i];

            // Calculate the new color value
            red = (cumulateHistogramShadesOfGrey[currentColor[0]] * ColorUtil.MAX_VALUE_COLOR_RGB) / size;
            green = (cumulateHistogramShadesOfGrey[currentColor[1]] * ColorUtil.MAX_VALUE_COLOR_RGB) / size;
            blue = (cumulateHistogramShadesOfGrey[currentColor[2]] * ColorUtil.MAX_VALUE_COLOR_RGB) / size;

            // Apply the new color value
            pixelsColorNew[i] = Color.rgb(red, green, blue);
        }

        return ImageFile.createBitmapFromPixels(pixelsColorNew, bmp.getWidth(), bmp.getHeight());
    }
}
