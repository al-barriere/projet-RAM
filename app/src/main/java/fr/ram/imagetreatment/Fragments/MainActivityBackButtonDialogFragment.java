package fr.ram.imagetreatment.Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import fr.ram.imagetreatment.R;
import fr.ram.imagetreatment.Util.ImageFile;

public class MainActivityBackButtonDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bitmap image = (Bitmap) getArguments().getParcelable("imageBitmap");

        builder.setTitle(R.string.main_activity_back_button_title);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (ImageFile.saveImage(getActivity(), image))
                    getActivity().finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().finish();
            }
        });
        builder.setNeutralButton(R.string.cancel, null);
        return builder.create();
    }
}
