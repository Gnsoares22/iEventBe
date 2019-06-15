package com.example.ieventbe.Empresa;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ieventbe.BancoBD.ImageSubAdapter;
import com.example.ieventbe.BancoBD.Lp_Adapter;
import com.example.ieventbe.Classes.ListaPresenca;
import com.example.ieventbe.Classes.SubEvento;
import com.example.ieventbe.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ListaPresencaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Lp_Adapter adapterlp;

    private DatabaseReference DatabaseRef;
    private List<ListaPresenca> lps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_presenca);
        getSupportActionBar().setTitle("Lista de presença");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Lógica da lista aqui em baixo

        recyclerView = findViewById(R.id.recycler_view_lista_presenca);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lps = new ArrayList<>();

        adapterlp = new Lp_Adapter(ListaPresencaActivity.this,lps);

        DatabaseRef = FirebaseDatabase.getInstance().getReference("ListaPresenca");

        DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    ListaPresenca lp = postSnapshot.getValue(ListaPresenca.class);
                    lps.add(lp);

                }

                adapterlp = new Lp_Adapter(ListaPresencaActivity.this,lps);

                recyclerView.setAdapter(adapterlp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ListaPresencaActivity.this,databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ListaSubeventosActivity.class));
                finishAffinity();
                break;
            default:
                break;
        }
        return true;

    }
}
