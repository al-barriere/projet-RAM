package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Remi on 10/02/2017.
 */

public class Gaussien extends Convolution {
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int maskSize = 5;
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

        b.putInt("mask_size", maskSize);
        b.putSerializable("mask", mask);

        super.calcul(bmp, img, b);
    }
}
