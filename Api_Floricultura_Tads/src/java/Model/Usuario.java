package Model;

/**
 *
 * @author guilherme.rsvieira
 */
public class Usuario {

    private int codigo;
    private String nome;
    private String email;
    private String senha;
    private int setor;
    private String nomeSetor;

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
    
    public Usuario(int codigo, String nome, String email, String senha, String nomeSetor){
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nomeSetor = nomeSetor;
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
}
