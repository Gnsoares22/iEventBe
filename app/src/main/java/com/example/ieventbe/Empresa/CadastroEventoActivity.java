package com.example.ieventbe.Empresa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.ieventbe.R;
import com.squareup.picasso.Picasso;

public class CadastroEventoActivity extends AppCompatActivity {

    private ImageView fotoevento;

    private Uri mImageUri;

    //Atividade para cadastro de eventos com upload de foto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        getSupportActionBar().setTitle("Cadastrar Eventos"); //da um titulo para o cabeçalho da acitvity do app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //mostra o botao voltar
        getSupportActionBar().setHomeButtonEnabled(true); //ativa o botao voltar


        //variáveis da minha view

        EditText tituloevento = (EditText)findViewById(R.id.txtTituloEvento);
        EditText descricaoevento = (EditText)findViewById(R.id.txtDescricaoEvento);
        EditText periodoevento = (EditText)findViewById(R.id.txtPeriodoEvento);
        EditText organizadorevento = (EditText)findViewById(R.id.txtEventoOrganizador);
        Spinner listaestado = (Spinner)findViewById(R.id.spinnerestado);
        Spinner listacidade = (Spinner)findViewById(R.id.spinnercidade);
        ImageButton btnUpload = (ImageButton)findViewById(R.id.btnUpload);
        ImageButton btnCamera = (ImageButton)findViewById(R.id.btnUploadCamera);
        fotoevento = findViewById(R.id.fotoevento);
        Button cadastraeventp = (Button)findViewById(R.id.btnCadastraEvento);


        //criando lista para estados para exbir no spinner estados

        ArrayAdapter<CharSequence> estados = ArrayAdapter.createFromResource(this,R.array.Estado,android.R.layout.simple_spinner_item);
        estados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaestado.setAdapter(estados);


        //criando lista para cidades para exibir no spinner cidades

        ArrayAdapter<CharSequence> cidades = ArrayAdapter.createFromResource(this,R.array.Cidades,android.R.layout.simple_spinner_item);
        cidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listacidade.setAdapter(cidades);


        //Quando clicar no botao upload

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                abrirfotosandroid();
            }

        });


        //Quando clicar no botao Camera Celular

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tirarfoto();

            }
        });

    }


    //Codigo para voltar para tela apos clicar na setinha de voltar no ActionBar
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, EmpresaActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();
                break;
            default:break;
        }
        return true;

    }


    //abre a galeria de foto do meu celular através deste método

    private void abrirfotosandroid() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }


    //tirar foto diratamente do meu celular

    private void tirarfoto() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,2);


    }

    //recebe a requisição do metodo abrir fotos da galeria do meu celular e processa no aplicativo

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se o codigo de requisição for 1 eu pego diretamente da minha galeria de fotos

        if(requestCode == 1 && resultCode == RESULT_OK
        && data != null && data.getData() != null){

            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(fotoevento);

            fotoevento.setVisibility(View.VISIBLE);

            //se o codigo de requisição for 2 eu consigo tirar foto pelo celular

        } else if(requestCode == 2 && resultCode == RESULT_OK){

            Bundle extras = data.getExtras();
            Bitmap imagem =  (Bitmap) extras.get("data");
            fotoevento.setImageBitmap(imagem);
            fotoevento.setVisibility(View.VISIBLE);

        }

    }


}
