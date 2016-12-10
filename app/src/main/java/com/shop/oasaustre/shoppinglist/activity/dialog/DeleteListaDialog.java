package com.shop.oasaustre.shoppinglist.activity.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.DelCategoriaTask;
import com.shop.oasaustre.shoppinglist.activity.task.DelListaTask;

/**
 * Created by oasaustre on 7/12/16.
 */

public class DeleteListaDialog extends DialogFragment {

    private int delPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("¿Está seguro que desea eliminar esta lista y los artículos?")
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        executeRemoveCategoria();
                    }

                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        return builder.create();
    }

    public int getDelPosition() {
        return delPosition;
    }

    public void setDelPosition(int delPosition) {
        this.delPosition = delPosition;
    }

    private void executeRemoveCategoria(){
        DelListaTask task = new DelListaTask(getActivity(),this,delPosition);
        task.execute();
    }
}

