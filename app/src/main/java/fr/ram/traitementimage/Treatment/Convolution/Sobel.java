package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.traitementimage.Util.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Sobel extends Convolution {
    @Override
    public void calcul(CustomImageView img, Bundle b) {
        int maskSize = 3;
        double[][] mask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};

        b = new Bundle();
        b.putInt("mask_size", maskSize);
        b.putSerializable("mask", mask);

        super.calcul(img, b);
    }
}
