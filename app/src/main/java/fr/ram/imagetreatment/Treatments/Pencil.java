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
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        Bitmap returnBitmap;

        Sobel sobel = new Sobel();
        returnBitmap = sobel._compute(bmp, args);

        InverseColor inverseColor = new InverseColor();
        returnBitmap = inverseColor._compute(returnBitmap, args);

        args = new Bundle();
        args.putInt(BundleArgs.MASK_SIZE, 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        returnBitmap = gaussianBlur._compute(returnBitmap, args);

        return returnBitmap;
    }
}
