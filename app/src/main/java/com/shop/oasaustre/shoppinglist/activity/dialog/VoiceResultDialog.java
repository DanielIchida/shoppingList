package com.shop.oasaustre.shoppinglist.activity.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shop.oasaustre.shoppinglist.R;
import com.shop.oasaustre.shoppinglist.activity.task.NewCategoryTask;
import com.shop.oasaustre.shoppinglist.adapter.ListaAdapter;
import com.shop.oasaustre.shoppinglist.adapter.VoiceAdapter;
import com.shop.oasaustre.shoppinglist.db.entity.Lista;

import java.util.List;

/**
 * Created by oasaustre on 3/12/16.
 */

public class VoiceResultDialog extends Dialog {

    private List<String> words;
    private Activity activity;

    public VoiceResultDialog(Context context){
        super(context);
        activity = (Activity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_form);

        DividerItemDecoration did = new DividerItemDecoration(activity,DividerItemDecoration.VERTICAL);
        VoiceAdapter voiceAdapter = new VoiceAdapter(getContext(),getWords());
        voiceAdapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getWordSelect(view);
                dismiss();
            }
        });

        RecyclerView rv_listaVoice = (RecyclerView) this.findViewById(R.id.rv_voiceList);
        //rv_listaVoice.addItemDecoration(did);
        rv_listaVoice.setAdapter(voiceAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv_listaVoice.setLayoutManager(layoutManager);
    }

 /*   @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createListDialog();
    }

    private AlertDialog createListDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        DividerItemDecoration did = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        VoiceAdapter voiceAdapter = new VoiceAdapter(getActivity(),getWords());
        voiceAdapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                getWordSelect(view);
            }
        });

        RecyclerView rv_listaVoice = (RecyclerView) getActivity().findViewById(R.id.rv_voiceList);
        rv_listaVoice.addItemDecoration(did);
        rv_listaVoice.setAdapter(voiceAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rv_listaVoice.setLayoutManager(layoutManager);

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.voice_form, null);

        builder.setView(v);


        return builder.create();
    }*/

    private void getWordSelect(View view){
        int positionItemSelect = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
        VoiceAdapter adapter = (VoiceAdapter)((RecyclerView) view.getParent()).getAdapter();
        String words = adapter.getLista().get(positionItemSelect);

        AutoCompleteTextView text = (AutoCompleteTextView) activity.
                findViewById(R.id.txtBuscarArticulo);

        text.setText(words);
        text.showDropDown();
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
