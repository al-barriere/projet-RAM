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
        int value=7;
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        int[] pixelMoyenneur = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int red, blue, green, x_pixelMatrix, y_pixelMatrix;
        for(int i = 0; i < pixelMoyenneur.length; i++)
        {
            red=blue=green=0;
            x_pixelMatrix=i%width;
            y_pixelMatrix=i/width;

            if(i <= width*(value/2) || i >= width * (height-(value/2)) || i % width < value/2  || i % width >= width-(value/2))
            {
                red = Color.red(pixels[i]);
                green = Color.green(pixels[i]);
                blue = Color.blue(pixels[i]);
            }
            else {
                for (int x = x_pixelMatrix -(value / 2); x <= x_pixelMatrix +(value / 2); x++) {
                    for (int y = y_pixelMatrix - (value / 2); y <= y_pixelMatrix + (value / 2); y++) {
                        red+=Color.red(pixels[x+y*width]);
                        green+=Color.green(pixels[x+y*width]);
                        blue+=Color.blue(pixels[x+y*width]);
                    }
                }
            }

            red/=(value*value);
            green/=(value*value);
            blue/=(value*value);
            pixelMoyenneur[i] = Color.rgb(red, green, blue);
        }

        bmp.setPixels(pixelMoyenneur, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
    public void test(Bitmap bmp, ImageView img, Bundle b,double[][] filtre,int sizefiltre)
    {
        int value=sizefiltre;
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        int[] pixelMoyenneur = new int[bmp.getWidth() * bmp.getHeight()];
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int red, blue, green, x_pixelMatrix, y_pixelMatrix;
        float redF,blueF,greenF;
        for(int i = 0; i < pixelMoyenneur.length; i++)
        {
            redF=blueF=greenF=0;
            x_pixelMatrix=i%width;
            y_pixelMatrix=i/width;

            if(i <= width*(value/2) || i >= width * (height-(value/2)) || i % width < value/2  || i % width >= width-(value/2))
            {
                redF = Color.red(pixels[i]);
                greenF = Color.green(pixels[i]);
                blueF = Color.blue(pixels[i]);
            }
            else {
                int cpti=0;
                int cptj=0;
                for (int x = x_pixelMatrix -(value / 2); x <= x_pixelMatrix +(value / 2); x++) {
                    for (int y = y_pixelMatrix - (value / 2); y <= y_pixelMatrix + (value / 2); y++) {
                        double what=filtre[cpti][cptj];
                        redF+=Color.red(pixels[x+y*width])*filtre[cpti][cptj];
                        greenF+=Color.green(pixels[x+y*width])*filtre[cpti][cptj];
                        blueF+=Color.blue(pixels[x+y*width])*filtre[cpti][cptj];

                        cptj++;
                    }
                    cpti++;
                    cptj=0;
                }
            }

            red=(int)redF;
            green=(int)greenF;
            blue=(int)blueF;
            pixelMoyenneur[i] = Color.rgb(red, green, blue);
        }

        bmp.setPixels(pixelMoyenneur, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }

}
