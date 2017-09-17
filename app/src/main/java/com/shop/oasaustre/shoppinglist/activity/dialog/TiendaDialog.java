package com.shop.oasaustre.shoppinglist.activity.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.ITask;
import com.shop.oasaustre.shoppinglist.activity.task.NewTiendaTask;
import com.shop.oasaustre.shoppinglist.activity.task.TaskFactory;
import com.shop.oasaustre.shoppinglist.app.App;

/**
 * Created by oasaustre on 3/12/16.
 */

public class TiendaDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createListDialog();
    }

    private AlertDialog createListDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.new_tienda_form, null);

        builder.setView(v);



        TextView btnNewSave = (TextView) v.findViewById(R.id.btnNewTiendaSave);
        TextView btnNewCancel = (TextView) v.findViewById(R.id.btnNewTiendaCancel);
        final EditText fieldNewTienda = (EditText) v.findViewById(R.id.fieldNewTienda);

        btnNewSave.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        createNewTienda(fieldNewTienda);
                    }
                });

        btnNewCancel.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });


        return builder.create();
    }


    private void createNewTienda(EditText field){

        if(field.getText().length() > 0){
            ITask task = TaskFactory.getInstance().createNewTiendaTask(this,(App) this.getActivity().getApplication());
            task.run(field.getText().toString());
        }else{
            Toast.makeText(getActivity(),"El campo de texto está vacío",Toast.LENGTH_LONG);
        }
    }
}
