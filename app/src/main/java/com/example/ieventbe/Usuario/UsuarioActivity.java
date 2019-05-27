package com.example.ieventbe.Usuario;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.ieventbe.MainActivity;
import com.example.ieventbe.R;

public class UsuarioActivity extends AppCompatActivity {

     protected BluetoothAdapter btfAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        getSupportActionBar().hide();

        //declara as variaveis

        ImageView eventos = (ImageView)findViewById(R.id.eventos);
        ImageView comousa = (ImageView)findViewById(R.id.funciona);
        ImageView informacao = (ImageView)findViewById(R.id.info);
        ImageView normasuso = (ImageView)findViewById(R.id.normasuso);
        ImageView sair = (ImageView)findViewById(R.id.sair);
        Switch ligablu = (Switch)findViewById(R.id.ligaBlu);
        btfAdapter = BluetoothAdapter.getDefaultAdapter();

        //Liga e desliga Bluetooth

        ligablu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                        btfAdapter.enable();
                        Toast.makeText(getApplicationContext(),"Bluetooth Ligado",Toast.LENGTH_SHORT).show();

                } else {

                    btfAdapter.disable();
                    Toast.makeText(getApplicationContext(),"Bluetooth Desligado",Toast.LENGTH_SHORT).show();

                }
            }
        });


        //quando clicar no botao eventos

        eventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Quando o usuário clicar no menu como usar !!

        comousa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioActivity.this);

                builder.setTitle("Seja bem vindo ao iEventBe"); //titulo
                builder.setIcon(R.drawable.information); //icone
                builder.setMessage("Obrigado por utilizar o aplicativo iEventBe !!!" +
                        "\n\n Para usar basta ligar o Bluetooth do seu celular," +
                        " estar próximo a um beacon, receber a notificação" +
                        " e confirmar seus dados !! \n\n Após isso seu nome já estará na lista de presença" +
                        " do evento notificado !!!!"); // mensagem

                AlertDialog alertDialog = builder.create(); //cria o modal
                alertDialog.show(); //mostra o modal

            }
        });


        //informacao click

        informacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //norma de uso click

        normasuso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Quando o usuário clicar no botão sair

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioActivity.this);
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
