package br.com.bootcamp.revisao.poo;

public class TesteCarro {
    public static void main(String[] args) {
        Carro meuCarro = new Carro();
        meuCarro.setMarca("Honda");
        meuCarro.setModelo("Civic");
        meuCarro.setAno(2020);
        meuCarro.setVelocidade(0);

        System.out.println(meuCarro.getMarca());
        System.out.println(meuCarro.getModelo());
        System.out.println(meuCarro.getAno());
        System.out.println(meuCarro.getVelocidade());

        System.out.println("-----------------------------------");
        meuCarro.acelerar();
        meuCarro.frear();
    }
}