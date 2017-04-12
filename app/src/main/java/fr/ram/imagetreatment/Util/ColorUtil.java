package fr.ram.imagetreatment.Util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.StringBuilderPrinter;

/**
 * Created by Rémi on 28/02/2017.
 */

public class ColorUtil {
    public static final int MIN_VALUE_COLOR = 0;
    public static final int MAX_VALUE_COLOR_RGB = 255;
    public static final int MAX_VALUE_COLOR_HSV = 360;

    /***
     * Return the value of the grey pixel created with the colors passed in parameters
     * @param red The red value
     * @param green The green value
     * @param blue The blue value
     * @return The new RGB value to create grey
     */
    public static int pixelToGrey(int red, int green, int blue) {
        red = (red * 3) / 10;
        green = (green * 59) / 100;
        blue = (blue * 11) / 100;
        return red + green + blue;
    }

    /***
     * If the color value if over MAX_VALUE_COLOR_RGB, set it to MAX_VALUE_COLOR_RGB
     * If it is under MIN_VALUE_COLOR, set it to MIN_VALUE_COLOR
     * @param pixel The color value
     * @return The new color value
     */
    public static int overFlowColor(int pixel) {
        if (pixel > MAX_VALUE_COLOR_RGB)
            pixel = MAX_VALUE_COLOR_RGB;
        else if (pixel < MIN_VALUE_COLOR)
            pixel = MIN_VALUE_COLOR;
        return pixel;
    }

    /***
     * Rescale the value between the RGB interval
     * @param value The pixel color value
     * @param min The former minimum value of the value interval
     * @param max The former maximum value of the value interval
     * @return The new pixel color value
     */
    public static int changeColorInterval(int value, int min, int max) {
        return ((value - min) * MAX_VALUE_COLOR_RGB) / (max - min);
    }

}
