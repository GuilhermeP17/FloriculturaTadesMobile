package br.com.senac.projectsolutions.Model;

public class Venda {
    private int codigoVenda;
    private int codigoProd;
    private int codigoUsuario;
    private int quantidadeItens;
    private double precoUnitario;
    private String nomeProduto;
    private String cpfCliente;
    private String data;
    private int status;

    public Venda(){

    }

    public Venda(int codigoProd, int codigoUsuario, int quantidadeItens, String cpfCliente, String data, int status) {
        this.codigoProd = codigoProd;
        this.codigoUsuario = codigoUsuario;
        this.quantidadeItens = quantidadeItens;
        this.cpfCliente = cpfCliente;
        this.data = data;
        this.status = status;
    }

    public Venda(int codigoVenda, int codigoProd, int codigoUsuario, int quantidadeItens, String cpfCliente, String data, int status) {
        this.codigoVenda = codigoVenda;
        this.codigoProd = codigoProd;
        this.codigoUsuario = codigoUsuario;
        this.quantidadeItens = quantidadeItens;
        this.cpfCliente = cpfCliente;
        this.data = data;
        this.status = status;
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public int getCodigoProd() {
        return codigoProd;
    }

    public void setCodigoProd(int codigoProd) {
        this.codigoProd = codigoProd;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
}
