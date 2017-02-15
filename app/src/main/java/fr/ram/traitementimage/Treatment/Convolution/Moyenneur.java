package fr.ram.traitementimage.Treatment.Convolution;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Maxime on 10/02/2017.
 */

public class Moyenneur extends Convolution {
    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b)
    {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        int[] pixelMoyenneur = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        for(int i = 0; i < pixelMoyenneur.length; i++)
        {
            if(i <= width || i >= width * (height-1) || i % width ==0 || i % width == width-1)
            {
                pixelMoyenneur[i] = pixels[i];
            }
            else {
                pixelMoyenneur[i] = (pixels[i - width - 1]
                        + pixels[i - width]
                        + pixels[i - width + 1]
                        + pixels[i - 1]
                        + pixels[i]
                        + pixels[i + 1]
                        + pixels[i + width - 1]
                        + pixels[i + width]
                        + pixels[i + width + 1]) / 9;
            }

        }
        bmp.setPixels(pixelMoyenneur, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        img.setImageBitmap(bmp);

    }

}
