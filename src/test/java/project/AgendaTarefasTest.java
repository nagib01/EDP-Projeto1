package project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Regra de Negócio (Agenda)")
class AgendaTarefasTest {

    private AgendaTarefas agenda;
    private EstruturaArvore arvoreInterna;

    @BeforeEach
    void setUp() throws Exception {
        agenda = new AgendaTarefas("Projeto Teste");
        
        // Usando Reflection para acessar a árvore interna e obter IDs para os testes
        // Isso é necessário porque a AgendaTarefas encapsula a árvore e não retorna IDs na criação
        Field campoArvore = AgendaTarefas.class.getDeclaredField("arvore");
        campoArvore.setAccessible(true);
        arvoreInterna = (EstruturaArvore) campoArvore.get(agenda);
    }

    // --- 3.1 Adição ---
    @Test
    @DisplayName("Deve adicionar tarefa na raiz e incrementar contagem visível")
    void testAdicionarNaRaiz() {
        agenda.adicionarTarefaNaRaiz("Tarefa 1");
        assertEquals(1, agenda.obterTotalTarefas());
        
        // Verifica se realmente foi adicionada na árvore interna
        assertEquals(2, arvoreInterna.contarNos()); // 1 Raiz + 1 Tarefa
    }

    @Test
    @DisplayName("Deve adicionar subtarefa com ID válido")
    void testAdicionarSubtarefaValida() {
        agenda.adicionarTarefaNaRaiz("Pai");
        
        // Pega o ID da tarefa "Pai" recém criada (única filha da raiz)
        Tarefa pai = arvoreInterna.getRaiz().getSubtarefas().get(0);
        int idPai = pai.getId();

        boolean adicionou = agenda.adicionarSubtarefa(idPai, "Filho");
        
        assertTrue(adicionou);
        assertEquals(2, agenda.obterTotalTarefas()); // Pai + Filho
        assertEquals(1, pai.getSubtarefas().size());
    }

    @Test
    @DisplayName("Não deve adicionar subtarefa com ID inválido")
    void testAdicionarSubtarefaInvalida() {
        boolean adicionou = agenda.adicionarSubtarefa(-999, "Orfão");
        assertFalse(adicionou);
        assertEquals(0, agenda.obterTotalTarefas());
    }

    // --- 3.2 Contagem (Regra N-1) ---
    @Test
    @DisplayName("Agenda vazia deve retornar 0 tarefas (Raiz invisível)")
    void testContagemInicial() {
        assertEquals(0, agenda.obterTotalTarefas());
        // Internamente sabemos que existe 1 nó (raiz), mas para o usuário é 0
    }

    @Test
    @DisplayName("Deve contar corretamente após remoção")
    void testContagemAposRemocao() {
        agenda.adicionarTarefaNaRaiz("T1");
        agenda.adicionarTarefaNaRaiz("T2");
        assertEquals(2, agenda.obterTotalTarefas());

        // Descobre ID de T1 para remover
        Tarefa t1 = arvoreInterna.getRaiz().getSubtarefas().get(0);
        
        agenda.removerTarefa(t1.getId());
        assertEquals(1, agenda.obterTotalTarefas());
    }

    // --- 3.3 Remoção ---
    @Test
    @DisplayName("Deve remover tarefa existente")
    void testRemoverExistente() {
        agenda.adicionarTarefaNaRaiz("Para Remover");
        Tarefa t = arvoreInterna.getRaiz().getSubtarefas().get(0);

        assertTrue(agenda.removerTarefa(t.getId()));
        assertEquals(0, agenda.obterTotalTarefas());
    }

    @Test
    @DisplayName("Não deve remover tarefa inexistente")
    void testRemoverInexistente() {
        assertFalse(agenda.removerTarefa(-500));
    }

    @Test
    @DisplayName("Não deve permitir remover a raiz via Agenda")
    void testNaoRemoveRaizPelaAgenda() {
        int idRaiz = arvoreInterna.getRaiz().getId();
        assertFalse(agenda.removerTarefa(idRaiz));
    }

    // --- 3.4 Edge Cases ---
    @Test
    @DisplayName("Deve lidar com múltiplas tarefas de mesmo nome (IDs diferentes)")
    void testNomesDuplicados() {
        agenda.adicionarTarefaNaRaiz("Reunião");
        agenda.adicionarTarefaNaRaiz("Reunião");

        assertEquals(2, agenda.obterTotalTarefas());
        
        Tarefa t1 = arvoreInterna.getRaiz().getSubtarefas().get(0);
        Tarefa t2 = arvoreInterna.getRaiz().getSubtarefas().get(1);
        
        assertNotEquals(t1.getId(), t2.getId());
        
        // Remove uma, a outra deve ficar
        agenda.removerTarefa(t1.getId());
        assertEquals(1, agenda.obterTotalTarefas());
        assertNotNull(arvoreInterna.buscar(t2.getId()));
    }
}
