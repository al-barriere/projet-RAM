package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Maxime on 09/02/2017.
 */

public class HistogramEqualization extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap image = img.getImageBitmap();
        Bitmap greyImage = image;
        int size = image.getWidth() * image.getHeight();
        int pixelsColor[] = new int[size];
        int pixelsColorRGB[][] = new int[size][3];
        int pixelsColorNew[] = new int[size];
        int pixelsShadesOfGrey[] = new int[size];
        int histogramShadesOfGrey[] = new int[256];
        int cumulateHistogramShadesOfGrey[] = new int[256];
        int tempColor;
        int red, green, blue;
        ShadesOfGrey sod = new ShadesOfGrey();

        image.getPixels(pixelsColor, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

        for (int i = 0; i < size; i++) {
            pixelsColorRGB[i][0] = Color.red(pixelsColor[i]);
            pixelsColorRGB[i][1] = Color.green(pixelsColor[i]);
            pixelsColorRGB[i][2] = Color.blue(pixelsColor[i]);
        }

        sod._compute(greyImage);
        greyImage.getPixels(pixelsShadesOfGrey, 0, greyImage.getWidth(), 0, 0, greyImage.getWidth(), greyImage.getHeight());

        for (int i = 0; i <= 255; i++) {
            histogramShadesOfGrey[i] = 0;
        }

        for (int i = 0; i < size; i++) {
            tempColor = Color.red(pixelsShadesOfGrey[i]);
            histogramShadesOfGrey[tempColor]++;
        }

        cumulateHistogramShadesOfGrey[0] = histogramShadesOfGrey[0];

        for (int i = 1; i <= 255; i++) {
            cumulateHistogramShadesOfGrey[i] = cumulateHistogramShadesOfGrey[i - 1] + histogramShadesOfGrey[i];
        }

        for (int i = 0; i < size; i++) {
            red = (cumulateHistogramShadesOfGrey[pixelsColorRGB[i][0]] * 255) / size;
            green = (cumulateHistogramShadesOfGrey[pixelsColorRGB[i][1]] * 255) / size;
            blue = (cumulateHistogramShadesOfGrey[pixelsColorRGB[i][2]] * 255) / size;
            pixelsColorNew[i] = Color.rgb(red, green, blue);
        }

        image.setPixels(pixelsColorNew, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        return image;
    }
}
