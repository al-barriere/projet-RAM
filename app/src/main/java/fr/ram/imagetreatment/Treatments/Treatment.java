package fr.ram.imagetreatment.Treatments;

import android.os.Bundle;

import fr.ram.imagetreatment.CustomViews.CustomImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public abstract class Treatment {
    public void compute(CustomImageView img, Bundle args) {
        img.setImageModified(true);
    }
}
