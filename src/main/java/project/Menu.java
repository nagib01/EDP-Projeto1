package project;

import java.util.Scanner;

/**
 * Classe responsável pela interação com o utilizador (Interface de Texto).
 * Isola a lógica de entrada/saída (I/O) da lógica de negócio e da inicialização.
 */
public class Menu {
    private AgendaTarefas agenda;
    private Scanner scanner;

    public Menu(AgendaTarefas agenda) {
        this.agenda = agenda;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;
        do {
            exibirOpcoes();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println("\n--- Agenda de Tarefas ---");
        System.out.println("1. Adicionar Tarefa Principal");
        System.out.println("2. Adicionar Subtarefa");
        System.out.println("3. Listar Tarefas");
        System.out.println("4. Remover Tarefa");
        System.out.println("5. Contar Tarefas");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarTarefaPrincipal();
                break;
            case 2:
                adicionarSubtarefa();
                break;
            case 3:
                System.out.println("\nEstrutura de Tarefas:");
                agenda.imprimirAgenda();
                break;
            case 4:
                removerTarefa();
                break;
            case 5:
                System.out.println("Total de tarefas: " + agenda.obterTotalTarefas());
                break;
            case 0:
                System.out.println("Encerrando...");
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }

    private void adicionarTarefaPrincipal() {
        System.out.print("Nome da tarefa: ");
        String nome = scanner.nextLine();
        agenda.adicionarTarefaNaRaiz(nome);
        System.out.println("Tarefa adicionada.");
    }

    private void adicionarSubtarefa() {
        System.out.print("ID da tarefa pai: ");
        int idPai = lerInteiro();
        System.out.print("Nome da subtarefa: ");
        String nomeSub = scanner.nextLine();
        if (agenda.adicionarSubtarefa(idPai, nomeSub)) {
            System.out.println("Subtarefa adicionada.");
        } else {
            System.out.println("Tarefa pai não encontrada.");
        }
    }

    private void removerTarefa() {
        System.out.print("ID da tarefa a remover: ");
        int idRemover = lerInteiro();
        if (agenda.removerTarefa(idRemover)) {
            System.out.println("Tarefa removida.");
        } else {
            System.out.println("Tarefa não encontrada ou não pode ser removida.");
        }
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
