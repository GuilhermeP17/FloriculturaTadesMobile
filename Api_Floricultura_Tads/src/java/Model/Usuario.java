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
    @XmlElement private String logradouro;
    @XmlElement private int numero;
    @XmlElement private String bairro;
    @XmlElement private String cidade;
    @XmlElement private String estado;
    @XmlElement private String cep;
    @XmlElement private String tipoEndereco;

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

    public Usuario(int codigo, String nome, String email, String senha, int setor, String nomeSetor, String CPF, String logradouro, int numero, String bairro, String cidade, String estado, String cep, String tipoEndereco) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.setor = setor;
        this.nomeSetor = nomeSetor;
        this.CPF = CPF;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.tipoEndereco = tipoEndereco;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoEndereco() {
        return tipoEndereco;
    }

    public void setTipoEndereco(String tipoEndereco) {
        this.tipoEndereco = tipoEndereco;
    }
    
    
}
