package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

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
        float factor =(259 * (value + 255)) / (255 * (259 - value)) ;
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        for(int i=0;i<size;i++){
            int o = pixels[i];
            red = (int)(factor*(Color.red(o)-128)+128);
            green = (int)(factor*(Color.green(o)-128)+128);
            blue = (int)(factor*(Color.blue(o)-128)+128);


            red = ColorUtil.OverFlowColor(red);
            green =  ColorUtil.OverFlowColor(green);
            blue =  ColorUtil.OverFlowColor(blue);


            pixels[i] = Color.rgb(red,green,blue);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        return bmp;
    }
}
