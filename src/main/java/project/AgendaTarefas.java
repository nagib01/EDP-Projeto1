package project;

/**
 * Classe de Regra de Negócio.
 * Utiliza a EstruturaArvore para gerenciar as tarefas, mas foca no "O Quê" e não no "Como".
 */
public class AgendaTarefas {
    private EstruturaArvore arvore;

    public AgendaTarefas(String nomeProjeto) {
        this.arvore = new EstruturaArvore(nomeProjeto);
    }

    public void adicionarTarefaNaRaiz(String nome) {
        Tarefa raiz = arvore.getRaiz();
        raiz.adicionarSubtarefa(new Tarefa(nome));
    }

    public boolean adicionarSubtarefa(int idPai, String nome) {
        Tarefa pai = arvore.buscar(idPai);
        if (pai != null) {
            pai.adicionarSubtarefa(new Tarefa(nome));
            return true;
        }
        return false;
    }

    public boolean removerTarefa(int id) {
        return arvore.remover(id);
    }

    public void imprimirAgenda() {
        arvore.exibirArvore();
    }

    /**
     * Retorna o total de tarefas do utilizador.
     * Aplica a Regra 4.1: Subtrai 1 do total de nós, pois a raiz é apenas estrutural.
     */
    public int obterTotalTarefas() {
        int totalNos = arvore.contarNos();
        return Math.max(0, totalNos - 1);
    }
}
