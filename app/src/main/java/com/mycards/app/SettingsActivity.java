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

public class SettingsActivity extends Activity {

    private Button btn;
    private Flag flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_settings);

        btn = (Button)findViewById(R.id.btnSalvar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        EditText edNome = (EditText)this.findViewById(R.id.etIp);
        edNome.setText(Parametros.getInstance().IP);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });
    }

    private void salvarESair() {
        try {
            EditText edIp = (EditText)this.findViewById(R.id.etIp);
            if (edIp.getText().toString().trim().equals("")) {
                edIp.requestFocus();
                throw new Exception("Informe o endereço de IP");
            }

            Parametros.getInstance().IP = edIp.getText().toString();

            Toast.makeText(this, "Configuração salva com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void finalizar() {
        this.finish();
    }

}
