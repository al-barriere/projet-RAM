package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.Util.BundleArgs;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Laplacian extends Convolution {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        Bitmap returnBitmap;

        //creation of a mask that will be applied to the pixel of the image
        int maskSize = 3;
        double[][] mask = {{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};
        //before applying the filter, image is transformed into a shade of gray
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        returnBitmap = shadesOfGray._compute(bmp, null);

        args = new Bundle();
        args.putInt(BundleArgs.NB_MASK, 1);
        args.putInt(BundleArgs.MASK_SIZE, maskSize);
        args.putSerializable(BundleArgs.MASK, mask);
        //value of all pixels with the mask is between min and max
        args.putInt(BundleArgs.MIN, -255 * 8);
        args.putInt(BundleArgs.MAX, 255 * 8);
        returnBitmap = super._compute(returnBitmap, args);

        //after applying the filter, the range of pixel values of the image is extended for better sharpness
        HistogramEqualization he = new HistogramEqualization();
        returnBitmap = he._compute(returnBitmap, null);

        return returnBitmap;
    }
}
