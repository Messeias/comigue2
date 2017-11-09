package comigue.com.br.comigue.pojo;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alunoinfo on 10/10/17.
 */


public class Horario implements Serializable {
    private long codHorario;

    private Date hora;
    private Materia materia;

    public Horario() {
        super();
    }

    public Horario(Date hora, Materia materia) {
        super();
        this.hora = hora;
        this.materia = materia;
    }

    public long getCodHorario() {
        return codHorario;
    }

    public void setCodHorario(long codHorario) {
        this.codHorario = codHorario;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }


}