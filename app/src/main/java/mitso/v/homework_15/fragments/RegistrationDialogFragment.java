package mitso.v.homework_15.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import mitso.v.homework_15.R;
import mitso.v.homework_15.constants.Constants;

public class RegistrationDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String message = getArguments().getString(Constants.KEY_DIALOG_MESSAGE, "");

        return new AlertDialog
                .Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(R.string.s_dp_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }
}