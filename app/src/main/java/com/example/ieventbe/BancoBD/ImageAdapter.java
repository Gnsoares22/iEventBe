package com.example.ieventbe.BancoBD;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ieventbe.Classes.Evento;
import com.example.ieventbe.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> implements Filterable {

    //variavel de contexto e lista

    private Context e_context;
    private List<Evento> e_eventos;
    private List<Evento> e_eventos_full;


    public ImageAdapter(Context context, List<Evento> eventos) {

        e_context = context;
        e_eventos = eventos;
        e_eventos_full = new ArrayList<>(eventos);

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(e_context).inflate(R.layout.imagem_evento, parent, false);
        return new ImageViewHolder(v);

    }

    //metodo que pega os items da classe do evento e vai formando a recycleview

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        //coloca os atributos no banco alocado em cada parte da imagem_evento da RecycleView

        Evento eventoatual = e_eventos.get(position);
        holder.txtTituloEvento.setText(" Título do Evento: " + eventoatual.getTituloEvento());
        holder.txtDescricaoEvento.setText(" Descrição do Evento: " + eventoatual.getDescricaoEvento());
        holder.txtPeriodoEvento.setText(" Período do evento: " + eventoatual.getPeriodoEvento());
        holder.txtEventoOrganizador.setText(" Organizador do evento: " + eventoatual.getOrganizadorEvento());

        //recupera a imagem do banco para lista-lá
        Picasso.with(e_context).load(eventoatual.getImagemEventoUrl()).fit().centerCrop()
                .into(holder.mfotoevento);

        holder.txtEstado.setText("Estado: " + eventoatual.getEstadoEvento());
        holder.txtCidade.setText("Cidade: " + eventoatual.getCidadeEvento());

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

            List<Evento> eventosfiltrados = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {

                eventosfiltrados.addAll(e_eventos_full);

            } else {

                String resultado = constraint.toString().toLowerCase().trim();

                for (Evento item : e_eventos_full) {

                    if (item.getTituloEvento().toLowerCase().contains(resultado) || item.getDescricaoEvento().toLowerCase().contains(resultado)
                            || item.getOrganizadorEvento().toLowerCase().contains(resultado) || item.getPeriodoEvento().toLowerCase().contains(resultado) ||
                            item.getEstadoEvento().toLowerCase().contains(resultado) || item.getCidadeEvento().toLowerCase().contains(resultado)) {

                        eventosfiltrados.add(item);

                    }
                }
            }

            FilterResults filtra_resultados = new FilterResults();
            filtra_resultados.values = eventosfiltrados;

            return filtra_resultados;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            e_eventos.clear();
            e_eventos.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    //metodo que pega o id dos items da lista recycleview

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        //variaveis que irão ser listadas através do que já está registrado no BD

        public TextView txtTituloEvento;
        public TextView txtDescricaoEvento;
        public TextView txtPeriodoEvento;
        public TextView txtEventoOrganizador;
        public ImageView mfotoevento;
        public TextView txtEstado;
        public TextView txtCidade;


        //construtor da classe ImageViewHolder

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            //recupera o id dos items do cardview (no imagem_evento.xml)
            // para formar os itens que serão listados

            txtTituloEvento = itemView.findViewById(R.id.titulo_evento);
            txtDescricaoEvento = itemView.findViewById(R.id.descricao_evento);
            txtPeriodoEvento = itemView.findViewById(R.id.periodo_evento);
            txtEventoOrganizador = itemView.findViewById(R.id.organizador_evento);
            mfotoevento = itemView.findViewById(R.id.foto_evento_upload);
            txtEstado = itemView.findViewById(R.id.estado_evento);
            txtCidade = itemView.findViewById(R.id.cidade_evento);


        }
    }

}
