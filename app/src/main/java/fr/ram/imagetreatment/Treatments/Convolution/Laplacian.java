package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Laplacian extends Convolution {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        Bitmap returnBitmap;

        int maskSize = 3;
        double[][] mask = {{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        returnBitmap = shadesOfGray._compute(bmp, null);

        args = new Bundle();
        args.putInt("nbMask", 1);
        args.putInt("maskSize", maskSize);
        args.putSerializable("mask", mask);
        args.putInt("min", -255 * 8);
        args.putInt("max", 255 * 8);
        returnBitmap = super._compute(returnBitmap, args);

        HistogramEqualization he = new HistogramEqualization();
        returnBitmap = he._compute(returnBitmap, null);

        return returnBitmap;

        // HistogramEqualization he=new HistogramEqualization();
        // return he._compute(img,null);

    }
}
