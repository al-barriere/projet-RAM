package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import static android.graphics.Color.RGBToHSV;

/**
 * Created by AntoineB on 17-02-05.
 * You choose a color. It will be display and the others one will be change to the gray color
 */

public class ColorFilter implements Treatment {

    /*
    Apply the effect "ColorFilter"
     */
    public static void applyFilter(Bitmap bmp, ImageView img,int min, int color){
        int red,blue,green,rgb;
        int size = bmp.getWidth()*bmp.getHeight();
        int pixels[] = new int[size];
        float hsv[] = new float[3];

        bmp.getPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        for(int i=0;i<size;i++){
            rgb = pixels[i];

            red = Color.red(rgb);
            green = Color.green(rgb);
            blue = Color.blue(rgb);
            RGBToHSV(red, green, blue, hsv);
            if(hsv[0]<(float)color-min || hsv[0]>(float)color+min ){
                rgb = pixelToGrey(red,green,blue);
                rgb = Color.rgb(rgb,rgb,rgb);
            }else{
                rgb = Color.rgb(red,green,blue);
            }
            pixels[i] = rgb;
        }
        bmp.setPixels(pixels, 0, bmp.getWidth(), 0, 0, bmp.getWidth(), bmp.getHeight());
        img.setImageBitmap(bmp);
    }

    /*
    Change the color of all the pixel which are inferior or superior to the value
     */
    private static int pixelToGrey(int red, int green, int blue) {
        red = (red * 3) / 10;
        green = (green * 59) / 100;
        blue = (blue * 11) / 100;
        return red + green + blue;
    }

    @Override
    public void calcul(Bitmap bmp, ImageView img) {

    }
}
