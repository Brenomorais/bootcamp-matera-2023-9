package br.com.bootcamp.revisao.poo;

public class CarroGasolina  implements  Reabastecivel, Aceleravel{

    @Override
    public void reabastecer() {
        System.out.println("Reabastecendo com gasolina no posto.");
    }

    @Override
    public void acelera() {
        System.out.println("Acelerando o carro a gasolina.");
    }
}
