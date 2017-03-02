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
        View view = layout.inflate(R.layout.mask_value, null);
        builder.setView(view);

        Button bt_3_3 = (Button) view.findViewById(R.id.id_3_3);
        bt_3_3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callingActivity(3);
            }
        });
        Button bt_5_5 = (Button) view.findViewById(R.id.id_5_5);
        bt_5_5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callingActivity(5);
            }
        });
        Button bt_7_7 = (Button) view.findViewById(R.id.id_7_7);
        bt_7_7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callingActivity(7);
            }
        });
        Button bt_9_9 = (Button) view.findViewById(R.id.id_9_9);
        bt_9_9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                callingActivity(9);
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
