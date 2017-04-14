package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.Convolution.GaussianBlur;
import fr.ram.imagetreatment.Treatments.Convolution.Sobel;
import fr.ram.imagetreatment.Treatments.InverseColor;
import fr.ram.imagetreatment.Treatments.Treatment;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 13/04/2017.
 */

public class test extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmpTmp = img.getImageBitmap();
        bmpTmp = bmpTmp.copy(Bitmap.Config.ARGB_8888, true);


        Bitmap bmp = img.getImageBitmap();
        Sobel d = new Sobel();
        d._compute(img, args);

        InverseColor inverseColor = new InverseColor();
        inverseColor._compute(img, args);

        int pixels_contours[] = new int[bmp.getWidth() * bmp.getHeight()];
        int pixels_image[] = new int[bmpTmp.getWidth() * bmpTmp.getHeight()];

        bmp.getPixels(pixels_contours, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        bmpTmp.getPixels(pixels_image, 0, bmpTmp.getWidth(), 0, 0, bmpTmp.getWidth(), bmpTmp.getHeight());

        for (int i = 0; i < pixels_contours.length; i++) {
            int pixelContour = pixels_contours[i];
            int pixelImage = pixels_image[i];

            int o = pixelContour;
            int b = Color.blue(o);
            int r = Color.red(o);
            int g = Color.green(o);

            if (r > 127 && g > 127 && b > 127) {//si le pixel est blanc, donc ne represente pas un contour on copie l'image de base
                r = Color.red(pixelImage);
                g = Color.green(pixelImage);
                b = Color.blue(pixelImage);
            } else {//sinon on dessine le contour
                r = (Color.red(pixelContour));
                g = (Color.green(pixelContour));
                b = (Color.blue(pixelContour));

            }
            pixels_image[i] = Color.rgb(r, g, b);
        }
        bmp.setPixels(pixels_image, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());


        return bmp;
    }
}