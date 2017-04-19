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
    /***
     * This method contains the logic of the Treatment
     * @param bmp The Bitmap to modify
     * @param args The arguments of the Treatment
     * @return The modified Bitmap
     */
    public abstract Bitmap _compute(Bitmap bmp, Bundle args);

    /***
     * This method is called by the ImageTreatmentActivity in order to start a Treatment
     * @param activity The calling activity
     * @param img The CustomImageView containing the Bitmap to modify
     * @param args The arguments of the Treatment
     */
    public void compute(Activity activity, CustomImageView img, Bundle args) {
        // Start a ProgressDialog in order to avoid the feeling of a blocked UI
        ProgressDialog effectProgressDialog = ProgressDialog.show(activity, activity.getString(R.string.please_wait), activity.getString(R.string.processing_image), true);
        effectProgressDialog.setCancelable(false);

        // Create and execute the treatment in another thread
        TreatmentAsyncTask treatmentRunnable = new TreatmentAsyncTask();
        treatmentRunnable.execute(img.getImageBitmap(), args, this, activity, effectProgressDialog);
    }
}
