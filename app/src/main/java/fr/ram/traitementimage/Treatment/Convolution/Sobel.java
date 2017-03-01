package fr.ram.traitementimage.Treatment.Convolution;

import android.os.Bundle;

import fr.ram.traitementimage.CustomView.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        args = new Bundle();
        args.putInt("mask_size", maskSize);
        args.putSerializable("mask", mask);

        super.compute(img, args);
    }
}
