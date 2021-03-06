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
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.ieventbe.BancoBD.ImageAdapter;
import com.example.ieventbe.Classes.Evento;
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

public class ListaEventosActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;

    private ProgressBar progressc;

    private ValueEventListener mDBListener;
    private FirebaseStorage fotos;
    private DatabaseReference DatabaseRef;
    private List<Evento> eventos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        getSupportActionBar().setTitle("Lista de eventos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Lógica da lista aqui em baixo

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressc = findViewById(R.id.progress_circulo);

        eventos = new ArrayList<>();

        adapter = new ImageAdapter(ListaEventosActivity.this,eventos);

        fotos = FirebaseStorage.getInstance();

        DatabaseRef = FirebaseDatabase.getInstance().getReference("eventos");

      mDBListener =  DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventos.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    Evento evento = postSnapshot.getValue(Evento.class);
                    evento.setId(postSnapshot.getKey());
                    eventos.add(evento);

                }

                adapter.notifyDataSetChanged();

                adapter = new ImageAdapter(ListaEventosActivity.this,eventos);

                recyclerView.setAdapter(adapter);
                progressc.setVisibility(View.INVISIBLE);

                adapter.setOnItemClickListener(ListaEventosActivity.this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ListaEventosActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }


    @Override
    public void onItemClick(int position) {
        //somente pega a posição
    }

    @Override
    public void onEditClick(int position) {

        Toast.makeText(getApplicationContext(),"Em Breve",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onDeleteClick(int position) {

        Evento eventoselecionado = eventos.get(position);
        final String idselecionado = eventoselecionado.getId();
        StorageReference imageref = fotos.getReferenceFromUrl(eventoselecionado.getImagemEventoUrl());


        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                DatabaseRef.child(idselecionado).removeValue();

                Toast.makeText(getApplicationContext(),"Evento Deletado !!!!",Toast.LENGTH_LONG).show();


            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseRef.removeEventListener(mDBListener);

    }

    @Override
    public void onSubClick(int position) {

        //vai para os sub eventos listados

        Intent i = new Intent(ListaEventosActivity.this, ListaSubeventosActivity.class);
        startActivity(i);


    }
}
