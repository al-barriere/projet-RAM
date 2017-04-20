package fr.ram.imagetreatment.Runnables;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.Treatments.Treatment;

/**
 * Created by Rémi on 17/03/2017.
 */

public class TreatmentAsyncTask extends AsyncTask<Object, Integer, Bitmap> {
    private Bitmap bmp;
    private Bundle args;
    private Treatment treatment;
    private ProgressDialog progressDialog;
    private ImageTreatmentActivity delegate;

    /***
     * Compute the treatment on the Bitmap
     * @param params Arguments for the process including the CustomImageView, the Treatment arguments,
     *               the Treatment class, the calling activity and the effect ProgressDialog
     * @return The modified bitmap
     */
    @Override
    protected Bitmap doInBackground(Object... params) {
        this.bmp = (Bitmap) params[0];
        this.args = (Bundle) params[1];
        this.treatment = (Treatment) params[2];
        this.delegate = (ImageTreatmentActivity) params[3];
        this.progressDialog = (ProgressDialog) params[4];

        // Compute the treatment on the CustomImageView
        return treatment.render(bmp, args);
    }

    /***
     * Inform the calling activity that the execution is finished
     * @param result The modified bitmap
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result, progressDialog);
    }
}
