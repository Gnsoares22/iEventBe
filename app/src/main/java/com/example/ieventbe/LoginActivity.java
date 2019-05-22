package com.example.ieventbe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ieventbe.BancoBD.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtSenha;
    private Button btnEntrar;
    DatabaseHelper db;
    private CheckBox cu,ce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); //esconde a barra

        db = new DatabaseHelper(this);

        //adicionando alguns usuários aleatórios no banco local Sqlite

        db.addLogin("maria@gmail.com","1234","E");
        db.addLogin("fatec@gmail.com","12345","E");
        db.addLogin("gnsoares8@gmail.com","12345","U");
        db.addLogin("adilson@gmail.com","12345","U");

       //busca o id dos buttons e editviews criados no res->layout
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtSenha = (EditText)findViewById(R.id.txtSenha);
        btnEntrar = (Button)findViewById(R.id.btnSalvar);
        ce = (CheckBox)findViewById(R.id.checke);
        cu = (CheckBox)findViewById(R.id.checku);

        //on click do botao btnEntrar
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();

                Boolean checkemailpassU = db.checkemailesenhaUsuario(email,senha);
                Boolean checkemailpassE = db.checkemailesenhaEmpresa(email,senha);

                if(email.equals("") || email.length() < 4) {

                    Toast.makeText(getApplicationContext(),"Preencha o campo email", Toast.LENGTH_SHORT).show();

                } else if(senha.equals("")){

                    Toast.makeText(getApplicationContext(),"Preencha o campo senha", Toast.LENGTH_SHORT).show();

                } else if(ce.isChecked() && cu.isChecked()){

                    Toast.makeText(getApplicationContext(),"Escolha somente um tipo de usuário", Toast.LENGTH_SHORT).show();

                } else if(ce.isChecked() == false && cu.isChecked() == false){

                    Toast.makeText(getApplicationContext(),"Selecione o tipo de usuário", Toast.LENGTH_SHORT).show();

                } else if(checkemailpassU == true && cu.isChecked()){

                    //acrescentar uma internet para mandar para tela do usuário

                    Toast.makeText(getApplicationContext(),"Usuário", Toast.LENGTH_SHORT).show();

                }  else if(checkemailpassE == true && ce.isChecked()){

                    //Incrementar uma intent para mandar para tela da empresa

                    Toast.makeText(getApplicationContext(),"Empresa", Toast.LENGTH_SHORT).show();

                } else if(checkemailpassU == false || checkemailpassE == false){

                    Toast.makeText(getApplicationContext(),"Usuário não encontrado", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }


}
