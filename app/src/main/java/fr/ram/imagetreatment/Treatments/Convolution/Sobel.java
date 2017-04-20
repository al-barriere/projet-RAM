package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        Bitmap returnBitmap;

        // Creation of masks that will be applied to the pixel of the image
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] mask2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // Before applying the filter, image is transformed into a shade of gray
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        returnBitmap = shadesOfGray.render(bmp, null);

        args = new Bundle();
        args.putInt(BundleArgs.MASK_SIZE, 3);

        // Apply a Gaussian blur in order to remove the imperfections
        GaussianBlur gaussianBlur = new GaussianBlur();
        returnBitmap = gaussianBlur.render(returnBitmap, args);

        args = new Bundle();
        args.putInt(BundleArgs.NB_MASK, 2);
        // First mask
        args.putInt(BundleArgs.MASK_SIZE, maskSize);
        args.putSerializable(BundleArgs.MASK, mask);
        // Second mask
        args.putInt(BundleArgs.MASK_2_SIZE, maskSize);
        args.putSerializable(BundleArgs.MASK_2, mask2);
        // Value of all pixels with the mask is between min and max
        args.putInt(BundleArgs.MIN, -4 * ColorUtil.MAX_VALUE_COLOR_RGB);
        args.putInt(BundleArgs.MAX, 4 * ColorUtil.MAX_VALUE_COLOR_RGB);
        returnBitmap = super.render(returnBitmap, args);

        return returnBitmap;
    }
}
