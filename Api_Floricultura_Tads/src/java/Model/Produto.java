package Model;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author guilherme.rsvieira
 */
public class Produto {

    @XmlElement private int codigo;
    @XmlElement private String nome;
    @XmlElement private String descricao;
    @XmlElement private String tipo;
    @XmlElement private int quantidadeEstoque;
    @XmlElement private double valorUnitario;
    @XmlElement private String srcImagem;
    @XmlElement private int codigoVenda;

    public Produto() {
    }

    public Produto(String nome, String tipo, int quantidadeEstoque, double valorUnitario) {
        this.nome = nome;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }

    public Produto(String nome, String descricao, String tipo, int quantidadeEstoque, double valorUnitario) {
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }

    public Produto(int codigo, String nome, String descricao, String tipo, int quantidadeEstoque, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
    }

    public Produto(int codigo, String nome, String descricao, String tipo, int quantidadeEstoque, double valorUnitario, String srcImagem) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.valorUnitario = valorUnitario;
        this.tipo = tipo;
        this.srcImagem = srcImagem;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSrcImagem() {
    	return srcImagem;
    }
    
    public void setSrcImagem(String srcImagem) {
    	this.srcImagem = srcImagem;
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }
    
}
