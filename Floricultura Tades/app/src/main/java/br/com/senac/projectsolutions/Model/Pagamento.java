package br.com.senac.projectsolutions.Model;

public class Pagamento {
    int id;
    String tipoPagamento;
    String numeroPagamento;
    String nomeTitular;
    String dataVencimento;
    String codigoSegurança;
    int idInfoPagamento;
    int idUsuario;

    public Pagamento() {}

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
