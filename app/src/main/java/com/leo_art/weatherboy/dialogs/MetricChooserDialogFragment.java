package com.leo_art.weatherboy.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.leo_art.weatherboy.R;

/**
 * Created by bogdan on 4/26/15.
 */
public class MetricChooserDialogFragment extends DialogFragment {

    private OnMetricDialogChooseListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnMetricDialogChooseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMetricDialogChooseListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pick_metric)
                .setItems(R.array.metrics_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        dialog.dismiss();
                        listener.onMetricDialogItemChosen(which);
                    }
                });
        return builder.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnMetricDialogChooseListener{
        void onMetricDialogItemChosen(int which);
    }
}
