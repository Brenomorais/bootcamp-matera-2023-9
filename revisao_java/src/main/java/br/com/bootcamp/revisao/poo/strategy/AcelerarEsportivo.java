package br.com.bootcamp.revisao.poo.strategy;

public class AcelerarEsportivo implements  ModoAcelerar{
    @Override
    public void acelerar() {
        System.out.println("Aceleração esportiva! Vai mais rápido.");
    }
}
