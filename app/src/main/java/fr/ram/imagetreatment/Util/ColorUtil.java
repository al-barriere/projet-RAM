package fr.ram.imagetreatment.Util;

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

    public static int changeColorInterval(int value, int min, int max){
        return ((value-min) * MAX_VALUE_COLOR_RGB)/(max-min);
    }
}
