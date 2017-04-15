package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by remi on 15/04/2017.
 */

public class MedianFilter extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();
        int maskSize = args.getInt("maskSize");
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int[] pixelsOutput = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int x_pixelMatrix, y_pixelMatrix;

        ArrayList<Float> hues = new ArrayList<>();
        ArrayList<Float> saturations = new ArrayList<>();
        ArrayList<Float> values = new ArrayList<>();
        float hsv[] = new float[3];

        for (int i = 0; i < pixelsOutput.length; i++) {
            x_pixelMatrix = i % width;
            y_pixelMatrix = i / width;

            if (!(i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < maskSize / 2 || i % width >= width - (maskSize / 2))) {
                for (int x = x_pixelMatrix - (maskSize / 2); x <= x_pixelMatrix + (maskSize / 2); x++) {
                    for (int y = y_pixelMatrix - (maskSize / 2); y <= y_pixelMatrix + (maskSize / 2); y++) {
                        hsv = new float[3];
                        Color.colorToHSV(pixels[x + y * width], hsv);
                        hues.add(hsv[0]);
                        saturations.add(hsv[1]);
                        values.add(hsv[2]);
                    }
                }
                Collections.sort(hues);
                Collections.sort(saturations);
                Collections.sort(values);

                hsv[0] = hues.get(maskSize/2);
                hsv[1] = saturations.get(maskSize/2);
                hsv[2] = values.get(maskSize/2);

                hues.clear();
                saturations.clear();
                values.clear();

                pixelsOutput[i] = Color.HSVToColor(hsv);
            }
            else {
                pixelsOutput[i] = pixels[i];
            }
        }

        bmp.setPixels(pixelsOutput, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        return bmp;
    }
}
