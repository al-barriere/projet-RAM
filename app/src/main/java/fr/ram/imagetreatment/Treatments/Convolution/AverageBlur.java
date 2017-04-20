package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;

/**
 * Created by Maxime on 10/02/2017.
 */

public class AverageBlur extends Convolution {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        // Creation of a mask that will be applied to the pixel of the image
        int maskSize = args.getInt(BundleArgs.MASK_SIZE);
        double[][] mask = new double[maskSize][maskSize];
        for (int i = 0; i < maskSize; i++)
            for (int j = 0; j < maskSize; j++)
                // Each case are the same value which is 1 divided by the size of matrix
                mask[i][j] = 1.0 / (maskSize * maskSize);

        args.putInt(BundleArgs.NB_MASK, 1);
        args.putSerializable(BundleArgs.MASK, mask);

        // Value of all pixels with the mask is between min and max
        args.putInt(BundleArgs.MIN, 0);
        args.putInt(BundleArgs.MAX, 255);

        return super._compute(bmp, args);
    }
}
