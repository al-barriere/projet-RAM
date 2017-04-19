package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by remi on 15/04/2017.
 */

public class MedianFilter extends Treatment {
    /***
     * Return the given Bitmap with a median filter
     * This filter consists in taking the median value of the pixels around it
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int maskSize = 3;

        // Get the pixels of the input Bitmap
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int[] pixelsOutput = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int xPixelMatrix, yPixelMatrix;

        ArrayList<Float> hues = new ArrayList<>();
        ArrayList<Float> saturations = new ArrayList<>();
        ArrayList<Float> values = new ArrayList<>();
        float hsv[] = new float[3];

        for (int i = 0; i < pixelsOutput.length; i++) {
            xPixelMatrix = i % width;
            yPixelMatrix = i / width;

            if (i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < (maskSize / 2) || i % width >= width - (maskSize / 2)) {
                pixelsOutput[i] = pixels[i];
            } else {
                for (int x = xPixelMatrix - (maskSize / 2); x <= xPixelMatrix + (maskSize / 2); x++) {
                    for (int y = yPixelMatrix - (maskSize / 2); y <= yPixelMatrix + (maskSize / 2); y++) {
                        // Get the HSV values of the pixel
                        hsv = new float[3];
                        Color.colorToHSV(pixels[x + y * width], hsv);

                        // Save the HSV values
                        hues.add(hsv[0]);
                        saturations.add(hsv[1]);
                        values.add(hsv[2]);
                    }
                }

                // Sort the values
                Collections.sort(hues);
                Collections.sort(saturations);
                Collections.sort(values);

                // Get the median values
                hsv[0] = hues.get(maskSize / 2);
                hsv[1] = saturations.get(maskSize / 2);
                hsv[2] = values.get(maskSize / 2);

                // Clear the ArrayLists
                hues.clear();
                saturations.clear();
                values.clear();

                // Set the new pixel value
                pixelsOutput[i] = Color.HSVToColor(hsv);
            }
        }

        return ImageFile.createBitmapFromPixels(pixelsOutput, bmp.getWidth(), bmp.getHeight());
    }
}
