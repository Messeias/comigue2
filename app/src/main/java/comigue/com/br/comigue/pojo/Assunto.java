package comigue.com.br.comigue.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Assunto implements Serializable {
    private long codAssunto;
    private String descricao;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private PlanoDeEnsino planoDeEnsino;



    public Assunto() {
        super();
    }

    public Assunto(String descricao, String nome, Date dataInicio, Date dataFim, PlanoDeEnsino planoDeEnsino) {
        super();
        this.descricao = descricao;
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.planoDeEnsino = planoDeEnsino;
    }

    public long getCodAssunto() {
        return codAssunto;
    }

    public void setCodAssunto(long codAssunto) {
        this.codAssunto = codAssunto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public PlanoDeEnsino getPlanoDeEnsino() {
        return planoDeEnsino;
    }

    public void setPlanoDeEnsino(PlanoDeEnsino planoDeEnsino) {
        this.planoDeEnsino = planoDeEnsino;
    }

    @Override
    public boolean equals(Object o){
        Assunto ass = (Assunto) o;
        return  this.getNome().equals(ass.getNome()) && this.getDescricao() == ass.getDescricao();
    }

}