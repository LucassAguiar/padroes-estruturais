package br.unicamp.padroesestruturais.legacy.decorator;

public class SeguroTransacaoDecorator extends AjusteValorDecorator {

    public SeguroTransacaoDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() + 4.90;
    }
}
