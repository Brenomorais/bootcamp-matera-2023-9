package br.com.bootcamp.revisao.poo;

// Está classe CarroEsportivo é um subclasse da superclase Carro
public class CarroEsportivo extends Carro{
    int turbo;

    void ativarTurbo(){
        turbo = 1;
        setVelocidade(getVelocidade() + 5);
        System.out.println("Turbo ativado! Velocidade agora é: " + getVelocidade() + "km/h");
    }
}
