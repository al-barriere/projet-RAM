package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.ShadesOfGray;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] mask2 = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        ShadesOfGray shadesOfGray = new ShadesOfGray();
        shadesOfGray._compute(img, null);
        args = new Bundle();

        args.putInt("mask_size", 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur._compute(img, args);

        args = new Bundle();
        args.putInt("nbMask", 2);
        args.putInt("mask_size", maskSize);
        args.putSerializable("mask", mask);
        args.putInt("mask2_size", maskSize);
        args.putSerializable("mask2", mask2);

        return super._compute(img, args);
    }
}
