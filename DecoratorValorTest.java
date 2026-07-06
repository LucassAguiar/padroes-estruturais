package br.unicamp.padroesestruturais.legacy;

import br.unicamp.padroesestruturais.legacy.decorator.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorValorTest {

    @Test
    void deveAplicarAntecipacaoRecebiveis() {
        ValorCobranca valor = new AntecipacaoRecebiveisDecorator(new ValorBase(1000.0));

        assertEquals(1015.0, valor.calcular(), 0.001);
    }

    @Test
    void deveAplicarEmissaoNotaFiscal() {
        ValorCobranca valor = new EmissaoNotaFiscalDecorator(new ValorBase(1000.0));

        assertEquals(1002.50, valor.calcular(), 0.001);
    }

    @Test
    void deveComporMultiplosDecorators() {
        ValorCobranca valor = new EmissaoNotaFiscalDecorator(
                new SeguroTransacaoDecorator(
                        new JurosParcelamentoDecorator(
                                new DescontoFidelidadeDecorator(
                                        new ValorBase(1000.0)
                                )
                        )
                )
        );

        double esperado = 1000.0;
        esperado = esperado * 0.95;
        esperado = esperado * 1.0299;
        esperado = esperado + 4.90;
        esperado = esperado + 2.50;

        assertEquals(esperado, valor.calcular(), 0.001);
    }

    @Test
    void devePermitirComposicaoEmOrdemDiferente() {
        ValorCobranca valor = new DescontoFidelidadeDecorator(
                new EmissaoNotaFiscalDecorator(
                        new AntecipacaoRecebiveisDecorator(
                                new ValorBase(1000.0)
                        )
                )
        );

        double esperado = 1000.0;
        esperado = esperado * 1.015;
        esperado = esperado + 2.50;
        esperado = esperado * 0.95;

        assertEquals(esperado, valor.calcular(), 0.001);
    }
}
