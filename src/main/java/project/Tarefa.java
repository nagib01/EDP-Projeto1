package project;

import java.util.ArrayList;
import java.util.List;

public class Tarefa {
    private static int idCounter = 1;

    private int id;
    private String nome;
    private List<Tarefa> subtarefas;

    public Tarefa(String nome) {
        this.id = idCounter++;
        this.nome = nome;
        this.subtarefas = new ArrayList<>();
    }

    public void adicionarSubtarefa(Tarefa tarefa) {
        this.subtarefas.add(tarefa);
    }

    public boolean removerSubtarefa(int id) {
        return subtarefas.removeIf(t -> t.getId() == id);
    }

    public List<Tarefa> getSubtarefas() {
        return subtarefas;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nome;
    }
}
