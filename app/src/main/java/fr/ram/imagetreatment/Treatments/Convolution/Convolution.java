package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.Treatment;
import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;

/**
 * Created by Maxime on 10/02/2017.
 */

public abstract class Convolution extends Treatment {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int nbMask = args.getInt(BundleArgs.NB_MASK);
        int maskSize = args.getInt(BundleArgs.MASK_SIZE);
        int min=args.getInt(BundleArgs.MIN);
        int max=args.getInt(BundleArgs.MAX);
        double[][] mask = (double[][]) args.get(BundleArgs.MASK);
        double[][] mask2 = null;
        if (nbMask == 2) {
            mask2 = (double[][]) args.get(BundleArgs.MASK_2);
        }
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        int[] pixelsOutput = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int red, blue, green, x_pixelMatrix, y_pixelMatrix;
        float redF, blueF, greenF;
        float redF2, blueF2, greenF2;
        if (nbMask == 1) {
            for (int i = 0; i < pixelsOutput.length; i++) {
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
                red = ColorUtil.changeColorInterval((int) redF,min,max);
                green = ColorUtil.changeColorInterval((int) greenF,min,max);
                blue = ColorUtil.changeColorInterval((int) blueF,min,max);
                pixelsOutput[i] = Color.rgb(red, green, blue);
            }
        } else {//nbMask ==2
            for (int i = 0; i < pixelsOutput.length; i++) {
                redF = blueF = greenF = 0;
                redF2 = blueF2 = greenF2 = 0;
                x_pixelMatrix = i % width;
                y_pixelMatrix = i / width;

                if (i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < maskSize / 2 || i % width >= width - (maskSize / 2)) {
                    redF = Color.red(pixels[i]);
                    greenF = Color.green(pixels[i]);
                    blueF = Color.blue(pixels[i]);
                    redF2 = Color.red(pixels[i]);
                    greenF2 = Color.green(pixels[i]);
                    blueF2 = Color.blue(pixels[i]);

                } else {
                    int cpti = 0;
                    int cptj = 0;
                    for (int x = x_pixelMatrix - (maskSize / 2); x <= x_pixelMatrix + (maskSize / 2); x++) {
                        for (int y = y_pixelMatrix - (maskSize / 2); y <= y_pixelMatrix + (maskSize / 2); y++) {
                            redF += Color.red(pixels[x + y * width]) * mask[cpti][cptj];
                            greenF += Color.green(pixels[x + y * width]) * mask[cpti][cptj];
                            blueF += Color.blue(pixels[x + y * width]) * mask[cpti][cptj];

                            redF2 += Color.red(pixels[x + y * width]) * mask2[cpti][cptj];
                            greenF2 += Color.green(pixels[x + y * width]) * mask2[cpti][cptj];
                            blueF2 += Color.blue(pixels[x + y * width]) * mask2[cpti][cptj];


                            cptj++;
                        }
                        cpti++;
                        cptj = 0;
                    }
                }
                red = ColorUtil.overFlowColor((int) Math.sqrt(redF * redF + redF2 * redF2));
                green = ColorUtil.overFlowColor((int) Math.sqrt(greenF * greenF + greenF2 * greenF2));
                blue = ColorUtil.overFlowColor((int) Math.sqrt(blueF * blueF + blueF2 * blueF2));
                pixelsOutput[i] = Color.rgb(red, green, blue);
            }

        }
        return ImageFile.createBitmapFromPixels(pixelsOutput, bmp.getWidth(), bmp.getHeight());
    }


}
