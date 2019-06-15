package com.example.ieventbe.Empresa;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ieventbe.BancoBD.ImageAdapter;
import com.example.ieventbe.BancoBD.ImageSubAdapter;
import com.example.ieventbe.Classes.Evento;
import com.example.ieventbe.Classes.SubEvento;
import com.example.ieventbe.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListaSubeventosActivity extends AppCompatActivity implements ImageSubAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageSubAdapter adaptersub;

    private ProgressBar progressc;

    private ValueEventListener mDBListener;
    private FirebaseStorage fotos;
    private DatabaseReference DatabaseRef;
    private List<SubEvento> subeventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_subeventos);

        getSupportActionBar().setTitle("Lista de sub eventos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Lógica da lista aqui em baixo

        recyclerView = findViewById(R.id.recycler_view_sub);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressc = findViewById(R.id.progress_circulo);

        subeventos = new ArrayList<>();

        adaptersub = new ImageSubAdapter(ListaSubeventosActivity.this,subeventos);

        fotos = FirebaseStorage.getInstance();

        DatabaseRef = FirebaseDatabase.getInstance().getReference("subeventos");

        mDBListener =  DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                subeventos.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    SubEvento sub = postSnapshot.getValue(SubEvento.class);
                    sub.setSub_id(postSnapshot.getKey());
                    subeventos.add(sub);

                    progressc.setVisibility(View.INVISIBLE);

                }

                adaptersub.notifyDataSetChanged();

                adaptersub = new ImageSubAdapter(ListaSubeventosActivity.this,subeventos);

                recyclerView.setAdapter(adaptersub);

              adaptersub.setOnItemClickListener(ListaSubeventosActivity.this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ListaSubeventosActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressc.setVisibility(View.INVISIBLE);

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, EmpresaActivity.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_filtro, menu);

        MenuItem procurar = menu.findItem(R.id.menu_filtra);
        SearchView procura = (SearchView) procurar.getActionView();

        procura.setImeOptions(EditorInfo.IME_ACTION_DONE);


        procura.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adaptersub.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onEditClick(int position) {

        Toast.makeText(getApplicationContext(),"Em Breve",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDeleteClick(int position) {

        SubEvento subselecionado = subeventos.get(position);
        final String idselecionado = subselecionado.getSub_id();
        StorageReference imageref = fotos.getReferenceFromUrl(subselecionado.getSub_foto());


        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                DatabaseRef.child(idselecionado).removeValue();

                Toast.makeText(getApplicationContext(),"Sub evento Deletado !!!!",Toast.LENGTH_LONG).show();


            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseRef.removeEventListener(mDBListener);

    }

    @Override
    public void onListClick(int position) {

        Intent i = new Intent(ListaSubeventosActivity.this, ListaPresencaActivity.class);
        startActivity(i);

    }
}
