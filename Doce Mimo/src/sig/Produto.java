package sig;

public class Produto {
    private String descricao;

    // Métodos para Descrição do Produto
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Produto [Descrição: " + descricao + "]";
    }
}
