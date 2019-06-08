package com.example.ieventbe.Classes;

import com.google.firebase.database.Exclude;

public class Evento {

      private String TituloEvento;
      private String DescricaoEvento;
      private String PeriodoEvento;
      private String OrganizadorEvento;
      private String ImagemEventoUrl;
      private String EstadoEvento;
      private String CidadeEvento;
      private String Id;

      public Evento(){

          //classe construtora
      }


      //Metodo para pegar todos os atributos

      public Evento(String titEvento, String desEvento, String perEvento, String orgEvento,
                    String imgEventoUrl, String  estEvento, String cidEvento){

          if(titEvento.trim().equals("")){

              TituloEvento = "Sem título para o evento";

          } else if(desEvento.trim().equals("")){

              DescricaoEvento = "Evento sem descrição";

          } else if(perEvento.trim().equals("")){

              PeriodoEvento = "Evento sem período";

          } else if(orgEvento.trim().equals("")){

              OrganizadorEvento = "Evento sem organizador";
          }

          TituloEvento = titEvento;
          DescricaoEvento = desEvento;
          PeriodoEvento = perEvento;
          OrganizadorEvento = orgEvento;
          ImagemEventoUrl = imgEventoUrl;
          EstadoEvento = estEvento;
          CidadeEvento = cidEvento;

      }

      //metodos getters and setters da classe evento

    public String getTituloEvento() {
        return TituloEvento;
    }

    public void setTituloEvento(String titEvento) {
        TituloEvento = titEvento;
    }

    public String getDescricaoEvento() {
        return DescricaoEvento;
    }

    public void setDescricaoEvento(String desEvento) {
        DescricaoEvento = desEvento;
    }


    public String getPeriodoEvento() {
        return PeriodoEvento;
    }

    public void setPeriodoEvento(String perEvento) {
        PeriodoEvento = perEvento;
    }

    public String getOrganizadorEvento() {
        return OrganizadorEvento;
    }

    public void setOrganizadorEvento(String orgEvento) {
        OrganizadorEvento = orgEvento;
    }

    public String getImagemEventoUrl() {
        return ImagemEventoUrl;
    }

    public void setImagemEventoUrl(String imgEventoUrl) {
        ImagemEventoUrl = imgEventoUrl;
    }

    public String getEstadoEvento() {
        return EstadoEvento;
    }

    public void setEstadoEvento(String estEvento) {
        EstadoEvento = estEvento;
    }

    public String getCidadeEvento() {
        return CidadeEvento;
    }

    public void setCidadeEvento(String cidEvento) {
        CidadeEvento = cidEvento;
    }

    @Exclude
    public String getId() {
        return Id;
    }

    @Exclude
    public void setId(String id) {
        Id = id;
    }
}
