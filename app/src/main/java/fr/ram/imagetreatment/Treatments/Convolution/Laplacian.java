package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Laplacian extends Convolution {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};

        args = new Bundle();
        args.putInt("nbMask",1);
        args.putInt("mask_size", maskSize);
        args.putSerializable("mask", mask);

        return super._compute(img, args);
    }
}
