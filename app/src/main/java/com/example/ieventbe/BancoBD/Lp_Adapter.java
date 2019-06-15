package com.example.ieventbe.BancoBD;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ieventbe.Classes.ListaPresenca;
import com.example.ieventbe.Classes.SubEvento;
import com.example.ieventbe.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Lp_Adapter extends RecyclerView.Adapter<Lp_Adapter.ImageViewHolder> implements Filterable {

    //variavel de contexto e lista

    private Context e_context;
    private List<ListaPresenca> e_eventos;
    private List<ListaPresenca> e_eventos_full;


    public Lp_Adapter(Context context, List<ListaPresenca> lp) {

        e_context = context;
        e_eventos = lp;
        e_eventos_full = new ArrayList<>(lp);

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(e_context).inflate(R.layout.imagem_ls, parent, false);
        return new ImageViewHolder(v);

    }


    //metodo que pega os items da classe do evento e vai formando a recycleview

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

        //coloca os atributos no banco alocado em cada parte da imagem_evento da RecycleView


        ListaPresenca ls = e_eventos.get(position);
        holder.txtNome.setText(" Nome: " + ls.getNome());
        holder.txtEmail.setText(" Email: " + ls.getEmail());
        holder.txtNumerocel.setText(" Número celular: " + ls.getNumerocelular());
        holder.txtDataevento.setText(" Data do sub evento: " + ls.getDatachegada());
        holder.txthorachegada.setText(" Hora chegada: " + ls.getHorachegada());
        holder.txthorasaida.setText("Hora saida: " + ls.getHorasaida());


    }

    @Override
    public int getItemCount() {
        return e_eventos.size();
    }


    //Metodo do filtro na recycleview

    @Override
    public Filter getFilter() {
        return filtro;
    }

    private Filter filtro = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<ListaPresenca> eventosfiltrados = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                eventosfiltrados.addAll(e_eventos_full);

            } else {

                String resultado = constraint.toString().toLowerCase().trim();

                for (ListaPresenca item : e_eventos_full) {

                    if (item.getNome().toLowerCase().contains(resultado) || item.getEmail().toLowerCase().contains(resultado)
                            || item.getDatachegada().toLowerCase().contains(resultado) || item.getHorachegada().toLowerCase().contains(resultado) ||
                            item.getHorasaida().toLowerCase().contains(resultado)) {

                        eventosfiltrados.add(item);

                    }
                }
            }

            FilterResults filtra_resultados = new FilterResults();
            filtra_resultados.values = eventosfiltrados;

            return filtra_resultados;

        }

        //metodo que publica os resultados de pesquisa

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            e_eventos.clear();
            e_eventos.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    //metodo que pega o id dos items da lista recycleview

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        //variaveis que irão ser listadas através do que já está registrado no BD

        public TextView txtNome;
        public TextView txtEmail;
        public TextView txtNumerocel;
        public TextView txtDataevento;
        public TextView txthorachegada;
        public TextView txthorasaida;


        //construtor da classe ImageViewHolder

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            // para formar os itens que serão listados

            txtNome = itemView.findViewById(R.id.ls_nome);
            txtEmail = itemView.findViewById(R.id.ls_numero);
            txtNumerocel = itemView.findViewById(R.id.ls_data);
            txtDataevento = itemView.findViewById(R.id.ls_data);
            txthorachegada = itemView.findViewById(R.id.ls_hora_inicio);
            txthorasaida = itemView.findViewById(R.id.ls_hora_saida);


        }

    }

}



