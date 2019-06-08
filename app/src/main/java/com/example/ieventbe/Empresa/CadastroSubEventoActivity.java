package com.example.ieventbe.Empresa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.ieventbe.R;

public class CadastroSubEventoActivity extends AppCompatActivity {

    private ImageView fotosubevento;
    private ProgressBar progresso;
    private EditText responsavel;
    private EditText local;
    private EditText minicurriculo;
    private EditText periodosubevento;
    private EditText descricaosubevento;
    private Spinner tiposubevento;
    private Spinner eventoid;
    Bitmap imagem;

    private Uri mImageUri;

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


        //criando lista para tipos de sub eventos para exbir no spinner tipo de sub eventos

        ArrayAdapter<CharSequence> tipos = ArrayAdapter.createFromResource(this, R.array.Subeventos, android.R.layout.simple_spinner_item);
        tipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tiposubevento.setAdapter(tipos);


        //CONTINUAR


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
}
