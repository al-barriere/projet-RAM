package fr.ram.imagetreatment.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.SeekBar;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.R;

/**
 * Created by AntoineB on 17-02-10.
 */

abstract class SeekbarHueColorDialogFragment extends DialogFragment {
    /***
     * Return an AlertDialog.Builder for an AlertDialog to select a color
     * @return The AlertDialog.Builder
     */
    protected AlertDialog.Builder getBuilder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the view
        LayoutInflater layout = getActivity().getLayoutInflater();
        builder.setView(layout.inflate(R.layout.seekbar_hue, null));

        builder.setTitle(R.string.question_hue);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }

    /***
     * Return an AlertDialog in order to select a color
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = getBuilder();
        return builder.create();
    }
}
