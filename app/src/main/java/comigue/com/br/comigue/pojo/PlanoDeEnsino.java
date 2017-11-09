package comigue.com.br.comigue.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class PlanoDeEnsino implements Serializable {

    private long codPlanoDeEnsino;
    private String professor;
    private List<Assunto> assuntos;
    private Materia materia;

    public PlanoDeEnsino() {
        super();
    }

    public PlanoDeEnsino(String professor, List<Assunto> assuntos) {
        super();
        this.professor = professor;
        this.assuntos = assuntos;
    }

    public long getCodPlanoDeEnsino() {
        return codPlanoDeEnsino;
    }

    public void setCodPlanoDeEnsino(long codPlanoDeEnsino) {
        this.codPlanoDeEnsino = codPlanoDeEnsino;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

}