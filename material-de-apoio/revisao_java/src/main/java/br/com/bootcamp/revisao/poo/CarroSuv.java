package br.com.bootcamp.revisao.poo;

public class CarroSuv extends Carro{

    boolean atracaoIntegral = false;

    void ativarOffRoad(){
        atracaoIntegral = true;
        System.out.println("Modo Off-Road ativado, tração 4x4 ligada!");
    }
    void desativarOffRoad(){
        atracaoIntegral = false;
        System.out.println("Modo Off-Road desativado, tração 4x4 desligada!");
    }
}
