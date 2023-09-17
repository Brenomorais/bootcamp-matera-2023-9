package br.com.bootcamp.revisao.poo.polimorfismo;

public class TesteCar {
    public static void main(String[] args) {
        Car carElectric = new CarroEletrico();
        carElectric.dirigir();

        Car carAuto = new CarroAutonomo();
        carAuto.dirigir();
    }
}
