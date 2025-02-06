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
            
            idPedidoAtual = carregarUltimoId(); // Carrega o último ID usado
            carregarPedidos(); // Carrega os pedidos do diretório
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
        System.out.println("\nNovo Pedido");
    
        // Criar um novo cliente
        Cliente cliente = new Cliente();
        System.out.print("Nome do Cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.print("Endereço do Cliente: ");
        cliente.setEndereco(scanner.nextLine());
        System.out.print("Email do Cliente: ");
        cliente.setEmail(scanner.nextLine());
        System.out.print("Telefone do Cliente: ");
        cliente.setTelefone(scanner.nextLine());
    
        // Criar um novo produto
        Produto produto = new Produto();
        System.out.print("Sabor do Brigadeiro (Chocolate Branco, Café, Leite Ninho): ");
        produto.setDescricao(scanner.nextLine().toLowerCase()); 
    
        System.out.print("Quantidade de Brigadeiros: ");
        int quantidade = scanner.nextInt();
        // Calcula o valor total do pedido
        double valorTotal = quantidade * produto.getPRECO(); 
    
        // Criar um novo pedido
        Pedido pedido = new Pedido();
        pedido.setId(idPedidoAtual);
        pedido.setCliente(cliente);
        pedido.setProduto(produto);
        pedido.setStatusPedido("Em Produção");
        pedido.setValorTotal(valorTotal);
        
        System.out.print("Forma de Pagamento: ");
        pedido.setFormaPagamento(scanner.nextLine());
    
        System.out.print("O pagamento foi realizado? (S/N): ");
        pedido.setPago(scanner.nextLine().equalsIgnoreCase("S"));
    
        // Adicionar o pedido à lista e salvar no arquivo
        salvarPedido(pedido);
        
        // 🔹 Agora salvamos o último ID usado no sistema!
        idPedidoAtual++;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sig/ultimoId.txt"))) {
            writer.write(String.valueOf(idPedidoAtual));
        } catch (IOException e) {
            System.out.println("Erro ao salvar o ID: " + e.getMessage());
        }
    
        System.out.println("\nPedido adicionado com sucesso! Pedido #" + pedido.getId());
    }

    private void listarPedidos() {
        File dir = new File(PEDIDOS_DIR);
        String[] arquivos = dir.list();
    
        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
    
        System.out.println("\n📜 Lista de Pedidos:");
        for (String arquivo : arquivos) {
            Pedido pedido = carregarPedido(arquivo);
            if (pedido != null) {
                System.out.println(pedido);
            }
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
    
        // Buscar o pedido na lista
        Pedido pedidoParaRemover = null;
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                pedidoParaRemover = pedido;
                break;
            }
        }

        //buscarPedidoPorId(id);
    
        // Se o pedido foi encontrado, remover da lista
        if (pedidoParaRemover != null) {
            pedidos.remove(pedidoParaRemover);
            System.out.println("Pedido removido da lista.");
        } else {
            System.out.println("Pedido não encontrado na lista.");
        }
    
        // Remover o arquivo do pedido
        File arquivo = new File(PEDIDOS_DIR + id + ".txt");
        if (arquivo.exists() && arquivo.delete()) {
            System.out.println("Arquivo do pedido removido com sucesso!");
        } else {
            System.out.println("⚠ Erro ao remover o arquivo do pedido.");
        }
    }


    private void mostrarEstatisticasBrigadeiros() {
        int totalBrigadeiros = 0;
        int chocolateBranco = 0, cafe = 0, leiteNinho = 0;
        double faturamentoTotal = 0;
    
        for (Pedido pedido : pedidos) {
            String descricao = pedido.getProduto().getDescricao().toLowerCase();
            int quantidade = (int) (pedido.getValorTotal() / 1.50); // 🔹 Calcula a quantidade com base no valor total
            
            totalBrigadeiros += quantidade;
            faturamentoTotal += pedido.getValorTotal();
    
            if (descricao.contains("chocolate branco")) {
                chocolateBranco += quantidade;
            } else if (descricao.contains("cafe")) {
                cafe += quantidade;
            } else if (descricao.contains("leite ninho")) {
                leiteNinho += quantidade;
            }
        }
    
        System.out.println("\nEstatísticas de Vendas de Brigadeiros:");
        System.out.println("🔹 Total de brigadeiros vendidos: " + totalBrigadeiros);
        System.out.printf("🔹 Faturamento Total: R$ %.2f\n", faturamentoTotal);
        System.out.println("🔹 Brigadeiros por Sabor:");
        System.out.println("   - Chocolate Branco: " + chocolateBranco);
        System.out.println("   - Café: " + cafe);
        System.out.println("   - Leite Ninho: " + leiteNinho);
    }
    
    // Salva o pedido em um arquivo de texto
    private void salvarPedido(Pedido pedido) {
        // Salva o pedido em um arquivo de texto
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PEDIDOS_DIR + pedido.getId() + ".txt"))) {
            int mesAtual = LocalDate.now().getDayOfMonth();
           // writer.write("Mês: " + mesAtual + "\n");
            writer.write(pedido.getId() + "\n");
            writer.write(mesAtual + "\n");
            writer.write("Nome: " + pedido.getCliente().getNome() + "\n");
            writer.write("Endereço: " + pedido.getCliente().getEndereco() + "\n");
            writer.write("Descrição: " + pedido.getProduto().getDescricao() + "\n");
            writer.write("Email: " + pedido.getCliente().getEmail() + "\n");
            writer.write("Telefone: " + pedido.getCliente().getTelefone() + "\n");
            writer.write("Pagamento: " + pedido.getFormaPagamento() + "\n");
            writer.write("Status do pedido" + pedido.getStatusPedido() + "\n");
            writer.write(pedido.isPago() ? "Pago\n" : "Não Pago\n");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o pedido: " + e.getMessage());
        }
    }   
    // Carrega o último ID usado no sistema
   private int carregarUltimoId() {
        File arquivo = new File("sig/ultimoId.txt");
    
        // Se o arquivo não existir, retorna 1 
        if (!arquivo.exists()) {
            return 1;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            return Integer.parseInt(reader.readLine()); // Lê o ID salvo no arquivo
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar o ID: " + e.getMessage());
            return 1; //erro
        }
    }
    // Carrega os pedidos salvos no diretório
    private void carregarPedidos() {
        File dir = new File(PEDIDOS_DIR);
        String[] arquivos = dir.list(); // Lista todos os arquivos da pasta
    
        if (arquivos != null) {
            for (String arquivo : arquivos) {
                Pedido pedido = carregarPedido(arquivo); // Lê cada pedido do arquivo
                if (pedido != null) {   // Verifica se o pedido foi carregado com sucesso
                    pedidos.add(pedido); // Adiciona o pedido na lista
                }
            }
        }
    }

    private Pedido carregarPedido(String arquivoNome) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PEDIDOS_DIR + arquivoNome))) {
            int id = Integer.parseInt(reader.readLine());
            String nomeCliente = reader.readLine();
            String enderecoCliente = reader.readLine();
            String emailCliente = reader.readLine();
            String telefoneCliente = reader.readLine();
            String descricaoProduto = reader.readLine();
            String formaPagamento = reader.readLine();
            boolean pago = reader.readLine().equalsIgnoreCase("Pago");
    
            // Criar objetos Cliente e Produto com os dados lidos
            Cliente cliente = new Cliente();
            cliente.setNome(nomeCliente);
            cliente.setEndereco(enderecoCliente);
            cliente.setEmail(emailCliente);
            cliente.setTelefone(telefoneCliente);
    
            Produto produto = new Produto();
            produto.setDescricao(descricaoProduto);
    
            // Criar e configurar o objeto Pedido
            Pedido pedido = new Pedido();
            pedido.setId(id);
            pedido.setCliente(cliente);
            pedido.setProduto(produto);
            pedido.setFormaPagamento(formaPagamento);
            pedido.setPago(pago);
    
            return pedido;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao carregar o pedido: " + e.getMessage());
        }
        return null;
    }
}
