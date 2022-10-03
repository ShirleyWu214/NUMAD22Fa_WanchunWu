package edu.northeastern.numad22fa_wanchunwu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class LinkCollectorDialogFragment extends DialogFragment {

    public interface LinkCollectorDialogListener {
        void onDialogPositiveClick(DialogFragment linkDialog);
        void onDialogNegativeClick(DialogFragment linkDialog);
    }
    LinkCollectorDialogListener listener;

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_link_collector_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.add_link, (dialog, id) -> listener.onDialogPositiveClick(LinkCollectorDialogFragment.this))
                .setNegativeButton(R.string.cancel, (dialog, id) -> listener.onDialogNegativeClick(LinkCollectorDialogFragment.this));
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (LinkCollectorDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Activity must implement LinkCollectorDialogListener");
        }
    }
}