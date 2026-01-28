package project;

public class Main {
    static void main(String[] args) {
        AgendaTarefas agenda = new AgendaTarefas("Projeto Principal");
        Menu menu = new Menu(agenda);
        menu.iniciar();
    }
}
