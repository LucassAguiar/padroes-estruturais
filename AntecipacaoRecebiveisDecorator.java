package br.unicamp.padroesestruturais.legacy.decorator;

public class AntecipacaoRecebiveisDecorator extends AjusteValorDecorator {

    public AntecipacaoRecebiveisDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() * 1.015;
    }
}
