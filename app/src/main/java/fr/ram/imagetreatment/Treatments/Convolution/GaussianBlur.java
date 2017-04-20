package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;

/**
 * Created by Remi on 10/02/2017.
 */

public class GaussianBlur extends Convolution {
    /***
     * Generate a gaussian mask and applies it
     * Mask generation algorithm adapted from http://www.programming-techniques.com/2013/02/gaussian-filter-generation-using-cc.html
     * @param bmp The input Bitmap
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    public Bitmap render(Bitmap bmp, Bundle args) {
        // Retrieve the mask size
        int maskSize = args.getInt(BundleArgs.MASK_SIZE);
        double[][] mask = new double[maskSize][maskSize];
        double maskValue;

        double sigma = 1.0f;
        double r, s = 2.0f * sigma * sigma;

        double sum = 0.0f;
        int shift = maskSize / 2;

        // Generate the Mask
        for (int x = -shift; x <= shift; x++) {
            for (int y = -shift; y <= shift; y++) {
                r = Math.sqrt(x * x + y * y);
                maskValue = (Math.exp(-(r * r) / s)) / (Math.PI * s);
                mask[x + shift][y + shift] = maskValue;
                sum += maskValue;
            }
        }

        // Normalize the Mask values (set it between 0 and 1)
        for (int i = 0; i < maskSize; ++i)
            for (int j = 0; j < maskSize; ++j)
                mask[i][j] /= sum;

        // Set the Convolution arguments
        args.putInt(BundleArgs.NB_MASK, 1);
        args.putSerializable(BundleArgs.MASK, mask);
        args.putInt(BundleArgs.MIN, 0);
        args.putInt(BundleArgs.MAX, 255);

        return super.render(bmp, args);
    }
}
