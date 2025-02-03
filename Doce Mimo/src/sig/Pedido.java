package sig;

public class Pedido {
    private int id;
    private Cliente cliente;
    private Produto produto;
    private String formaPagamento;
    private String statusPedido;
    private boolean pago;

    // Métodos para ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos para Cliente
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // Métodos para Produto
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    // Métodos para Forma de Pagamento
    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    // Métodos para Status do Pedido
    public String getStatusPedido() {
        return statusPedido;
    }

    private void atualizarStatus() {
        this.statusPedido = pago ? "Finalizado" : "Pendente";
    }

    // Métodos para Pagamento
    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
        atualizarStatus();
    }

    @Override
    public String toString() {
        return "Pedido [ID: " + id + ", Cliente: " + cliente + ", Produto: " + produto + 
               ", Forma de Pagamento: " + formaPagamento + ", Status: " + statusPedido + "]";
    }

    public void setEndereco(String novoEndereco) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEndereco'");
    }

    public void setStatusPedido(String novoStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStatusPedido'");
    }
}
