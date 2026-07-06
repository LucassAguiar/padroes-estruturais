package br.unicamp.padroesestruturais.legacy.decorator;

public class EmissaoNotaFiscalDecorator extends AjusteValorDecorator {

    public EmissaoNotaFiscalDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() + 2.50;
    }
}
