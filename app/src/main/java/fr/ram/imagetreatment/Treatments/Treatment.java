package fr.ram.imagetreatment.Treatments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Runnables.TreatmentAsyncTask;

/**
 * Created by Maxime on 03/02/2017.
 */
public abstract class Treatment {
    public abstract Bitmap _compute(Bitmap bmp, Bundle args);

    public void compute(Activity activity, CustomImageView img, Bundle args) {
        ProgressDialog effectProgressDialog = ProgressDialog.show(activity, activity.getString(R.string.please_wait), activity.getString(R.string.processing_image), true);
        effectProgressDialog.setCancelable(false);

        TreatmentAsyncTask treatmentRunnable = new TreatmentAsyncTask();
        treatmentRunnable.execute(img.getImageBitmap(), args, this, activity, effectProgressDialog);
    }
}
