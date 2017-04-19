package fr.ram.imagetreatment.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.Objects;

import fr.ram.imagetreatment.Activities.ImageTreatmentActivity;
import fr.ram.imagetreatment.R;

/**
 * Created by Maxime on 22/02/2017.
 */

public class SizeMaskDialogFragment extends DialogFragment {
    /***
     * Return a Dialog in order to select a value between min and max (theses values are set in R.layout.seekbar_value)
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.sizeMask);

        // Create an item list with the array in R.array.mask_sizes_array
        builder.setItems(R.array.mask_sizes_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Applies treatment depending on the selected button
                switch (which) {
                    // 3 * 3
                    case 0:
                        callingActivity(3);
                        break;
                    // 5 * 5
                    case 1:
                        callingActivity(5);
                        break;
                    // 7 * 7
                    case 2:
                        callingActivity(7);
                        break;
                    // 9 * 9
                    case 3:
                        callingActivity(9);
                        break;
                }
            }
        });

        return builder.create();
    }

    /***
     * Ask the ImageTreatmentActivity to apply a treatment of a given size
     * @param size The size of the mask
     */
    private void callingActivity(int size) {
        // We retrieve the filter type
        String filter = getArguments().getString("filter");

        // We retrieve the ImageTreatmentActivity
        ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();

        // Apply the treatment depending on the requested filter
        if (Objects.equals(filter, "averageBlur"))
            callingActivity.averageBlurTreatment(size);
        else if (Objects.equals(filter, "gaussianBlur"))
            callingActivity.gaussianFilterTreatment(size);

        // Close the Dialog
        dismiss();
    }
}
