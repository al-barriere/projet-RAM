package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Util.BundleArgs;

/**
 * Created by Maxime on 18/03/2017.
 */

public class Pencil extends Treatment {
    /**
     * Return the given Bitmap with a pencil drawing effect
     * @param bmp The Bitmap input
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    @Override
    public Bitmap render(Bitmap bmp, Bundle args) {
        // Create the Bitmap result
        Bitmap returnBitmap;

        // Apply the Sobel effect
        Sobel sobel = new Sobel();
        returnBitmap = sobel.render(bmp, args);

        // Inverse the colors
        InverseColor inverseColor = new InverseColor();
        returnBitmap = inverseColor.render(returnBitmap, args);

        // Apply a Gaussian blur in order to remove the imperfections
        args = new Bundle();
        args.putInt(BundleArgs.MASK_SIZE, 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        returnBitmap = gaussianBlur.render(returnBitmap, args);

        // Return the modified Bitmap
        return returnBitmap;
    }
}
