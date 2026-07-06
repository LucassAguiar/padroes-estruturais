package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.adapter.PaySecureAdapter;
import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaySecureAdapterTest {

    @Test
    void deveAdaptarCobrancaPaySecureAprovada() {
        PaySecureAdapter adapter = new PaySecureAdapter();
        Pedido pedido = new Pedido("PED-001", "Joao Silva", "Notebook", 1000.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 1000.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(FormaPagamento.CARTAO_CREDITO, resultado.getFormaPagamento());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("PSEC-"));
    }

    @Test
    void deveAdaptarCobrancaPaySecureRecusada() {
        PaySecureAdapter adapter = new PaySecureAdapter();
        Pedido pedido = new Pedido("PED-003", "Construtora ABC", "Servidor", 15000.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 15000.0, FormaPagamento.CARTAO_CREDITO);

        assertEquals("RECUSADA", resultado.getStatus());
    }
}
