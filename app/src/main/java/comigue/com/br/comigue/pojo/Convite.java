package comigue.com.br.comigue.pojo;

import java.io.Serializable;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Convite implements Serializable {

    private long codConvite;
    private boolean status;
    private Usuario usuario;
    private Materia materia;

    public Convite() {
        super();
    }



    public Convite(boolean status, Usuario usuario, Materia materia) {
        super();
        this.status = status;
        this.usuario = usuario;
        this.materia = materia;
    }



    public long getCodConvite() {
        return codConvite;
    }

    public void setCodConvite(long codConvite) {
        this.codConvite = codConvite;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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