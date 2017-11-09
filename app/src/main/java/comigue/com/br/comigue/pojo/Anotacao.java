package comigue.com.br.comigue.pojo;

import java.io.Serializable;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Anotacao implements Serializable {

    private long codAnotacao;
    private String titulo;
    private String texto;
    private byte[] midia;
    private Usuario usuario;
    private Materia materia;

    public Anotacao() {
        super();
    }




    public Anotacao(String titulo, String texto, byte[] midia, Usuario usuario, Materia materia) {
        super();
        this.titulo = titulo;
        this.texto = texto;
        this.midia = midia;
        this.usuario = usuario;
        this.materia = materia;
    }




    public long getCodAnotacao() {
        return codAnotacao;
    }

    public void setCodAnotacao(long codAnotacao) {
        this.codAnotacao = codAnotacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getMidia() {
        return midia;
    }

    public void setMidia(byte[] midia) {
        this.midia = midia;
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