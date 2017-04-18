package fr.ram.imagetreatment.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.SeekBar;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.CustomViews.MinMaxSeekBar;
import fr.ram.imagetreatment.R;

/**
 * Created by AntoineB on 17-02-11.
 */

public class SeekbarValueDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layout = getActivity().getLayoutInflater();
        builder.setTitle(R.string.question_value);
        builder.setView(layout.inflate(R.layout.seekbar_value, null));

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                MinMaxSeekBar contrast = (MinMaxSeekBar) ((AlertDialog) dialog).findViewById(R.id.seekBar);
                ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();

                if (getTag().equals("contrast")) {
                    callingActivity.contrastTreatment(contrast.getValue());
                } else {
                    callingActivity.overExposureTreatment(contrast.getValue());
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
