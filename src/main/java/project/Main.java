package project;

public class Main {
    static void main() {
        AgendaTarefas agenda = new AgendaTarefas("Projeto Principal");
        Menu menu = new Menu(agenda);
        menu.iniciar();
    }
}
