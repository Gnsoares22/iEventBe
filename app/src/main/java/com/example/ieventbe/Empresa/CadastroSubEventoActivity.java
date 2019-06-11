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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ieventbe.Classes.Evento;
import com.example.ieventbe.Classes.SubEvento;
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

public class CadastroSubEventoActivity extends AppCompatActivity {

    private ImageView fotosubevento;
    private ProgressBar progresso;
    private EditText responsavel;
    private EditText local;
    private EditText minicurriculo;
    private EditText periodosubevento;
    private EditText descricaosubevento;
    private Spinner tiposubevento;
    Bitmap imagem;

    private Uri mImageUri;

    private StorageReference stref;
    private DatabaseReference ref;

    private StorageTask SubeventosUploadTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_evento);

        getSupportActionBar().setTitle("Cadastrar Sub eventos"); //da um titulo para o cabeçalho da acitvity do app
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //mostra o botao voltar
        getSupportActionBar().setHomeButtonEnabled(true); //ativa o botao voltar



        responsavel = findViewById(R.id.txtResponsavel);
        local = findViewById(R.id.txtAtividades);
        minicurriculo = findViewById(R.id.txtMinicurriculo);
        descricaosubevento = findViewById(R.id.txtDescricaoSub);
        periodosubevento = findViewById(R.id.txtPeriodoSubevento);
        tiposubevento = (Spinner) findViewById(R.id.spinnertiposubevento);
        ImageView btnUpload = (ImageView) findViewById(R.id.btnUpload);
        ImageView btnCamera = (ImageView) findViewById(R.id.btnUploadCamera);
        fotosubevento = findViewById(R.id.fotosubevento);
        progresso = findViewById(R.id.Progresso);
        Button btnCadastraSub = (Button) findViewById(R.id.btnCadastraSubEvento);

        //Instanciando a referencia para a classe sub evento

        stref = FirebaseStorage.getInstance().getReference("subevento");
        ref = FirebaseDatabase.getInstance().getReference("subeventos");


        //criando lista para tipos de sub eventos para exbir no spinner tipo de sub eventos

        ArrayAdapter<CharSequence> tipos = ArrayAdapter.createFromResource(this, R.array.Subeventos, android.R.layout.simple_spinner_item);
        tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposubevento.setAdapter(tipos);


        btnCadastraSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verifica se a foto é nula ou se elá está em carregamento ainda

                if (SubeventosUploadTask != null && SubeventosUploadTask.isInProgress()) {

                    Toast.makeText(CadastroSubEventoActivity.this, "Foto do evento está" +
                            " carregando aguarde !!!", Toast.LENGTH_LONG).show();

                } else {

                    //método pára cadastrar subevento
                    SalvadadosBd();

                }

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo para ligar a camera do celular
                tirarfoto();

            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //metodo para fazer upload diretamente dos arquivos do seu celular
                abrirfotosandroid();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //se o codigo de requisição for 1 eu pego diretamente da minha galeria de fotos

        if (requestCode == 1 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(fotosubevento);

            fotosubevento.setVisibility(View.VISIBLE);

            //se o codigo de requisição for 2 eu consigo tirar foto pelo celular

        } else if (requestCode == 2 && resultCode == RESULT_OK) {


            imagem = (Bitmap) data.getExtras().get("data");
            fotosubevento.setImageBitmap(imagem);
            fotosubevento.setVisibility(View.VISIBLE);


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

            final StorageReference filereference = stref.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            SubeventosUploadTask = filereference.putFile(mImageUri)

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
                            }, 500);


                            //método novo para armazenar imagens

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();


                            //metodo que pega tudoo da classe sub evento para registrar no firebase!!!


                            SubEvento sub = new SubEvento(responsavel.getText().toString().trim(), local
                                    .getText().toString().trim(), minicurriculo.getText().toString().trim(), descricaosubevento
                                    .getText().toString().trim(), periodosubevento.getText().toString().trim(),
                                    downloadUrl.toString(), tiposubevento.getSelectedItem().toString().trim());

                            String subeventoId = ref.push().getKey();
                            ref.child(subeventoId).setValue(sub);


                            //sucesso ao gravar os dados

                            Toast.makeText(CadastroSubEventoActivity.this, "Sub Evento cadastrado" +
                                    " com sucesso !!!", Toast.LENGTH_LONG).show();


                        }
                    })

                    //se não mostra a exceção de erro
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(CadastroSubEventoActivity.this,
                                    e.getMessage(), Toast.LENGTH_LONG);

                        }
                    })


                    //adiciona o tempo na barra de progresso

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //seta o tempo da barra de progresso até a foto ir para o firebase storage

                            double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progresso.setProgress((int) progress);

                        }
                    });

            //SE A IMAGEM FOR PEGA DIRETAMENTE DA CAMERA

        }  else

            Toast.makeText(CadastroSubEventoActivity.this, "Nenhuma imagem" +
                    " selecionada !!!", Toast.LENGTH_LONG).show();

    }

}
