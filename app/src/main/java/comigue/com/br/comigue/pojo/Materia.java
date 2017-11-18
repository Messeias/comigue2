package comigue.com.br.comigue.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Materia implements Serializable {

    private long codMateria;
    private String nome;
    private List<Horario> horarios;
    private String descricao;
    private boolean publico;
    private Anotacao anotacoes;
    private List<Convite> convites;
    private List<Tarefa> tarefas;
    private PlanoDeEnsino planoDeEnsino;

    public Materia() {
        super();
    }

    public Materia(String nome, List<Horario> horarios, String descricao, boolean publico, Anotacao anotacoes,
                   List<Convite> convites, List<Tarefa> tarefas) {
        super();
        this.nome = nome;
        this.horarios = horarios;
        this.descricao = descricao;
        this.publico = publico;
        this.anotacoes = anotacoes;
        this.convites = convites;
        this.tarefas = tarefas;
    }



    public long getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(long codMateria) {
        this.codMateria = codMateria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public Anotacao getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(Anotacao anotacoes) {
        this.anotacoes = anotacoes;
    }

    public List<Convite> getConvites() {
        return convites;
    }

    public void setConvites(List<Convite> convites) {
        this.convites = convites;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }


    @Override
    public String toString(){
        return this.getNome();
    }


    public PlanoDeEnsino getPlanoDeEnsino() {
        return planoDeEnsino;
    }

    public void setPlanoDeEnsino(PlanoDeEnsino planoDeEnsino) {
        this.planoDeEnsino = planoDeEnsino;
    }
}