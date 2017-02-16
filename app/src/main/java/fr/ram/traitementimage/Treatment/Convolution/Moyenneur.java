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
    public void calcul(Bitmap bmp, ImageView img, Bundle b)
    {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        int[] pixelMoyenneur = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        for(int i = 0; i < pixelMoyenneur.length; i++)
        {
            int red= Color.red(pixelMoyenneur[i]);
            int blue= Color.blue(pixelMoyenneur[i]);
            int green= Color.green(pixelMoyenneur[i]);
            if(i <= width || i >= width * (height-1) || i % width ==0 || i % width == width-1)
            {
                red = Color.red(pixels[i]);
                green = Color.green(pixels[i]);
                blue = Color.blue(pixels[i]);
            }
            else {
                red = (Color.red(pixels[i - width - 1])
                        + Color.red(pixels[i - width])
                        + Color.red(pixels[i - width + 1])
                        + Color.red(pixels[i - 1])
                        + Color.red(pixels[i])
                        + Color.red(pixels[i + 1])
                        + Color.red(pixels[i + width - 1])
                        + Color.red(pixels[i + width])
                        + Color.red(pixels[i + width + 1])) / 9;
                green = (Color.green(pixels[i - width - 1])
                        + Color.green(pixels[i - width])
                        + Color.green(pixels[i - width + 1])
                        + Color.green(pixels[i - 1])
                        + Color.green(pixels[i])
                        + Color.green(pixels[i + 1])
                        + Color.green(pixels[i + width - 1])
                        + Color.green(pixels[i + width])
                        + Color.green(pixels[i + width + 1])) / 9;
                blue = (Color.blue(pixels[i - width - 1])
                        + Color.blue(pixels[i - width])
                        + Color.blue(pixels[i - width + 1])
                        + Color.blue(pixels[i - 1])
                        + Color.blue(pixels[i])
                        + Color.blue(pixels[i + 1])
                        + Color.blue(pixels[i + width - 1])
                        + Color.blue(pixels[i + width])
                        + Color.blue(pixels[i + width + 1])) / 9;
            }
            pixelMoyenneur[i] = Color.rgb(red, green, blue);

        }

        bmp.setPixels(pixelMoyenneur, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        img.setImageBitmap(bmp);

    }

}
