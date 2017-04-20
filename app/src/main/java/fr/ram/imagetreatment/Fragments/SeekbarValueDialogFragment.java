package fr.ram.imagetreatment.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.CustomViews.MinMaxSeekBar;
import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.FragmentTags;

/**
 * Created by AntoineB on 17-02-11.
 */

public class SeekbarValueDialogFragment extends DialogFragment {
    /***
     * Return a Dialog in order to select a value between min and max (theses values are set in R.layout.seekbar_value)
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the view
        LayoutInflater layout = getActivity().getLayoutInflater();
        builder.setView(layout.inflate(R.layout.seekbar_value, null));

        builder.setTitle(R.string.question_value);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                MinMaxSeekBar seekbar = (MinMaxSeekBar) ((AlertDialog) dialog).findViewById(R.id.seekBar);
                ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();

                // If the Dialog is called by a contrast button, we modify the contrast of the image
                if (getTag().equals(FragmentTags.CONTRAST)) {
                    callingActivity.contrastTreatment(seekbar.getValue());
                }
                // Else, if it is an exposure treatment
                else if (getTag().equals(FragmentTags.EXPOSURE)) {
                    callingActivity.exposureTreatment(seekbar.getValue());
                }

                // We close the Dialog
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // We close the Dialog
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
