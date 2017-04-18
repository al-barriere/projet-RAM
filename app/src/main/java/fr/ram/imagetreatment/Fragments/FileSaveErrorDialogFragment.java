package fr.ram.imagetreatment.Fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import fr.ram.imagetreatment.R;

/**
 * Created by Rémi on 05/04/2017.
 */

public class FileSaveErrorDialogFragment extends DialogFragment {
    /***
     * Create an AlertDialog in order to inform the user of an error happening during the creation of a file
     * @param savedInstanceState The Dialog savedInstanceState
     * @return The Dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.imagefile_save_ioexception_title);
        builder.setMessage(R.string.imagefile_save_ioexception_content);
        builder.setPositiveButton(R.string.ok, null);

        return builder.create();
    }
}
