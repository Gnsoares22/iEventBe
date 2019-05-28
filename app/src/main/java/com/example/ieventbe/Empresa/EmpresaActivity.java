package com.example.ieventbe.Empresa;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ieventbe.R;
import com.example.ieventbe.Sobre.SobreEmpresaActivity;
import com.example.ieventbe.Sobre.SobreUsuarioActivity;
import com.example.ieventbe.Usuario.UsuarioActivity;

public class EmpresaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        getSupportActionBar().hide();

        ImageView criaevento = (ImageView)findViewById(R.id.criaeventos);
        ImageView listaevento = (ImageView)findViewById(R.id.listaeventos);
        ImageView criasubevento = (ImageView)findViewById(R.id.criasubevento);
        ImageView listasubevento = (ImageView)findViewById(R.id.listasubevento);
        ImageView comousa = (ImageView)findViewById(R.id.funciona);
        ImageView sobre = (ImageView)findViewById(R.id.sobre);
        ImageView regulamentos = (ImageView)findViewById(R.id.normasuso);
        ImageView sair = (ImageView)findViewById(R.id.sair);



        //Quando usuário clicar em como usa

        comousa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EmpresaActivity.this);

                builder.setTitle("Seja bem vindo ao iEventBe"); //titulo
                builder.setIcon(R.drawable.information); //icone
                builder.setMessage("Obrigado por utilizar o aplicativo iEventBe !!!" +
                        "\n\n Como empresa você pode estar cadastrando eventos e sub eventos," +
                        " para gerir informações aos usuários e controlar quem esteve presente no sub evento." +
                        "\n\n Crie eventos e sub eventos e torne seu evento automatizado" +
                        " clicando nas opções do menu  !!!!"); // mensagem

                AlertDialog alertDialog = builder.create(); //cria o modal
                alertDialog.show(); //mostra o modal

            }
        });

        //Quando o usuário clicar no botao sobre o aplicativo

        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(EmpresaActivity.this, SobreEmpresaActivity.class);
                startActivity(i);

            }
        });


        //Quando o usuário clicar no botão sair

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EmpresaActivity.this);
                //define o titulo
                builder.setTitle("Deseja sair do aplicativo?");
                builder.setIcon(R.drawable.information);

                //define um botão como positivo
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
                //define um botão como negativo.
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        //acontece nada
                    }
                });

                builder.create();
                builder.show();


            }
        });
    }

}
