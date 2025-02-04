package sig;

public class Produto {
    private String descricao;
    private final double PRECO = 1.50;

    // Métodos para Descrição do Produto
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPRECO() {
        return PRECO;
    }

    public double calcularPreco(int quantidade) {
        return quantidade * PRECO;
    }

    @Override
    public String toString() {
        return "Produto [Descrição: " + descricao + "]";
    }
}
