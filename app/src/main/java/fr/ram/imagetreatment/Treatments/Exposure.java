package fr.ram.imagetreatment.Treatments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import fr.ram.imagetreatment.Util.BundleArgs;
import fr.ram.imagetreatment.Util.ColorUtil;
import fr.ram.imagetreatment.Util.ImageFile;


/**
 * Created by AntoineB on 17-02-05.
 *
 */

public class Exposure extends Treatment {
    @Override
    public Bitmap _compute(Bitmap bmp, Bundle args) {
        int red,green,blue;
        int value= args.getInt(BundleArgs.VALUE);
        int size = bmp.getWidth()*bmp.getHeight();
        int pixels[] = new int[size];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0;i<size;i++){
            int o = pixels[i];
            red = Color.red(o) + (value);
            green = Color.green(o) +(value);
            blue = Color.blue(o) + (value);


            red = ColorUtil.overFlowColor(red);
            green =  ColorUtil.overFlowColor(green);
            blue =  ColorUtil.overFlowColor(blue);


            pixels[i] = Color.rgb(red,green,blue);
        }

        return ImageFile.createBitmapFromPixels(pixels, bmp.getWidth(), bmp.getHeight());
    }
}
