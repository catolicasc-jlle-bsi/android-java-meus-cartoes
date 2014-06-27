package com.mycards.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mycards.api.Download;
import com.mycards.api.Upload;
import com.mycards.business.Bank;
import com.mycards.business.Card;
import com.mycards.business.Flag;
import com.mycards003.app.R;

public class CadCartaoActivity extends Activity {

    private Card card;

    private Button btn;
    private Button btnDelete;

    private EditText name;
    private EditText cardName;
    private EditText cardNumber;
    private EditText dateValidatedMonth;
    private EditText dateValidatedYear;
    private EditText verifyCode;
    private Spinner flag;
    private Spinner bank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cad_cartao);
        btn = (Button)findViewById(R.id.btnCadastro);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        name = (EditText)this.findViewById(R.id.etNome);
        cardName = (EditText)this.findViewById(R.id.etcardName);
        cardNumber = (EditText)this.findViewById(R.id.etcardNumber);
        dateValidatedMonth = (EditText)this.findViewById(R.id.etValidateMonth);
        dateValidatedYear = (EditText)this.findViewById(R.id.etValidateYear);
        verifyCode = (EditText)this.findViewById(R.id.etVerifyCode);

        bank = (Spinner)findViewById(R.id.spinner_banco);
        flag = (Spinner)findViewById(R.id.spinner_bandeira);
    }

    @Override
    protected void onStart() {
        super.onStart();

        card = (Card) Parametros.getInstance().model;

        if (Parametros.getInstance().model == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else if (Parametros.getInstance().model.id == null) {
            btnDelete.setVisibility(View.INVISIBLE);
        } else {
            btnDelete.setVisibility(View.VISIBLE);
        }

        name.setText(card.name);
        cardName.setText(card.cardName);
        cardNumber.setText(card.cardNumber);
        dateValidatedMonth.setText(card.dateValidatedMounth);
        dateValidatedYear.setText(card.dateValidatedYear);
        verifyCode.setText(card.verifyCode);

        // O spinner será carregado por outra thread
        new Download().execute(new Bank(), this, bank);
        new Download().execute(new Flag(), this, flag);

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
            //TODO:Implementar rotina de exclusão

            Toast.makeText(this, "Cartão excluído com sucesso", Toast.LENGTH_SHORT).show();
            finalizar();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void salvarESair() {
        try {
            if (name.getText().toString().trim().equals("")) {
                name.requestFocus();
                throw new Exception("Informe o nome do cartão");
            }

            if (dateValidatedMonth.getText().toString().trim().equals("")) {
                Integer month = Integer.getInteger(dateValidatedMonth.getText().toString().trim());
                if ((month < 1) || (month > 12)) {
                    throw new Exception("Mês de validade inválido");
                }
            }

            card.name = name.getText().toString(); //Campo obrigatório
            card.cardName = cardName.getText().toString();
            card.cardNumber = cardNumber.getText().toString();
            card.dateValidatedMounth = dateValidatedMonth.getText().toString();
            card.dateValidatedYear = dateValidatedYear.getText().toString();
            card.verifyCode = verifyCode.getText().toString();

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
