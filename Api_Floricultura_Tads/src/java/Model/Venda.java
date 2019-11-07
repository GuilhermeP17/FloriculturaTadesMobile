package Model;

import java.util.ArrayList;

public class Venda {
    private int codigoVenda;
    private int codigoUsuario;
    private int quantidadeItens;
    private double valorTotal;
    private String data;
    private String status;
    private ArrayList<Produto> produtos;
    
    public Venda() {
    }

    public Venda(int codigoVenda, int codigoUsuario, int quantidadeItens, double valorTotal, String data, String status, ArrayList<Produto> produtos) {
        this.codigoVenda = codigoVenda;
        this.codigoUsuario = codigoUsuario;
        this.quantidadeItens = quantidadeItens;
        this.valorTotal = valorTotal;
        this.data = data;
        this.status = status;
        this.produtos = produtos;
    }

    public int getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(int codigoVenda) {
        this.codigoVenda = codigoVenda;
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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }      
   
}
