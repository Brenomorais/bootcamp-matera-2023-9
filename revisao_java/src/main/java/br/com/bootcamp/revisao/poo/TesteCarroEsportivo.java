package br.com.bootcamp.revisao.poo;

public class TesteCarroEsportivo {
    public static void main(String[] args) {
        CarroEsportivo meuCarroSport = new CarroEsportivo();
        meuCarroSport.setMarca("Honda");
        meuCarroSport.setModelo("Civic Turbo");
        meuCarroSport.setAno(2023);
        meuCarroSport.setVelocidade(0);

        System.out.println(meuCarroSport.getMarca());
        System.out.println(meuCarroSport.getModelo());
        System.out.println(meuCarroSport.getAno());
        System.out.println(meuCarroSport.getVelocidade());
        System.out.println("-----------------------------------");
        meuCarroSport.ligar();
        meuCarroSport.acelerar();
        meuCarroSport.ativarTurbo();
        meuCarroSport.frear();
        meuCarroSport.desligar();
    }
}