import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    // Criando os repositórios e o Scanner que serão usados em todo o programa
    private static FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
    private static ProdutoRepository produtoRepository = new ProdutoRepository();
    private static ClienteRepository clienteRepository = new ClienteRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenuPrincipal();
            // Usamos try-catch para evitar que o programa quebre se o usuário digitar texto
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1; // Define uma opção inválida para continuar no loop
            }

            switch (opcao) {
                case 1:
                    gerenciarFuncionarios();
                    break;
                case 2:
                    gerenciarProdutos();
                    break;
                case 3:
                    gerenciarClientes();
                    break;
                case 4:
                    cadastrarCliente(); // Atalho
                    break;
                case 5:
                    cadastrarProduto(); // Atalho
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
        scanner.close(); // Fechar o scanner ao sair do programa para liberar recursos
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1) Gerenciar Funcionários");
        System.out.println("2) Gerenciar Produtos");
        System.out.println("3) Gerenciar Clientes");
        System.out.println("4) Cadastrar Cliente (atalho)");
        System.out.println("5) Cadastrar Produto (atalho)");
        System.out.println("0) Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void exibirSubmenu(String entidade) {
        System.out.println("\n--- GERENCIAR " + entidade.toUpperCase() + " ---");
        System.out.println("1) Cadastrar");
        System.out.println("2) Listar");
        System.out.println("3) Buscar por ID");
        System.out.println("4) Atualizar por ID");
        System.out.println("5) Excluir por ID");
        System.out.println("0) Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }

    // --- MÉTODOS DE GERENCIAMENTO DE FUNCIONÁRIOS ---
    private static void gerenciarFuncionarios() {
        int opcao;
        do {
            exibirSubmenu("Funcionários");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1;
            }

            switch (opcao) {
                case 1: cadastrarFuncionario(); break;
                case 2: listarFuncionarios(); break;
                case 3: buscarFuncionarioPorId(); break;
                case 4: atualizarFuncionario(); break;
                case 5: excluirFuncionario(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void cadastrarFuncionario() {
        System.out.println("\n--- Cadastro de Funcionário ---");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o salário: ");
        double salario;
        try {
            salario = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um salário válido.");
            return;
        }
        System.out.print("Digite a matrícula: ");
        String matricula = scanner.nextLine();

        Funcionario f = new Funcionario(nome, salario, matricula);
        funcionarioRepository.adicionar(f);
        System.out.println("Funcionário cadastrado com sucesso! ID: " + f.getId());
    }

    private static void listarFuncionarios() {
        ArrayList<Funcionario> funcionarios = funcionarioRepository.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }
    
    private static void buscarFuncionarioPorId() {
        System.out.print("Digite o ID do funcionário para buscar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        Funcionario f = funcionarioRepository.buscarPorId(id);
        if (f != null) {
            System.out.println("Funcionário encontrado: " + f);
        } else {
            System.out.println("Funcionário com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário para atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (funcionarioRepository.buscarPorId(id) == null) {
            System.out.println("Funcionário com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo salário: ");
        double salario;
        try {
            salario = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um salário válido.");
            return;
        }
        System.out.print("Digite a nova matrícula: ");
        String matricula = scanner.nextLine();
        
        Funcionario dadosNovos = new Funcionario(nome, salario, matricula);
        if (funcionarioRepository.atualizar(id, dadosNovos)) {
            System.out.println("Funcionário atualizado com sucesso!");
        }
    }
    
    private static void excluirFuncionario() {
        System.out.print("Digite o ID do funcionário para excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (funcionarioRepository.removerPorId(id)) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Funcionário com ID " + id + " não encontrado.");
        }
    }

    // --- MÉTODOS DE GERENCIAMENTO DE PRODUTOS ---
    private static void gerenciarProdutos() {
        int opcao;
        do {
            exibirSubmenu("Produtos");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1;
            }

            switch (opcao) {
                case 1: cadastrarProduto(); break;
                case 2: listarProdutos(); break;
                case 3: buscarProdutoPorId(); break;
                case 4: atualizarProduto(); break;
                case 5: excluirProduto(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço: ");
        double preco;
        try {
            preco = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um preço válido.");
            return;
        }
        System.out.print("Digite a quantidade em estoque: ");
        int qtd;
        try {
            qtd = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite uma quantidade válida.");
            return;
        }

        Produto p = new Produto(nome, preco, qtd);
        produtoRepository.adicionar(p);
        System.out.println("Produto cadastrado com sucesso! ID: " + p.getId());
    }
    
    private static void listarProdutos() {
        ArrayList<Produto> produtos = produtoRepository.listar();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }
    
    private static void buscarProdutoPorId() {
        System.out.print("Digite o ID do produto para buscar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        Produto p = produtoRepository.buscarPorId(id);
        if (p != null) {
            System.out.println("Produto encontrado: " + p);
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarProduto() {
        System.out.print("Digite o ID do produto para atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (produtoRepository.buscarPorId(id) == null) {
            System.out.println("Produto com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo preço: ");
        double preco;
        try {
            preco = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um preço válido.");
            return;
        }
        System.out.print("Digite a nova quantidade em estoque: ");
        int qtd;
        try {
            qtd = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite uma quantidade válida.");
            return;
        }
        
        Produto dadosNovos = new Produto(nome, preco, qtd);
        if (produtoRepository.atualizar(id, dadosNovos)) {
            System.out.println("Produto atualizado com sucesso!");
        }
    }
    
    private static void excluirProduto() {
        System.out.print("Digite o ID do produto para excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (produtoRepository.removerPorId(id)) {
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Produto com ID " + id + " não encontrado.");
        }
    }

    // --- MÉTODOS DE GERENCIAMENTO DE CLIENTES ---
    private static void gerenciarClientes() {
         int opcao;
        do {
            exibirSubmenu("Clientes");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1;
            }
            
            switch (opcao) {
                case 1: cadastrarCliente(); break;
                case 2: listarClientes(); break;
                case 3: buscarClientePorId(); break;
                case 4: atualizarCliente(); break;
                case 5: excluirCliente(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
    
    private static void cadastrarCliente() {
        System.out.println("\n--- Cadastro de Cliente ---");
        System.out.print("Digite o nome do cliente: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        Cliente c = new Cliente(nome, telefone, email);
        clienteRepository.adicionar(c);
        System.out.println("Cliente cadastrado com sucesso! ID: " + c.getId());
    }
    
    private static void listarClientes() {
        ArrayList<Cliente> clientes = clienteRepository.listar();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE CLIENTES ---");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    private static void buscarClientePorId() {
        System.out.print("Digite o ID do cliente para buscar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        Cliente c = clienteRepository.buscarPorId(id);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c);
        } else {
            System.out.println("Cliente com ID " + id + " não encontrado.");
        }
    }

    private static void atualizarCliente() {
        System.out.print("Digite o ID do cliente para atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (clienteRepository.buscarPorId(id) == null) {
            System.out.println("Cliente com ID " + id + " não encontrado.");
            return;
        }
        
        System.out.print("Digite o novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o novo telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Digite o novo email: ");
        String email = scanner.nextLine();
        
        Cliente dadosNovos = new Cliente(nome, telefone, email);
        if (clienteRepository.atualizar(id, dadosNovos)) {
            System.out.println("Cliente atualizado com sucesso!");
        }
    }
    
    private static void excluirCliente() {
        System.out.print("Digite o ID do cliente para excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite um número de ID válido.");
            return;
        }
        if (clienteRepository.removerPorId(id)) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Falha ao remover. Cliente com ID " + id + " não encontrado.");
        }
    }
}
