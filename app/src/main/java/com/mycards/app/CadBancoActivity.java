package com.mycards.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mycards.api.Delete;
import com.mycards.api.Upload;
import com.mycards.business.Bank;
import com.mycards003.app.R;

public class CadBancoActivity extends Activity {

    private Button btn;
    private Button btnDelete;
    private Bank bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_banco);

        btn = (Button)findViewById(R.id.btnCadastro);
        btnDelete = (Button)findViewById(R.id.btnDelete);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bank = (Bank) Parametros.getInstance().model;

        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(bank.description);

        EditText edFenaban = (EditText)this.findViewById(R.id.etFenaban);
        edFenaban.setText(bank.code);
        if (Parametros.getInstance().model == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else if (Parametros.getInstance().model.id == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                excluirESair();
            }
        });
    }

    private void excluirESair() {
        try {
            new Delete().execute(bank);

            Toast.makeText(this, "Banco excluído com sucesso", Toast.LENGTH_SHORT).show();
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
                throw new Exception("Informe o nome do banco");
            }

            EditText edFenaban = (EditText)this.findViewById(R.id.etFenaban);
            if (edFenaban.getText().toString().trim().equals("")) {
                edFenaban.requestFocus();
                throw new Exception("Informe o código Fenaban");
            }

            bank.description = edNome.getText().toString();
            bank.code = edFenaban.getText().toString();

            new Upload().execute(bank);

            Toast.makeText(this, "Banco salvo com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }

}
