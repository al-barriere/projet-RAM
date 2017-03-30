package fr.ram.imagetreatment.Util;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by Rémi on 28/02/2017.
 */

public class ColorUtil {
    public static final int MAX_VALUE_COLOR_RGB = 255;
    public static final int MAX_VALUE_COLOR_HSV = 360;


    public static int pixelToGrey(int red, int green, int blue) {
        red = (red * 3) / 10;
        green = (green * 59) / 100;
        blue = (blue * 11) / 100;
        return red + green + blue;
    }

    public static int shiftRgbColor(int color) {
        color = ((color > MAX_VALUE_COLOR_RGB) ? MAX_VALUE_COLOR_RGB : color);
        color = ((color < 0) ? 0 : color);
        return color;
    }

    public static int OverFlowColor(int pixel){
        if(pixel>255)
            pixel=255;
        else if(pixel<0)
            pixel=0;
        return pixel;
    }

    public static int[] changeInterval(int[] pixels) {
        int minRed = Color.red(pixels[0]);
        int minGreen = Color.green(pixels[0]);
        int minBlue = Color.blue(pixels[0]);

        int maxR = Color.red(pixels[0]);
        int maxG = Color.green(pixels[0]);
        int maxB = Color.blue(pixels[0]);

        for (int i = 1; i < pixels.length; i++) {
            minRed = (Color.red(pixels[i]) < minRed) ? Color.red(pixels[i]) : minRed;
            minGreen = (Color.green(pixels[i]) < minGreen) ? Color.green(pixels[i]) : minGreen;
            minBlue = (Color.blue(pixels[i]) < minBlue) ? Color.blue(pixels[i]) : minBlue;

            maxR = (Color.red(pixels[i]) > maxR) ? Color.red(pixels[i]) : maxR;
            maxG = (Color.green(pixels[i]) > maxG) ? Color.green(pixels[i]) : maxG;
            maxB = (Color.blue(pixels[i]) > maxB) ? Color.blue(pixels[i]) : maxB;


        }

        for (int i = 0; i < pixels.length; i++) {
            int o = pixels[i];
            int red = changeColorInterval(Color.red(o),minRed, maxR);
            int green = changeColorInterval(Color.green(o), -minGreen, maxG);
            int blue = changeColorInterval(Color.blue(o), -minBlue, maxB);

            pixels[i] = Color.rgb(red, green, blue);
        }
        return pixels;
    }

    public static int changeColorInterval(int value, int min, int max) {
        return ((value - min) * MAX_VALUE_COLOR_HSV) / (max - min);
    }
}
