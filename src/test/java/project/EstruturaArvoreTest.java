package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da Estrutura de Dados (Árvore)")
class EstruturaArvoreTest {

    private EstruturaArvore arvore;

    @BeforeEach
    void setUp() {
        arvore = new EstruturaArvore("Raiz Estrutural");
    }

    // --- 2.1 Criação Inicial ---
    @Test
    @DisplayName("Deve criar árvore com raiz não nula")
    void testCriacaoRaiz() {
        assertNotNull(arvore.getRaiz());
        assertEquals("Raiz Estrutural", arvore.getRaiz().getNome());
        assertEquals(1, arvore.contarNos(), "Árvore recém-criada deve ter 1 nó (a raiz)");
    }

    // --- 2.2 Busca ---
    @Test
    @DisplayName("Deve buscar nós existentes e retornar null para inexistentes")
    void testBusca() {
        Tarefa raiz = arvore.getRaiz();
        Tarefa filho = new Tarefa("Filho");
        raiz.adicionarSubtarefa(filho);

        // Busca ID existente
        Tarefa encontrado = arvore.buscar(filho.getId());
        assertNotNull(encontrado);
        assertEquals("Filho", encontrado.getNome());

        // Busca ID inexistente
        assertNull(arvore.buscar(-999));

        // Busca Raiz
        assertNotNull(arvore.buscar(raiz.getId()));
    }

    // --- 2.3 Remoção ---
    @Test
    @DisplayName("Deve remover nó folha corretamente")
    void testRemoverFolha() {
        Tarefa raiz = arvore.getRaiz();
        Tarefa t1 = new Tarefa("T1");
        raiz.adicionarSubtarefa(t1);

        assertTrue(arvore.remover(t1.getId()));
        assertNull(arvore.buscar(t1.getId()));
        assertEquals(1, arvore.contarNos()); // Só sobra a raiz
    }

    @Test
    @DisplayName("Deve remover nó intermediário e seus filhos (Cascata)")
    void testRemoverCascata() {
        Tarefa raiz = arvore.getRaiz();
        Tarefa pai = new Tarefa("Pai");
        Tarefa filho = new Tarefa("Filho");
        
        pai.adicionarSubtarefa(filho);
        raiz.adicionarSubtarefa(pai);

        // Estrutura: Raiz -> Pai -> Filho
        assertEquals(3, arvore.contarNos());

        assertTrue(arvore.remover(pai.getId()));
        
        assertNull(arvore.buscar(pai.getId()));
        assertNull(arvore.buscar(filho.getId()), "O filho deve ser removido junto com o pai");
        assertEquals(1, arvore.contarNos());
    }

    @Test
    @DisplayName("Deve impedir remoção da raiz")
    void testNaoRemoveRaiz() {
        int idRaiz = arvore.getRaiz().getId();
        assertFalse(arvore.remover(idRaiz), "Deve retornar false ao tentar remover a raiz");
        assertNotNull(arvore.buscar(idRaiz), "A raiz deve permanecer na árvore");
    }

    @Test
    @DisplayName("Deve retornar false ao tentar remover ID inexistente")
    void testRemoverInexistente() {
        assertFalse(arvore.remover(-50));
    }

    @Test
    @DisplayName("Deve remover item do meio da lista de subtarefas (Teste de Iterator)")
    void testRemocaoMeioLista() {
        Tarefa raiz = arvore.getRaiz();
        Tarefa tA = new Tarefa("A");
        Tarefa tB = new Tarefa("B");
        Tarefa tC = new Tarefa("C");

        raiz.adicionarSubtarefa(tA);
        raiz.adicionarSubtarefa(tB);
        raiz.adicionarSubtarefa(tC);

        assertTrue(arvore.remover(tB.getId()));
        
        assertNotNull(arvore.buscar(tA.getId()));
        assertNull(arvore.buscar(tB.getId()));
        assertNotNull(arvore.buscar(tC.getId()));
        assertEquals(3, arvore.contarNos()); // Raiz + A + C
    }

    // --- 2.4 Contagem ---
    @Test
    @DisplayName("Deve contar nós corretamente em vários níveis")
    void testContagemComplexa() {
        // Raiz
        //  -> A
        //      -> A1
        //  -> B
        Tarefa raiz = arvore.getRaiz();
        Tarefa tA = new Tarefa("A");
        Tarefa tA1 = new Tarefa("A1");
        Tarefa tB = new Tarefa("B");

        tA.adicionarSubtarefa(tA1);
        raiz.adicionarSubtarefa(tA);
        raiz.adicionarSubtarefa(tB);

        assertEquals(4, arvore.contarNos()); // Raiz + A + A1 + B
    }

    // --- 2.5 Exibição ---
    @Test
    @DisplayName("Deve executar exibirArvore sem lançar exceções")
    void testExibicao() {
        Tarefa raiz = arvore.getRaiz();
        raiz.adicionarSubtarefa(new Tarefa("Teste Visual"));
        assertDoesNotThrow(() -> arvore.exibirArvore());
    }
}
