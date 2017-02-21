package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Moyenneur extends Convolution {
    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int maskSize = 5;

        double[][] mask = new double[maskSize][maskSize];
        for (int i = 0; i < maskSize; i++)
            for (int j = 0; j < maskSize; j++)
                mask[i][j] = 1.0/(maskSize*maskSize);

        b.putInt("mask_size", maskSize);
        b.putSerializable("mask", mask);

        super.calcul(bmp, img, b);
    }
}
