package br.unicamp.padroesestruturais.legacy.decorator;

public class DescontoFidelidadeDecorator extends AjusteValorDecorator {

    public DescontoFidelidadeDecorator(ValorCobranca valorCobranca) {
        super(valorCobranca);
    }

    @Override
    public double calcular() {
        return valorCobranca.calcular() * 0.95;
    }
}
