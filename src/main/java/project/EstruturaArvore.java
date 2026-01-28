package project;

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

    // --- Operações de Busca (Recursividade) ---

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

    // --- Operações de Listagem (Recursividade) ---

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

    // --- Operações de Remoção (Recursividade) ---

    public boolean remover(int id) {
        if (raiz.getId() == id) {
            return false; // Não removemos a raiz estrutural
        }
        return removerRecursivo(raiz, id);
    }

    private boolean removerRecursivo(Tarefa pai, int id) {
        for (Tarefa subtarefa : pai.getSubtarefas()) {
            if (subtarefa.getId() == id) {
                pai.removerSubtarefa(id);
                return true;
            }
            if (removerRecursivo(subtarefa, id)) {
                return true;
            }
        }
        return false;
    }

    // --- Operações de Contagem (Recursividade) ---

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
