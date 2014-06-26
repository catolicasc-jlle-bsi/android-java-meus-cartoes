package com.mycards003.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Renan on 25/06/14.
 */
public class CadBancoActivity extends Activity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_banco);

        btn = (Button)findViewById(R.id.btnCadastro);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EditText edNome = (EditText)this.findViewById(R.id.etNome);
        edNome.setText(Parametros.getInstance().nm_banco);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvar_e_sair();
            }
        });
    }

    private void salvar_e_sair() {
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
