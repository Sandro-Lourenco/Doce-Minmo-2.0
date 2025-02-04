package sig;

public class Cliente {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String endereco;

    // Métodos para ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Métodos para Nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        // Verifica se o nome é nulo ou vazio
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome inválido.");
            
        }else{
            // Converte a primeira letra do nome para maiúscula e o restante para minúscula
            this.nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
        } 
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Métodos para Endereço
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Cliente [ID: " + id + ", Nome: " + nome + ", Email: " + email + "Telefone" + telefone + ", Endereço: " + endereco + "]";
    }
}
