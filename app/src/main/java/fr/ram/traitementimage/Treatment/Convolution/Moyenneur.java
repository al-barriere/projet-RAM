package fr.ram.traitementimage.Treatment.Convolution;

import android.os.Bundle;

import fr.ram.traitementimage.CustomView.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Moyenneur extends Convolution {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        int maskSize = args.getInt("mask_size");
        double[][] mask = new double[maskSize][maskSize];
        for (int i = 0; i < maskSize; i++)
            for (int j = 0; j < maskSize; j++)
                mask[i][j] = 1.0 / (maskSize * maskSize);

        args.putSerializable("mask", mask);

        super.compute(img, args);
    }
}
