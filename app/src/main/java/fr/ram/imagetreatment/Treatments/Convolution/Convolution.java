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
        int minPixel=args.getInt(BundleArgs.MIN);
        int maxPixel=args.getInt(BundleArgs.MAX);
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
        //x and y of current pixel
        int x_pixelMatrix, y_pixelMatrix;
        int red, blue, green;
        //represent color pixel after apply mask1 or mask2
        float redMask, blueMask, greenMask, redMask2, blueMask2, greenMask2;
        if (nbMask == 1) {//if we are only 1 mask to apply
            for (int i = 0; i < pixelsOutput.length; i++) {
                redMask = blueMask = greenMask = 0;
                x_pixelMatrix = i % width;
                y_pixelMatrix = i / width;

                //if it's on the edges of the image don't apply the mask
                if (i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < maskSize / 2 || i % width >= width - (maskSize / 2)) {
                    redMask = Color.red(pixels[i]);
                    greenMask = Color.green(pixels[i]);
                    blueMask = Color.blue(pixels[i]);
                } else {//else it's in the center
                    int cpti = 0;
                    int cptj = 0;
                    //browse neighbors, value of current pixel is the sum of this neighbors multiply by the filter
                    for (int x = x_pixelMatrix - (maskSize / 2); x <= x_pixelMatrix + (maskSize / 2); x++) {
                        for (int y = y_pixelMatrix - (maskSize / 2); y <= y_pixelMatrix + (maskSize / 2); y++) {
                            redMask += Color.red(pixels[x + y * width]) * mask[cpti][cptj];
                            greenMask += Color.green(pixels[x + y * width]) * mask[cpti][cptj];
                            blueMask += Color.blue(pixels[x + y * width]) * mask[cpti][cptj];

                            cptj++;
                        }
                        cpti++;
                        cptj = 0;
                    }
                }
                //if the interval of pixels is not between 0 and 255, set value proportionally for the interval is to 0 and 255
                red = ColorUtil.changeColorInterval((int) redMask,minPixel,maxPixel);
                green = ColorUtil.changeColorInterval((int) greenMask,minPixel,maxPixel);
                blue = ColorUtil.changeColorInterval((int) blueMask,minPixel,maxPixel);
                pixelsOutput[i] = Color.rgb(red, green, blue);
            }
        } else {//nbMask ==2 else we are 2 mask to apply
            for (int i = 0; i < pixelsOutput.length; i++) {
                redMask = blueMask = greenMask = 0;
                redMask2 = blueMask2 = greenMask2 = 0;
                x_pixelMatrix = i % width;
                y_pixelMatrix = i / width;

                //if it's on the edges of the image don't apply the mask
                if (i <= width * (maskSize / 2) || i >= width * (height - (maskSize / 2)) || i % width < maskSize / 2 || i % width >= width - (maskSize / 2)) {
                    redMask = Color.red(pixels[i]);
                    greenMask = Color.green(pixels[i]);
                    blueMask = Color.blue(pixels[i]);
                    redMask2 = Color.red(pixels[i]);
                    greenMask2 = Color.green(pixels[i]);
                    blueMask2 = Color.blue(pixels[i]);

                } else {//else it's in the center
                    int cpti = 0;
                    int cptj = 0;
                    for (int x = x_pixelMatrix - (maskSize / 2); x <= x_pixelMatrix + (maskSize / 2); x++) {
                        for (int y = y_pixelMatrix - (maskSize / 2); y <= y_pixelMatrix + (maskSize / 2); y++) {
                            //apply first mask
                            redMask += Color.red(pixels[x + y * width]) * mask[cpti][cptj];
                            greenMask += Color.green(pixels[x + y * width]) * mask[cpti][cptj];
                            blueMask += Color.blue(pixels[x + y * width]) * mask[cpti][cptj];

                            //apply second mask
                            redMask2 += Color.red(pixels[x + y * width]) * mask2[cpti][cptj];
                            greenMask2 += Color.green(pixels[x + y * width]) * mask2[cpti][cptj];
                            blueMask2 += Color.blue(pixels[x + y * width]) * mask2[cpti][cptj];


                            cptj++;
                        }
                        cpti++;
                        cptj = 0;
                    }
                }
                //if the new pixel value is lower than 0 or higher than 255, set it respectively to 0 or 255
                //and make an approximation with the first and second pixel
                red = ColorUtil.overFlowColor((int) Math.sqrt(redMask * redMask + redMask2 * redMask2));
                green = ColorUtil.overFlowColor((int) Math.sqrt(greenMask * greenMask + greenMask2 * greenMask2));
                blue = ColorUtil.overFlowColor((int) Math.sqrt(blueMask * blueMask + blueMask2 * blueMask2));
                pixelsOutput[i] = Color.rgb(red, green, blue);
            }

        }
        return ImageFile.createBitmapFromPixels(pixelsOutput, bmp.getWidth(), bmp.getHeight());
    }


}
