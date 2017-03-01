package fr.ram.traitementimage.Util;

/**
 * Created by Rémi on 28/02/2017.
 */

public class ColorUtil {
    public static int pixelToGrey(int red, int green, int blue) {
        red = (red * 3) / 10;
        green = (green * 59) / 100;
        blue = (blue * 11) / 100;
        return red + green + blue;
    }

}
