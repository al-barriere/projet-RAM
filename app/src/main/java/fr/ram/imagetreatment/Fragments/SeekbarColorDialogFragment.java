package fr.ram.imagetreatment.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.SeekBar;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.R;

/**
 * Created by AntoineB on 17-02-10.
 */

public class SeekbarColorDialogFragment extends SeekbarHueColorDialogFragment {
    /***
     * Return a Dialog in order to select a color
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = getBuilder();

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Retrieve the SeekBar of the AlertDialog and the parent Activity (ImageTreatmentActivity)
                SeekBar contrast = (SeekBar) ((AlertDialog) dialog).findViewById(R.id.choice);
                ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();

                // Ask the ImageTreatmentActivity to call the treatment of ColorFilter
                callingActivity.filterColor(contrast.getProgress());

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
