package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guilherme.rsvieira
 */
@XmlRootElement
public class Usuario {
    @XmlElement private int codigo;
    @XmlElement private String nome;
    @XmlElement private String email;
    @XmlElement private String senha;
    @XmlElement private int setor;
    @XmlElement private String nomeSetor;
    @XmlElement private String CPF;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha, int setor) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
    }

    public Usuario(int codigo, String nome, String email, String senha, int setor) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
    }
    
    public Usuario(int codigo, String nome, String email, String nomeSetor, String CPF){
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.nomeSetor = nomeSetor;
        this.CPF = CPF;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public int getSetor() {
        return setor;
    }

    public void setSetor(int setor) {
        this.setor = setor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
}
