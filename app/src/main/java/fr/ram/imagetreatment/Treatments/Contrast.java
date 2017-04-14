package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Util.ColorUtil;

/**
 * Created by Maxime on 09/02/2017.
 */

public class Contrast extends Treatment {
    @Override
    public Bitmap _compute(CustomImageView img, Bundle args) {
        Bitmap bmp = img.getImageBitmap();
        int red,green,blue;
        int value= args.getInt("value");
        int size = bmp.getWidth()*bmp.getHeight();
        int pixels[] = new int[size];
        double factor =(259 * (value + 255)) ;
        Log.i("coucou",String.valueOf(factor));
        factor=factor/(255 * (259 - value)) ;
        Log.i("coucou",String.valueOf(factor));
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for(int i=0;i<size;i++){
            int o = pixels[i];
            double redF=factor*(Color.red(o)-128);
            Log.i("coucou",String.valueOf(redF));
            double greenF=factor*(Color.green(o)-128);
            double blueF=factor*(Color.blue(o)-128);
            red = (int)(redF+128);
            Log.i("coucou",String.valueOf(red));
            green = (int)(greenF+128);
            blue = (int)(blueF+128);
          //  Log.i("coucou",String.valueOf(factor)+" "+String.valueOf(greenF)+" "+String.valueOf(blueF));

            red = ColorUtil.overFlowColor(red);
            green =  ColorUtil.overFlowColor(green);
            blue =  ColorUtil.overFlowColor(blue);


            pixels[i] = Color.rgb(red,green,blue);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        return bmp;
    }
}
