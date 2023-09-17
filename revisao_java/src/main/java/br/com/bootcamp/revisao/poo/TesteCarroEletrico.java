package br.com.bootcamp.revisao.poo;

public class TesteCarroEletrico {
    public static void main(String[] args) {
        CarroEletrico meuCarroEletrico = new CarroEletrico();
        meuCarroEletrico.setMarca("Tesla");
        meuCarroEletrico.setModelo("Civic");
        meuCarroEletrico.setAno(2020);
        meuCarroEletrico.setVelocidade(0);

        System.out.println(meuCarroEletrico.getMarca());
        System.out.println(meuCarroEletrico.getModelo());
        System.out.println(meuCarroEletrico.getAno());
        System.out.println(meuCarroEletrico.getVelocidade());
        System.out.println("-----------------------------------");
        meuCarroEletrico.ligar();
        meuCarroEletrico.acelera();
        meuCarroEletrico.frear();
        meuCarroEletrico.desligar();

        meuCarroEletrico.reabastecer();
    }
}