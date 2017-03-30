package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Remi on 10/02/2017.
 */

public class GaussianBlur extends Convolution {
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = args.getInt("mask_size");
        double[][] mask = new double[maskSize][maskSize];

        double sigma = 1.0;
        double r, s = 2.0 * sigma * sigma;

        double sum = 0.0;
        int shift = maskSize / 2;

        for (int x = -shift; x <= shift; x++) {
            for (int y = -shift; y <= shift; y++) {
                r = Math.sqrt(x * x + y * y);
                mask[x + shift][y + shift] = (Math.exp(-(r * r) / s)) / (Math.PI * s);
                sum += mask[x + shift][y + shift];
            }
        }

        for (int i = 0; i < maskSize; ++i)
            for (int j = 0; j < maskSize; ++j)
                mask[i][j] /= sum;
        for (int i = 0; i < maskSize; ++i)
            for (int j = 0; j < maskSize; ++j)
                Log.i(String.valueOf(i)+"/"+String.valueOf(j),String.valueOf(mask[i][j]));

        args.putInt("nbMask", 1);
        args.putSerializable("mask", mask);
        args.putInt("min",0);
        args.putInt("max",255);

        return super._compute(img, args);
    }
}
