package fr.ram.traitementimage.Treatments;

import android.os.Bundle;

import fr.ram.traitementimage.CustomViews.CustomImageView;

/**
 * Created by Maxime on 03/02/2017.
 */
public abstract class Treatment {
    public void compute(CustomImageView img, Bundle args) {
        img.setImageModified(true);
    }
}
