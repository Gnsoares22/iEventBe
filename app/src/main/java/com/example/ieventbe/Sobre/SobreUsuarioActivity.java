package com.example.ieventbe.Sobre;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ieventbe.R;
import com.example.ieventbe.Usuario.UsuarioActivity;

public class SobreUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        getSupportActionBar().setTitle("Sobre o Aplicativo");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //mostra o botao voltar
        getSupportActionBar().setHomeButtonEnabled(true); //ativa o botao voltar


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, UsuarioActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();
                break;
            default:break;
        }
        return true;
    }
}
