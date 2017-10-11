package comigue.com.br.comigue.pojo;

import java.util.Date;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Tarefa {

    private long codTarefa;
    private String nome;
    private char etiqueta;
    private String descricao;
    private Date dataEntrega;
    private double peso;
    private Usuario usuario;
    private Materia materia;

    public Tarefa() {
        super();
    }




    public Tarefa(String nome, char etiqueta, String descricao, Date dataEntrega, double peso, Usuario usuario,
                  Materia materia) {
        super();
        this.nome = nome;
        this.etiqueta = etiqueta;
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.peso = peso;
        this.usuario = usuario;
        this.materia = materia;
    }




    public long getCodTarefa() {
        return codTarefa;
    }

    public void setCodTarefa(long codTarefa) {
        this.codTarefa = codTarefa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public char getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(char etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }




    public Materia getMateria() {
        return materia;
    }




    public void setMateria(Materia materia) {
        this.materia = materia;
    }



}