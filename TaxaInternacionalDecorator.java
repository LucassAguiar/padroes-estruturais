package br.unicamp.padroesestruturais.legacy.decorator;

public class TaxaInternacionalDecorator extends AjusteValorDecorator {

    public TaxaInternacionalDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() * 1.05;
    }
}
