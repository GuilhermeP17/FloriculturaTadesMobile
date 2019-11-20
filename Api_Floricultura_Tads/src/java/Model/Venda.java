package Model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Venda {

    @XmlElement private int idVenda;
    @XmlElement private String codigoVenda;
    @XmlElement private int quantidadeItens;
    @XmlElement private double valorFrete;
    @XmlElement private double valorTotal;
    @XmlElement private String data;
    @XmlElement private int idEndereco;
    @XmlElement private int codigoUsuario;
    @XmlElement private int idStatus;
    @XmlElement private int idPagamento;
    
    @XmlElement private String status;
    @XmlElement private String tipoPagamento;
    @XmlElement private String codigoPagamento;
    
    @XmlElement private ArrayList<Produto> produtos;

    public Venda() {
    }

    public Venda(int idVenda, String codigoVenda, int quantidadeItens, double valorFrete, double valorTotal, String data, int idEndereco, int codigoUsuario, int idStatus, int idPagamento) {
        this.idVenda = idVenda;
        this.codigoVenda = codigoVenda;
        this.quantidadeItens = quantidadeItens;
        this.valorFrete = valorFrete;
        this.valorTotal = valorTotal;
        this.data = data;
        this.idEndereco = idEndereco;
        this.codigoUsuario = codigoUsuario;
        this.idStatus = idStatus;
        this.idPagamento = idPagamento;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
    }

    public String getCodigoVenda() {
        return codigoVenda;
    }

    public void setCodigoVenda(String codigoVenda) {
        this.codigoVenda = codigoVenda;
    }

    public int getQuantidadeItens() {
        return quantidadeItens;
    }

    public void setQuantidadeItens(int quantidadeItens) {
        this.quantidadeItens = quantidadeItens;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
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

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(int idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

}
