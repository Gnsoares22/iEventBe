package com.example.ieventbe.Beacon;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ieventbe.Classes.ListaPresenca;
import com.example.ieventbe.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public class ConfirmacaoActivity extends AppCompatActivity {

    //variáveis globais para receber os parâmetros da UsuarioActivity
    String idBeacon = "";
    Integer idEvento = 0;


    FirebaseDatabase database;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);

        //Variáveis da lista de presença
        final EditText email = (EditText) findViewById(R.id.txtEmail);
        final EditText nome = (EditText) findViewById(R.id.txtNome);

        Button btnConfirma = (Button) findViewById(R.id.btnConfirma);

        getSupportActionBar().setTitle("Confirmação de presença");


        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        Date datahora = new Date();

        //pega o número do telefone do usuário É NATURAL APARECER ESSE ERRO, POIS SE EU JPA DEI PERMISSÃO
        //ELE É IGNORADO (PERMISSÃO BURLADA !!)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        final String numero = telephonyManager.getLine1Number();


        //pega a data atual do sistema

        Date data = new Date();

        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");

        final String dataFormatada = formataData.format(data);


        //Pega o horario atual do sistema

        SimpleDateFormat horaformatada = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        Date hora = Calendar.getInstance().getTime();

        final String hora_atual = horaformatada.format(hora);


        //credenciais do banco de dados

        database = FirebaseDatabase.getInstance();

        final DatabaseReference reference = database.getReference();


        //botao confirmar presença click

        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ListaPresenca lista = new ListaPresenca();
                lista.setId(UUID.randomUUID().toString());
                lista.setNome(nome.getText().toString());
                lista.setEmail(email.getText().toString());
                lista.setNumerocelular(numero.toString());
                lista.setDatachegada(dataFormatada.toString());
                lista.setHorachegada(hora_atual.toString());
                lista.setHorasaida("------");

                reference.child("ListaPresenca").setValue(lista);

                Toast.makeText(ConfirmacaoActivity.this, "Sua presença foi salva na lista de presença do subevento :) !!!",
                        Toast.LENGTH_LONG).show();

                //pega parâmetros passados pela UsuarioActivity
                Intent intent = getIntent();
                idBeacon = intent.getStringExtra("idBeacon");
                idEvento = intent.getIntExtra("idEvento",0);

                //devolve para a UsuarioActivity os parâmetros do evento confirmado
                Intent intentResult=new Intent();
                intentResult.putExtra("idBeacon",idBeacon);
                intentResult.putExtra("idEvento",idEvento);
                intentResult.putExtra("idPresenca", lista.getId());
                setResult(111,intentResult);

                //zera os campos !!!

                nome.setText("");
                email.setText("");

                finish();
            }
        });





    }

}