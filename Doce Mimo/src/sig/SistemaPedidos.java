package sig;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class SistemaPedidos {

    private static final String PEDIDOS_DIR = "sig/pedidos/";// Diretório para salvar os pedidos
    private static int idPedidoAtual = 1;
    private static final List<Pedido> pedidos = new ArrayList<>();

    public static void main(String[] args) {
        SistemaPedidos sistema = new SistemaPedidos();
        sistema.inicializarSistema();

        Scanner scanner = new Scanner(System.in);
        char opcao;

        do {
            opcao = sistema.menu();
            switch (opcao) {
                case '1':
                    sistema.adicionarPedido();
                    break;
                case '2':
                    sistema.listarPedidos();
                    break;
                case '3':
                    sistema.editarPedido();
                    break;

                case '4':
                    sistema.removerPedido();
                    break;
                case '5':
                    sistema.mostrarEstatisticasBrigadeiros(); // Estatísticas de brigadeiros
                    break;
                case '6':
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != '6');

        scanner.close();
    }

    private void inicializarSistema() {
        File dir = new File(PEDIDOS_DIR);
        // Verifica se o diretório de pedidos existe
        if (!dir.exists()) {
            dir.mkdirs();// Cria o diretório
        } else {
            idPedidoAtual = 1;
        }
    }

    private char menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nSistema de Gerenciamento de Pedidos - Doce Mimo");
        System.out.println("1 - Adicionar Pedido");
        System.out.println("2 - Listar Pedidos");
        System.out.println("3 - Atualizar pedido");
        System.out.println("4 - Remover Pedido");
        System.out.println("5 - Mostrar Estatísticas de Brigadeiros");
        System.out.println("6 - Sair");
        System.out.print("Escolha uma opção: ");
        return scanner.next().charAt(0);
    }

    private void adicionarPedido() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Novo Pedido");

        Cliente cliente = new Cliente();
        System.out.print("Nome do Cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.print("Endereço do Cliente: ");
        cliente.setEndereco(scanner.nextLine());
        System.out.print("Email do Cliente: ");
        cliente.setEmail(scanner.nextLine());
        System.out.print("Telefone do Cliente: ");
        cliente.setTelefone(scanner.nextLine());

        Produto produto = new Produto();
        System.out.print("Descrição do Produto: ");
        produto.setDescricao(scanner.nextLine());

        Pedido pedido = new Pedido();
        pedido.setId(idPedidoAtual);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setStatusPedido("Em Produção");
        System.out.print("Forma de Pagamento: ");
        pedido.setFormaPagamento(scanner.nextLine());

        System.out.print("O pagamento foi realizado? (S/N): ");
        pedido.setPago(scanner.nextLine().equalsIgnoreCase("S"));

        pedidos.add(pedido);// Adiciona o pedido à lista
        salvarPedido(pedido);
        idPedidoAtual++;
       
        System.out.println("Pedido adicionado com sucesso!");
    }

    private void listarPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }

        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }

    private void editarPedido(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID do Pedido a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = buscarPedidoPorId(id);
        // Verifica se o pedido foi encontrado
        if (pedido != null) {
            System.out.println("\nEditando o Pedido #" + id);
            System.out.println("Nome Atual: " + pedido.getCliente().getNome());
            System.out.print("Novo Nome (pressione ENTER para manter): ");
            String novoNome = scanner.nextLine();
            // Atualiza o nome do cliente se um novo nome for fornecido
            if (!novoNome.isEmpty()) {
                pedido.getCliente().setNome(novoNome);
            }
    
            System.out.println("Descrição Atual: " + pedido.getProduto().getDescricao());
            System.out.print("Nova Descrição (pressione ENTER para manter): ");
            String novaDescricao = scanner.nextLine();
            if (!novaDescricao.isEmpty()) {
                pedido.getProduto().setDescricao(novaDescricao);
            }

            System.out.println("Status Atual do Pedido: " + pedido.getStatusPedido());
            System.out.print("Novo Status (pressione ENTER para manter): ");
            String novoStatus = scanner.nextLine();
            if (!novoStatus.isEmpty()) {
                pedido.setStatusPedido(novoStatus);
            }
    
            System.out.println("Endereço Atual: " + pedido.getCliente().getEndereco());
            System.out.print("Novo Endereço (pressione ENTER para manter): ");
            String novoEndereco = scanner.nextLine();
            if (!novoEndereco.isEmpty()) {
                pedido.getCliente().setEndereco(novoEndereco);
            }
    
            System.out.println("Forma de Pagamento Atual: " + pedido.getFormaPagamento());
            System.out.print("Nova Forma de Pagamento (pressione ENTER para manter): ");
            String novaFormaPagamento = scanner.nextLine();
            if (!novaFormaPagamento.isEmpty()) {
                pedido.setFormaPagamento(novaFormaPagamento);
            }
    
            System.out.println("Status Atual do Pedido: " + pedido.getStatusPedido());
            System.out.print("Novo Status (pressione ENTER para manter): ");
            String novoStatus = scanner.nextLine();
            if (!novoStatus.isEmpty()) {
                pedido.setStatusPedido(novoStatus);
            }
    
            System.out.println("\nPedido atualizado com sucesso!");
        } else {
            System.out.println("\nPedido não encontrado.");
        }
    }

    private Pedido buscarPedidoPorId(int id) {
        for (Pedido pedido : pedidos) {  
            if (pedido.getId() == id) {// Verifica se o ID do pedido corresponde ao ID fornecido    
                return pedido;
            }
        }
        return null; // Retorna null se o pedido não for encontrado
    }

    private void removerPedido() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("ID do Pedido a ser removido: ");
        int id = scanner.nextInt();

       // pedidos.removeIf(pedido -> pedido.getId() == id);// Remove o pedido da lista
        File arquivo = new File(PEDIDOS_DIR + id + ".txt");// Cria o arquivo do pedido
        // Verifica se o arquivo existe e se foi removido
        if (arquivo.exists() && arquivo.delete()) {
            System.out.println("Pedido removido com sucesso!");
        } else {
            System.out.println("Erro ao remover o arquivo do pedido.");
        }
    }

    private void mostrarEstatisticasBrigadeiros() {
        // Contadores de brigadeiros vendidos
        int totalBrigadeiros = 0;
        int chocolateBranco = 0;
        int cafe = 0;
        int leiteNinho = 0;

        // Obtém o mês atual
        int mesAtual = LocalDate.now().getMonthValue();

        for (Pedido pedido : pedidos) {
            String descricao = pedido.getProduto().getDescricao().toLowerCase();
            
            // Filtra pedidos de brigadeiro e conta os sabores
            if (descricao.contains("brigadeiro")) {
                totalBrigadeiros++;

                if (descricao.contains("chocolate branco")) {
                    chocolateBranco++;
                } else if (descricao.contains("café")) {
                    cafe++;
                } else if (descricao.contains("leite ninho")) {
                    leiteNinho++;
                }
            }
        }

        // Exibe estatísticas
        System.out.println("\nEstatísticas de Vendas de Brigadeiros:");
        System.out.println("Total de brigadeiros vendidos neste mês: " + totalBrigadeiros);
        System.out.println("Chocolate Branco: " + chocolateBranco);
        System.out.println("Café: " + cafe);
        System.out.println("Leite Ninho: " + leiteNinho);

        // Determina o sabor mais vendido
        String saborMaisVendido;
        if (chocolateBranco >= cafe && chocolateBranco >= leiteNinho) {
            saborMaisVendido = "Chocolate Branco";
        } else if (cafe >= chocolateBranco && cafe >= leiteNinho) {
            saborMaisVendido = "Cafe";
        } else {
            saborMaisVendido = "Leite Ninho";
        }

        System.out.println("Sabor mais vendido: " + saborMaisVendido);
    }

    private void salvarPedido(Pedido pedido) {
        // Salva o pedido em um arquivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PEDIDOS_DIR + pedido.getId() + ".txt"))) {
            int mesAtual = LocalDate.now().getDayOfMonth();
            writer.write("Mês: " + mesAtual + "\n");
            writer.write("ID: " + pedido.getId() + "\n");
            writer.write("Nome: " + pedido.getCliente().getNome() + "\n");
            writer.write("Endereço: " + pedido.getCliente().getEndereco() + "\n");
            writer.write("Descrição: " + pedido.getProduto().getDescricao() + "\n");
            writer.write("Email: " + pedido.getCliente().getEmail() + "\n");
            writer.write("Telefone: " + pedido.getCliente().getTelefone() + "\n");
            writer.write("Pagamento: " + pedido.getFormaPagamento() + "\n");
            writer.write(pedido.isPago() ? "Pago\n" : "Não Pago\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o pedido: " + e.getMessage());
        }
    }   
}
