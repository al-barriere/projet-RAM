package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;


/**
 * Created by AntoineB on 17-02-05.
 *
 */

public class OverExposure extends Treatment {
    @Override
    public void compute(CustomImageView img, Bundle args) {
        super.compute(img, args);

        Bitmap bmp = img.getImageBitmap();
        int red,green,blue;
        int value= args.getInt("value");
        int size = bmp.getWidth()*bmp.getHeight();
        int pixels[] = new int[size];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0;i<size;i++){
            int o = pixels[i];
            red = Color.red(o) + (value-255);
            green = Color.green(o) +(value-255);
            blue = Color.blue(o) + (value-255);

            if(value>255){
                if (red > 255) {
                    red = 255;
                }if (green > 255) {
                    green = 255;
                }if (blue > 255) {
                    blue = 255;
                }
            }else{
                if (red <0) {
                    red = 0;
                } if (green <0) {
                    green = 0;
                } if (blue <0) {
                    blue = 0;
                }
            }

            pixels[i] = Color.rgb(red,green,blue);
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }
}
