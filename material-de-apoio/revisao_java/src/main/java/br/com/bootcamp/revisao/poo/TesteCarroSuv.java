package br.com.bootcamp.revisao.poo;

public class TesteCarroSuv {
    public static void main(String[] args) {
        CarroSuv meuCarroSuv = new CarroSuv();
        meuCarroSuv.setMarca("Tesla");
        meuCarroSuv.setModelo("Civic");
        meuCarroSuv.setAno(2020);
        meuCarroSuv.setVelocidade(0);

        System.out.println(meuCarroSuv.getMarca());
        System.out.println(meuCarroSuv.getModelo());
        System.out.println(meuCarroSuv.getAno());
        System.out.println(meuCarroSuv.getVelocidade());
        System.out.println("-----------------------------------");
        meuCarroSuv.ligar();
        meuCarroSuv.acelerar();
        meuCarroSuv.ativarOffRoad();
        meuCarroSuv.frear();
        meuCarroSuv.desativarOffRoad();
        meuCarroSuv.desligar();
    }
}