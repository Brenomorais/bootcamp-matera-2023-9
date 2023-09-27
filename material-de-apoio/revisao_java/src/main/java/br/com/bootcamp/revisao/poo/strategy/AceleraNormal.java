package br.com.bootcamp.revisao.poo.strategy;

public class AceleraNormal implements ModoAcelerar{
    @Override
    public void acelerar() {
        System.out.println("Aceleração normal");
    }
}
