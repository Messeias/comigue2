package comigue.com.br.comigue.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by alunoinfo on 10/10/17.
 */

public class Usuario implements Serializable{

    /**
     *
     */

    private long codUsuario;
    private String celular;
    private String email;
    private String senha;
    private String nome;
    private List<Convite> convites;
    private List<Anotacao> anotacoes;
    private List<Tarefa> tarefas;

    public Usuario() {
        super();
    }

    public Usuario(String celular, String email, String senha, String nome, List<Convite> convites,
                   List<Anotacao> anotacoes, List<Tarefa> tarefas) {
        super();
        this.celular = celular;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.convites = convites;
        this.anotacoes = anotacoes;
        this.tarefas = tarefas;
    }


    public long getCodUsuario() {
        return codUsuario;
    }
    public void setCodUsuario(long codUsuario) {
        this.codUsuario = codUsuario;
    }
    public String getCelular() {
        return celular;
    }
    public void setCelular(String celular) {
        this.celular = celular;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }


    public List<Convite> getConvites() {
        return convites;
    }

    public void setConvites(List<Convite> convites) {
        this.convites = convites;
    }

    public List<Anotacao> getAnotacoes() {
        return anotacoes;
    }

    public void setAnotacoes(List<Anotacao> anotacoes) {
        this.anotacoes = anotacoes;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}