package fr.ram.imagetreatment.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.ImageFile;

public class ImageTreatmentActivityBackButtonDialogFragment extends DialogFragment {
    /***
     * Create an AlertDialog in order to ask the user if he wants to save the CustomImageView's Bitmap before leaving the current activity
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Retrieve the Bitmap contained by the CustomImageView
        final Bitmap image = (Bitmap) getArguments().getParcelable("imageBitmap");

        builder.setTitle(R.string.main_activity_back_button_title);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // If there wasn't any error, we save the image and finish the current activity
                if (ImageFile.saveImage(getActivity(), image))
                    getActivity().finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Finish the current Activity (ImageTreatmentActivity) and go back to the StartActivity
                getActivity().finish();
            }
        });
        builder.setNeutralButton(R.string.cancel, null);

        return builder.create();
    }
}
