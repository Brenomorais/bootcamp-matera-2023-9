package br.com.bootcamp.revisao.poo.strategy;

public class CarroPadrao {

    private ModoAcelerar modoAcelerar;

    public CarroPadrao(ModoAcelerar modoAcelerar){
        this.modoAcelerar = modoAcelerar;
    }

    public  void definirModoAcelerar(ModoAcelerar modoAcelerar){
        this.modoAcelerar = modoAcelerar;
    }

    public void acelerar(){
        modoAcelerar.acelerar();
    }

    public static void main(String[] args) {
        CarroPadrao carro = new CarroPadrao(new AceleraNormal());
        carro.acelerar();//aleração normal

        //alterado o modo de aceleração em tempo de execuação
        carro.definirModoAcelerar(new AcelerarEsportivo());
        carro.acelerar();// aceleração esportiva
    }
}
