package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.traitementimage.Treatment.ShadesOfGray;
import fr.ram.traitementimage.Treatment.Treatment;
import fr.ram.traitementimage.Util.CustomImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Laplacien extends Convolution {
    @Override
    public void calcul(CustomImageView img, Bundle b) {
        /*ShadesOfGray sof  = new ShadesOfGray();
        sof.calcul(img, null);*/

        int maskSize = 3;
        double[][] mask = {{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};

        b = new Bundle();
        b.putInt("mask_size", maskSize);
        b.putSerializable("mask", mask);

        super.calcul(img, b);
    }
}
