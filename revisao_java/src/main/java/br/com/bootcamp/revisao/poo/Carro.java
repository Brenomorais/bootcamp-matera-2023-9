package br.com.bootcamp.revisao.poo;

public class Carro {
    //atributos: tambem chamados de campos ou variaveis de instancia
    private String marca;
    private String modelo;
    private int ano;
    private double velocidade;

    //metodos de instancias, tambem conhecidos como comportamentos\ações da classe
    public void ligar(){
        System.out.println("Carro ligado!");
    }
    public void desligar(){
        System.out.println("Carro desligado!");
    }
    public void acelerar(){
        velocidade += 10;
        System.out.println("Vrummm! Velocidade agora é: " + velocidade + "km/h");
    }
    public void acelar(double quantidade){
        velocidade += quantidade;
    }
    public void frear(){
        velocidade -= 10;
        System.out.println("Reduzindo... Velocidade agora é: " + velocidade + "km/h");
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }
}
