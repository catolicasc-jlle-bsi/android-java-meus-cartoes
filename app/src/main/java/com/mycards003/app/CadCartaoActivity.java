package com.mycards003.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mycards.api.Download;
import com.mycards.api.Upload;
import com.mycards.business.Bank;
import com.mycards.business.Card;
import com.mycards.business.Flag;
import com.mycards.business.Model;

import java.util.ArrayList;
import java.util.List;

public class CadCartaoActivity extends Activity {

    private Card card;

    private Button btn;

    private EditText name;
    private EditText cardName;
    private EditText cardNumber;
    private EditText dateValidatedMounth;
    private EditText dateValidatedYear;
    private EditText verifyCode;
    private Spinner flag;
    private Spinner bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_cartao);
        btn = (Button)findViewById(R.id.btnCadastro);

        name = (EditText)this.findViewById(R.id.etNome);
        /* TODO: preencher com os componentes
        cardName =
        cardNumber =
        dateValidatedMounth =
        dateValidatedYear =
        verifyCode =*/
        bank = (Spinner)findViewById(R.id.spinner_banco);
        flag = (Spinner)findViewById(R.id.spinner_bandeira);
    }

    @Override
    protected void onStart() {
        super.onStart();

        card = (Card) Parametros.getInstance().model;

        name.setText(card.name);
        /* TODO: preencher com os componentes
        cardName =
        cardNumber =
        dateValidatedMounth =
        dateValidatedYear =
        verifyCode =*/

        // O spinner será carregado por outra thread
        new Download().execute(new Bank(), this, bank);
        new Download().execute(new Flag(), this, flag);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                salvarESair();
            }
        });
    }

    private void salvarESair() {
        try {
            if (name.getText().toString().trim().equals("")) {
                name.requestFocus();
                throw new Exception("Informe o nome do cartão");
            }

            card.name = name.getText().toString(); //Campo obrigatório
            /* TODO: preencher com os componentes
            cardName =
            cardNumber =
            dateValidatedMounth =
            dateValidatedYear =
            verifyCode =*/

            card.bank = (Bank) bank.getSelectedItem();
            card.flag = (Flag) flag.getSelectedItem();

            new Upload().execute(card);

            Toast.makeText(this, "Cartão salvo com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void finalizar() {
        this.finish();
    }

}
