package br.unicamp.padroesestruturais.legacy.adapter;

import br.unicamp.padroesestruturais.legacy.domain.FormaPagamento;

public class GatewayPagamentoFactory {

    public GatewayPagamento criar(FormaPagamento forma) {
        if (forma == null) {
            throw new IllegalArgumentException("Forma de pagamento nao suportada: null");
        }

        return switch (forma) {
            case BOLETO, PIX -> new GatewayInternoAdapter();
            case CARTAO_CREDITO -> new PaySecureAdapter();
            case CARTEIRA_DIGITAL -> new WalletPayAdapter();
        };
    }
}
