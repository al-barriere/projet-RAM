package fr.ram.traitementimage.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.SeekBar;

import fr.ram.traitementimage.Activities.ImageTreatmentActivity;
import fr.ram.traitementimage.R;

/**
 * Created by AntoineB on 17-02-10.
 */

public class SeekbarHueColorDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layout = getActivity().getLayoutInflater();
        builder.setTitle(R.string.question_hue);
        builder.setView(layout.inflate(R.layout.seekbar_hue,null));



        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                SeekBar contrast = (SeekBar) ((AlertDialog) dialog).findViewById(R.id.choix);
                ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();
                if(callingActivity.getOption() == 0){
                    callingActivity.hueChoice(contrast.getProgress());
                }else if(callingActivity.getOption() == 1){
                    callingActivity.filterColor(contrast.getProgress());
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
