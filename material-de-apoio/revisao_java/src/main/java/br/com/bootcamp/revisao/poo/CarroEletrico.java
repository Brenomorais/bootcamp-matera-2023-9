package br.com.bootcamp.revisao.poo;

public class CarroEletrico extends Carro implements  Reabastecivel, Aceleravel{
    //override: sobreescrita do metodo quando a classe filha faz sua propria implementacao de um metodo da classe pai
    @Override
    public void reabastecer() {
        System.out.println("Carro a bateria na estação de carregamento.");
    }

    @Override
    public void acelera() {
        System.out.println("Acelerando o carro eletrico silenciosamente!");
    }

    public static void fazerCarroReabastecer(Reabastecivel carro){
        carro.reabastecer();
    }

    public static void fazerCarroAcelerar(Aceleravel carro){
        carro.acelera();
    }
}
