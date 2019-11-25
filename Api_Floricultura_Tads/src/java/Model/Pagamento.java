/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author guilherme.pereira
 */

@XmlRootElement
public class Pagamento {

    @XmlElement int id;
    @XmlElement String tipoPagamento;
    @XmlElement String numeroPagamento;
    @XmlElement String nomeTitular;
    @XmlElement String dataVencimento;
    @XmlElement String codigoSegurança;
    @XmlElement int idInfoPagamento;
    @XmlElement int idUsuario;

    public Pagamento() {
    }

    public Pagamento(int id, String tipoPagamento) {
        this.id = id;
        this.tipoPagamento = tipoPagamento;
    }

    public Pagamento(String numeroPagamento, int idInfoPagamento, int idUsuario) {
        this.numeroPagamento = numeroPagamento;
        this.idInfoPagamento = idInfoPagamento;
        this.idUsuario = idUsuario;
    }

    public Pagamento(int id, String tipoPagamento, String numeroPagamento, String nomeTitular, String dataVencimento, String codigoSegurança) {
        this.id = id;
        this.tipoPagamento = tipoPagamento;
        this.numeroPagamento = numeroPagamento;
        this.nomeTitular = nomeTitular;
        this.dataVencimento = dataVencimento;
        this.codigoSegurança = codigoSegurança;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getNumeroPagamento() {
        return numeroPagamento;
    }

    public void setNumeroPagamento(String numeroPagamento) {
        this.numeroPagamento = numeroPagamento;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getCodigoSegurança() {
        return codigoSegurança;
    }

    public void setCodigoSegurança(String codigoSegurança) {
        this.codigoSegurança = codigoSegurança;
    }

    public int getIdInfoPagamento() {
        return idInfoPagamento;
    }

    public void setIdInfoPagamento(int idInfoPagamento) {
        this.idInfoPagamento = idInfoPagamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

}
