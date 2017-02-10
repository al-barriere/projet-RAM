package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public interface Treatment {

    void calcul(Bitmap bmp, ImageView img, Bundle b);
}
