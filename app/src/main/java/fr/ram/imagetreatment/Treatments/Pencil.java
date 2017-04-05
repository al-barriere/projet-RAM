package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;

/**
 * Created by Maxime on 18/03/2017.
 */

public class Pencil extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();

        Sobel sobel=new Sobel();
        sobel._compute(img, args);

        InverseColor inverseColor=new InverseColor();
        inverseColor._compute(img,args);

        args=new Bundle();
        args.putInt("maskSize", 3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur._compute(img, args);

        return bmp;
    }
}
