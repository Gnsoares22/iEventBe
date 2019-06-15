package com.example.ieventbe.Empresa;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ieventbe.Classes.Evento;
import com.example.ieventbe.MainActivity;
import com.example.ieventbe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CadastroEventoActivity extends AppCompatActivity {

    private ImageView fotoevento;
    private ProgressBar progresso;
    private EditText tituloevento;
    private EditText descricaoevento;
    private EditText periodoevento;
    private EditText organizadorevento;
    private Spinner listaestado;
    private Spinner listacidade;
    Bitmap imagem;

    private Uri mImageUri;


    //variáveis do firebase e do storage data

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask EventosUploadTask;

    //Atividade para cadastro de eventos com upload de foto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);

        getSupportActionBar().setTitle("Cadastrar Eventos"); //da um titulo para o cabeçalho da acitvity do app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //mostra o botao voltar
        getSupportActionBar().setHomeButtonEnabled(true); //ativa o botao voltar


        //variáveis da minha view

        tituloevento = findViewById(R.id.txtTituloEvento);
        descricaoevento = findViewById(R.id.txtDescricaoEvento);
        periodoevento = findViewById(R.id.txtPeriodoEvento);
        organizadorevento = findViewById(R.id.txtEventoOrganizador);
        listaestado = (Spinner) findViewById(R.id.spinnerestado);
        listacidade = (Spinner) findViewById(R.id.spinnercidade);
        ImageView btnUpload = (ImageView) findViewById(R.id.btnUpload);
        ImageView btnCamera = (ImageView) findViewById(R.id.btnUploadCamera);
        fotoevento = findViewById(R.id.fotoevento);
        progresso = findViewById(R.id.Progresso);
        Button btnCadastra = (Button) findViewById(R.id.btnCadastraEvento);

        mStorageRef = FirebaseStorage.getInstance().getReference("fotoeventos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("eventos");


        //criando lista para estados para exbir no spinner estados

        ArrayAdapter<CharSequence> estados = ArrayAdapter.createFromResource(this, R.array.Estado, android.R.layout.simple_spinner_item);
        estados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listaestado.setAdapter(estados);


        //criando lista para cidades para exibir no spinner cidades

        ArrayAdapter<CharSequence> cidades = ArrayAdapter.createFromResource(this, R.array.Cidades, android.R.layout.simple_spinner_item);
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


        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verifica se a foto é nula ou se elá está em carregamento ainda

                if (EventosUploadTask != null && EventosUploadTask.isInProgress()) {

                    Toast.makeText(CadastroEventoActivity.this, "Foto do evento está" +
                            " carregando aguarde !!!", Toast.LENGTH_LONG).show();


                } else {

                    //Metodo para salvar os dados no BD firebase

                    SalvadadosBd();

                }
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
            default:
                break;
        }
        return true;

    }


    //abre a galeria de foto do meu celular através deste método

    private void abrirfotosandroid() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i, 1);
    }


    //tirar foto diratamente do meu celular

    private void tirarfoto() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 2);


    }

    //recebe a requisição do metodo abrir fotos da galeria do meu celular e processa no aplicativo

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se o codigo de requisição for 1 eu pego diretamente da minha galeria de fotos

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(fotoevento);

            fotoevento.setVisibility(View.VISIBLE);

            //se o codigo de requisição for 2 eu consigo tirar foto pelo celular

        } else if (requestCode == 2 && resultCode == RESULT_OK) {


            imagem = (Bitmap) data.getExtras().get("data");
            fotoevento.setImageBitmap(imagem);
            fotoevento.setVisibility(View.VISIBLE);


        }

    }

    //pega extensao da foto

    private String getFileExtension(Uri uri) {

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));

    }


    //METODO QUE SALVA OS DADOS NO FIREBASE REALTIME E NO FIREBASESTORE

    private void SalvadadosBd() {

        //SE A FOTO FOR PEGA DIRETO DO APARELHO

        if (mImageUri != null) {

            final StorageReference filereference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            EventosUploadTask = filereference.putFile(mImageUri)

                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override

                //se a foto for sucesso manda para o banco
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //para de executar o progressbar apos 5 segundos e o progressbar zera !!!
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progresso.setProgress(0);
                        }
                    }, 5000);


                    //método novo para armazenar imagens

                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();


                    //metodo que pega tudoo !!!

                    Evento eventos = new Evento(tituloevento.getText().toString().trim(), descricaoevento
                            .getText().toString().trim(), periodoevento.getText().toString().trim(), organizadorevento
                            .getText().toString().trim(), downloadUrl.toString(), listaestado.getSelectedItem().toString().trim(),
                            listacidade.getSelectedItem().toString().trim());

                    String eventoId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(eventoId).setValue(eventos);


                    //sucesso ao gravar os dados

                    Toast.makeText(CadastroEventoActivity.this, "Evento cadastrado" +
                            " com sucesso !!!", Toast.LENGTH_LONG).show();


                }
            })

                    //se não mostra a exceção de erro
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(CadastroEventoActivity.this,
                                    e.getMessage(), Toast.LENGTH_LONG);

                        }
                    })


                    //adiciona o tempo na barra de progresso

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //seta o tempo da barra de progresso até a foto ir para o firebase storage

                            double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progresso.setProgress((int) progress);

                        }
                    });

            //SE A IMAGEM FOR PEGA DIRETAMENTE DA CAMERA

        }  else

            Toast.makeText(CadastroEventoActivity.this, "Nenhuma imagem" +
                    " selecionada !!!", Toast.LENGTH_LONG).show();

    }

}


