package com.example.ieventbe.Classes;

public class SubEvento {

       private String sub_responsavel;
       private String sub_local_atividades;
       private String sub_minicurriculo;
       private String sub_descricacao;
       private String sub_periodo;
       private String sub_foto;
       private String sub_tipo;

    public SubEvento() {

        //Método construtor
    }

    //metodo pega todos itens da classe e salva no firebase

    public SubEvento(String res, String lca, String minic, String desc, String per, String foto,
                     String tipo){

        if(res.trim().equals("")){

            res = "Sub evento sem responsável";

        }

        if(lca.trim().equals("")){

            lca = "Evento sem local";

        }

        if(minic.trim().equals("")){

            minic = "Sem minicorriculo";

        }

        if(desc.trim().equals("")){

            desc = "Sub evento sem descricao";

        }

        if(per.trim().equals("")){

            per = "Evento sem periodo";
        }

            sub_responsavel = res;
            sub_local_atividades = lca;
            sub_minicurriculo = minic;
            sub_descricacao = desc;
            sub_periodo = per;
            sub_foto = foto;
            sub_tipo = tipo;

    }

    public String getSub_responsavel() {
        return sub_responsavel;
    }

    public void setSub_responsavel(String sub_responsavel) {
        this.sub_responsavel = sub_responsavel;
    }

    public String getSub_local_atividades() {
        return sub_local_atividades;
    }

    public void setSub_local_atividades(String sub_local_atividades) {
        this.sub_local_atividades = sub_local_atividades;
    }

    public String getSub_minicorriculo() {
        return sub_minicurriculo;
    }

    public void setSub_minicorriculo(String sub_minicorriculo) {
        this.sub_minicurriculo = sub_minicorriculo;
    }

    public String getSub_descricacao() {
        return sub_descricacao;
    }

    public void setSub_descricacao(String sub_descricacao) {
        this.sub_descricacao = sub_descricacao;
    }

    public String getSub_periodo() {
        return sub_periodo;
    }

    public void setSub_periodo(String sub_periodo) {
        this.sub_periodo = sub_periodo;
    }

    public String getSub_foto() {
        return sub_foto;
    }

    public void setSub_foto(String sub_foto) {
        this.sub_foto = sub_foto;
    }

    public String getSub_tipo() {
        return sub_tipo;
    }

    public void setSub_tipo(String sub_tipo) {
        this.sub_tipo = sub_tipo;
    }
}
