package fr.ram.imagetreatment.Treatments.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.HistogramEqualization;
import fr.ram.imagetreatment.Treatments.ShadesOfGrey;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Laplacian extends Convolution {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        int maskSize = 3;
        double[][] mask = {{1, 1, 1}, {1, -8, 1}, {1, 1, 1}};
        ShadesOfGrey shadesOfGray = new ShadesOfGrey();
        shadesOfGray._compute(img, null);

        args = new Bundle();
        args.putInt("nbMask",1);
        args.putInt("mask_size", maskSize);
        args.putSerializable("mask", mask);
        args.putInt("min",-255*8);
        args.putInt("max",255*8);
        super._compute(img, args);

        HistogramEqualization he=new HistogramEqualization();
        return he._compute(img,null);



       // HistogramEqualization he=new HistogramEqualization();
       // return he._compute(img,null);

    }
}
