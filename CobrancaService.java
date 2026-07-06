package br.unicamp.padroesestruturais.legacy.service;

import br.unicamp.padroesestruturais.legacy.adapter.GatewayPagamento;
import br.unicamp.padroesestruturais.legacy.adapter.GatewayPagamentoFactory;
import br.unicamp.padroesestruturais.legacy.decorator.*;
import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;
import br.unicamp.padroesestruturais.legacy.domain.Pedido;
import br.unicamp.padroesestruturais.legacy.domain.ResultadoCobranca;

import java.util.ArrayList;
import java.util.List;

public class CobrancaService {

    private final GatewayPagamentoFactory gatewayFactory;

    public CobrancaService() {
        this.gatewayFactory = new GatewayPagamentoFactory();
    }

    public ResultadoCobranca cobrar(Pedido pedido, FormaPagamento forma,
                                    boolean aplicarDescontoFidelidade,
                                    boolean aplicarJurosParcelamento,
                                    boolean aplicarTaxaInternacional,
                                    boolean aplicarSeguro) {

        double valorFinal = calcularValorFinal(
                pedido.getValorBase(),
                aplicarDescontoFidelidade,
                aplicarJurosParcelamento,
                aplicarTaxaInternacional,
                aplicarSeguro
        );

        return cobrarValorFinal(pedido, forma, valorFinal);
    }

    public ResultadoCobranca cobrarComValorDecorado(Pedido pedido,
                                                    FormaPagamento forma,
                                                    ValorCobranca valorCobranca) {
        return cobrarValorFinal(pedido, forma, valorCobranca.calcular());
    }

    private ResultadoCobranca cobrarValorFinal(Pedido pedido,
                                               FormaPagamento forma,
                                               double valorFinal) {
        GatewayPagamento gateway = gatewayFactory.criar(forma);
        return gateway.cobrar(pedido, valorFinal, forma);
    }

    public List<ResultadoCobranca> cobrarEmLote(List<Pedido> pedidos, FormaPagamento forma,
                                                boolean aplicarDescontoFidelidade,
                                                boolean aplicarJurosParcelamento,
                                                boolean aplicarTaxaInternacional,
                                                boolean aplicarSeguro) {

        List<ResultadoCobranca> resultados = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            resultados.add(cobrar(
                    pedido,
                    forma,
                    aplicarDescontoFidelidade,
                    aplicarJurosParcelamento,
                    aplicarTaxaInternacional,
                    aplicarSeguro
            ));
        }

        return resultados;
    }

    public double calcularValorFinal(double valorBase,
                                     boolean aplicarDescontoFidelidade,
                                     boolean aplicarJurosParcelamento,
                                     boolean aplicarTaxaInternacional,
                                     boolean aplicarSeguro) {

        ValorCobranca valor = new ValorBase(valorBase);

        if (aplicarDescontoFidelidade) {
            valor = new DescontoFidelidadeDecorator(valor);
        }

        if (aplicarJurosParcelamento) {
            valor = new JurosParcelamentoDecorator(valor);
        }

        if (aplicarTaxaInternacional) {
            valor = new TaxaInternacionalDecorator(valor);
        }

        if (aplicarSeguro) {
            valor = new SeguroTransacaoDecorator(valor);
        }

        return valor.calcular();
    }
}
