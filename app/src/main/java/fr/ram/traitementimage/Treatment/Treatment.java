package fr.ram.traitementimage.Treatment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import fr.ram.traitementimage.Util.CustomImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public abstract class Treatment {
    public void calcul(CustomImageView img, Bundle args) {
        img.setImageModified(true);
    }
}
