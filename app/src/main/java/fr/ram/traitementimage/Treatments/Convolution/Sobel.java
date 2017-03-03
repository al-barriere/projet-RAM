package fr.ram.traitementimage.Treatments.Convolution;

import android.os.Bundle;

import fr.ram.traitementimage.CustomViews.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] mask2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        args = new Bundle();
        args.putInt("nbMask", 2);
        args.putInt("mask_size", maskSize);
        args.putSerializable("mask", mask);
        args.putInt("mask2_size", maskSize);
        args.putSerializable("mask2", mask2);

        super.compute(img, args);
    }
}
