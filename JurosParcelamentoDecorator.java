package br.unicamp.padroesestruturais.legacy.decorator;

public class JurosParcelamentoDecorator extends AjusteValorDecorator {

    public JurosParcelamentoDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() * 1.0299;
    }
}
