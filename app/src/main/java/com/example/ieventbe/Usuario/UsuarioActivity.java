package com.example.ieventbe.Usuario;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ieventbe.Beacon.ConfirmacaoActivity;
import com.example.ieventbe.LoginActivity;
import com.example.ieventbe.R;
import com.example.ieventbe.Sobre.SobreUsuarioActivity;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import java.text.DecimalFormat;
import java.util.Collection;

public class UsuarioActivity extends AppCompatActivity implements BeaconConsumer {

    //Constante de coordenada do Beacon do tipo iBeacon
    private static final String IBEACON_LAYOUT = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";
    //UUID  do beacon
    private static final String BEACON_ID = "003e8c80-ea01-4ebb-b888-78da19df9e55";
    protected BluetoothAdapter btfAdapter;
    private BeaconManager beaconManager = null;


    //formatar casas decimais para distancia do beacon

    DecimalFormat format = new DecimalFormat("0.00");


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        getSupportActionBar().hide();

        //declara as variaveis

        ImageView eventos = (ImageView) findViewById(R.id.eventos);
        ImageView comousa = (ImageView) findViewById(R.id.funciona);
        ImageView sobre = (ImageView) findViewById(R.id.sobre);
        ImageView normasuso = (ImageView) findViewById(R.id.normasuso);
        ImageView sair = (ImageView) findViewById(R.id.sair);
        Switch ligablu = (Switch) findViewById(R.id.ligaBlu);

        //Metodo do Bluetooth
        btfAdapter = BluetoothAdapter.getDefaultAdapter();

        //Multiplas permissoes (SERIA INTERESSANTE COLOCAR UM WHILE PARA FICAR MANDANDO ATÉ O USUARIO ACEITAR)

       requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE}, 1234);


        //variáveis do Beacon

        beaconManager = BeaconManager.getInstanceForApplication(this); //pegando a instancia da aplicação
        //definindo a coordenada do iBeacon
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(IBEACON_LAYOUT));
        //periodo de scanner do beacon
        beaconManager.setForegroundScanPeriod(10000); //7000 == 7 segundos ... 100000 == 1 minuto
        beaconManager.bind(this);


        //Liga e desliga Bluetooth
        ligablu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    btfAdapter.enable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Ligado", Toast.LENGTH_SHORT).show();

                } else {

                    btfAdapter.disable();
                    Toast.makeText(getApplicationContext(), "Bluetooth Desligado", Toast.LENGTH_SHORT).show();

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


        //Quando o usuário clicar no botao sobre o aplicativo

        sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UsuarioActivity.this, SobreUsuarioActivity.class);
                startActivity(i);

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
                        Intent i = new Intent(UsuarioActivity.this, LoginActivity.class);
                        startActivity(i);
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

    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

    //callback do beacon na tela

    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacon, Region region) {
                //comando depreciado pelo android studio porem ainda pode ser usado

                if (beacon.size() > 0) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UsuarioActivity.this);

                    builder.setTitle("Notificação de evento"); //titulo
                    builder.setIcon(R.drawable.information); //icone
                    builder.setMessage("Deseja participar da palestra xxxxxx? " +
                            "\n\n Você está numa distancia de:  " + format.format( beacon.iterator().next().getDistance())
                    + " metros do sub evento."); // mensagem

                    //se o usuário confirmar sua participação no sub evento
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            //manda para a tela de confirmação de dados para registrar presença no sub evento
                            Intent i = new Intent(UsuarioActivity.this,ConfirmacaoActivity.class);
                            startActivity(i);

                        }
                    });

                    //se o usuário não confirmar sua participação no sub evento
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            //acontece nada

                        }
                    });

                    AlertDialog alertDialog = builder.create(); //cria o modal
                    alertDialog.show(); //mostra o modal


                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse(BEACON_ID),
                    Identifier.parse("893"), Identifier.parse("77")));
        } catch (RemoteException e) {   }

        beaconManager.getBeaconParsers().add(new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

         }


    }


