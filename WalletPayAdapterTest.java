package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.adapter.WalletPayAdapter;
import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletPayAdapterTest {

    @Test
    void deveAdaptarCobrancaWalletPayAprovada() {
        WalletPayAdapter adapter = new WalletPayAdapter();
        Pedido pedido = new Pedido("PED-001", "Joao Silva", "Notebook", 500.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 500.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("APROVADA", resultado.getStatus());
        assertEquals(FormaPagamento.CARTEIRA_DIGITAL, resultado.getFormaPagamento());
        assertNotNull(resultado.getReferencia());
        assertTrue(resultado.getReferencia().startsWith("WPAY-"));
    }

    @Test
    void deveAdaptarCobrancaWalletPayRecusada() {
        WalletPayAdapter adapter = new WalletPayAdapter();
        Pedido pedido = new Pedido("PED-003", "Construtora ABC", "Servidor", 15000.0);

        ResultadoCobranca resultado = adapter.cobrar(pedido, 15000.0, FormaPagamento.CARTEIRA_DIGITAL);

        assertEquals("RECUSADA", resultado.getStatus());
    }
}
