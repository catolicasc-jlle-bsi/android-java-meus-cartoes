package com.mycards.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycards.api.Upload;
import com.mycards.business.Flag;
import com.mycards003.app.R;

public class CadBandeiraActivity extends Activity {

    private Button btn;
    private Button btnDelete;
    private Flag flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_bandeira);

        btn = (Button)findViewById(R.id.btnCadastro);
        btnDelete = (Button)findViewById(R.id.btnDelete);

    }

    @Override
    protected void onStart() {
        super.onStart();

        flag = (Flag) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(flag.description);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });

        if (Parametros.getInstance().model == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else if (Parametros.getInstance().model.id == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                excluirESair();
            }
        });
    }

    private void excluirESair() {
        try {
            //TODO:Implementar rotina de exclusão

            Toast.makeText(this, "Bandeira excluída com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarESair() {
        try {
            EditText edNome = (EditText)this.findViewById(R.id.etNome);
            if (edNome.getText().toString().trim().equals("")) {
                edNome.requestFocus();
                throw new Exception("Informe o nome da bandeira");
            }

            flag.description = edNome.getText().toString();

            new Upload().execute(flag);

            Toast.makeText(this, "Bandeira salva com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }

}
