package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public class Sepia implements Treatment {
    public void calcul(Bitmap bmp, ImageView img, Bundle b) {
        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int blue = Color.blue(o);
            int red = Color.red(o);
            int green = Color.green(o);

            int tmp_red=(red*393/1000)+(green*769/1000)+(blue*189/1000);
            int tmp_blue=(red*272/1000)+(green*534/1000)+(blue*131/1000);
            int tmp_green=(red*349/1000)+(green*686/1000)+(blue*168/1000);
            if(tmp_red>255)
                tmp_red=255;
            if(tmp_green>255)
                tmp_green=255;
            if(tmp_blue>255)
                tmp_blue=255;

            pixels[i] = Color.rgb(tmp_red, tmp_green, tmp_blue);


        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());

        img.setImageBitmap(bmp);
    }
}
