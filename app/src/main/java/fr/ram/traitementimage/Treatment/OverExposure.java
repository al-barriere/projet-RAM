package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;


/**
 * Created by AntoineB on 17-02-05.
 *
 */

public class OverExposure implements Treatment {

    @Override
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int red,green,blue;
        //////
        int value=b.getInt("value");
        //////
        int size = bmp.getWidth()*bmp.getHeight();
        int pixels[] = new int[size];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0;i<size;i++){
            int o = pixels[i];
            red = Color.red(o) + value;
            green = Color.green(o) + value;
            blue = Color.blue(o) + value;

            if(red>255) {red=255;}
            if(green>255) {green=255;}
            if(blue>255) {blue=255;}
            pixels[i] = Color.rgb(red,green,blue);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);

    }
}
