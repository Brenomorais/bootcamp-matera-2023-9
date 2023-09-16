package br.com.bootcamp.revisao;

public class UsandoWhile {
    public static void main(String[] args) {
        int i = 1;
        while (i <= 3){
            System.out.println("Tocando minha canção faorita pela " + i + "° vez!");
            i = i + 1;
        }
        System.out.println("\n");

        int bateriaDoCelular = 10;
        while(bateriaDoCelular > 0){
            System.out.println("Celular ainda está funcionando..." +
                    "Bateria está com " + bateriaDoCelular + "%.");

            //diminui a bateria em 2% a cada ciclo
            bateriaDoCelular -= 2;

            if(bateriaDoCelular == 0){
                System.out.println("Celular Desligado!");
            }
        }
    }
}
