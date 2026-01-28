package project;

import java.util.Iterator;

/**
 * Classe responsável exclusivamente pela manipulação da estrutura de dados (Árvore).
 * Aqui residem os algoritmos de recursividade, busca e travessia.
 */
public class EstruturaArvore {
    private Tarefa raiz;

    public EstruturaArvore(String nomeRaiz) {
        this.raiz = new Tarefa(nomeRaiz);
    }

    public Tarefa getRaiz() {
        return raiz;
    }

    public Tarefa buscar(int id) {
        if (raiz.getId() == id) {
            return raiz;
        }
        return buscarRecursivo(raiz, id);
    }

    private Tarefa buscarRecursivo(Tarefa atual, int id) {
        for (Tarefa subtarefa : atual.getSubtarefas()) {
            if (subtarefa.getId() == id) {
                return subtarefa;
            }
            Tarefa encontrada = buscarRecursivo(subtarefa, id);
            if (encontrada != null) {
                return encontrada;
            }
        }
        return null;
    }

    public void exibirArvore() {
        exibirRecursivo(raiz, 0);
    }

    private void exibirRecursivo(Tarefa atual, int nivel) {
        StringBuilder indentacao = new StringBuilder();
        for (int i = 0; i < nivel; i++) {
            indentacao.append("  ");
        }
        System.out.println(indentacao.toString() + atual);
        for (Tarefa subtarefa : atual.getSubtarefas()) {
            exibirRecursivo(subtarefa, nivel + 1);
        }
    }

    public boolean remover(int id) {
        if (raiz.getId() == id) {
            return false;
        }
        return removerRecursivo(raiz, id);
    }

    /**
     * Implementa a remoção segura utilizando Iterator (Regra 5.2).
     * Evita ConcurrentModificationException e para a recursão assim que encontra o alvo.
     */
    private boolean removerRecursivo(Tarefa pai, int id) {
        Iterator<Tarefa> iterator = pai.getSubtarefas().iterator();
        
        while (iterator.hasNext()) {
            Tarefa subtarefa = iterator.next();
            
            if (subtarefa.getId() == id) {
                iterator.remove();
                return true;
            }
            
            if (removerRecursivo(subtarefa, id)) {
                return true;
            }
        }
        return false;
    }

    public int contarNos() {
        return contarRecursivo(raiz);
    }

    private int contarRecursivo(Tarefa atual) {
        int contador = 1;
        for (Tarefa subtarefa : atual.getSubtarefas()) {
            contador += contarRecursivo(subtarefa);
        }
        return contador;
    }
}
