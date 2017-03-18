package fr.ram.imagetreatment.Runnables;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.CustomViews.CustomImageView;
import fr.ram.imagetreatment.Treatments.Treatment;

/**
 * Created by Rémi on 17/03/2017.
 */

public class TreatmentAsyncTask extends AsyncTask<Object, Integer, Bitmap> {
    private CustomImageView img;
    private Bundle args;
    private Treatment treatment;
    ProgressDialog progressDialog;
    public ImageTreatmentActivity delegate = null;

    @Override
    protected Bitmap doInBackground(Object... params) {
        this.img = (CustomImageView) params[0];
        this.args = (Bundle) params[1];
        this.treatment = (Treatment) params[2];
        this.progressDialog = (ProgressDialog) params[3];

        Bitmap result = treatment._compute(img, args);
        return result;
    }

    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result);
        progressDialog.dismiss();
    }
}
