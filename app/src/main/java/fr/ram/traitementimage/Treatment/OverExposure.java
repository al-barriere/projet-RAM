package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;


/**
 * Created by AntoineB on 17-02-05.
 *
 */

public class OverExposure extends Treatment {

    /*
   Apply the effect "OverExposure"
    */
    public static void applyFilter(Bitmap img, int value){
        int red,green,blue;
        int size = img.getWidth()*img.getHeight();
        int pixels[] = new int[size];
        int new_pixels[] = new int[size];

        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        for(int i=0;i<size;i++){
            red = Color.red(pixels[i]) + value;
            green = Color.green(pixels[i])+value;
            blue = Color.blue(pixels[i])+value;

            if(red>255) {red=255;}
            else if(green>255) {green=255;}
            else if(blue>255) {blue=255;}
            new_pixels[i] = Color.rgb(red,green,blue);
        }
        img.setPixels(new_pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
    }
}
