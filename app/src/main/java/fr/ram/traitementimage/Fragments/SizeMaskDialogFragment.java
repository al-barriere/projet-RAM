package fr.ram.traitementimage.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.Objects;

import fr.ram.traitementimage.Activities.ImageTreatmentActivity;
import fr.ram.traitementimage.R;

/**
 * Created by Maxime on 22/02/2017.
 */

public class SizeMaskDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layout = getActivity().getLayoutInflater();
        builder.setTitle(R.string.sizeMask);

        builder.setItems(R.array.mask_sizes_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        callingActivity(3);
                        break;
                    case 1:
                        callingActivity(5);
                        break;
                    case 2:
                        callingActivity(7);
                        break;
                    case 3:
                        callingActivity(9);
                        break;
                }
            }
        });

        return builder.create();
    }

    private void callingActivity(int size) {
        String filter = getArguments().getString("filter");
        ImageTreatmentActivity callingActivity = (ImageTreatmentActivity) getActivity();

        if (Objects.equals(filter, "averageBlur"))
            callingActivity.averageBlurTreatment(size);
        else if (Objects.equals(filter, "gaussianBlur"))
            callingActivity.gaussianFilterTreatment(size);

        dismiss();
    }
}
