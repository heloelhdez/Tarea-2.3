/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author heloe
 */
public class Fraccion {
    public int numerador=0;
    public int denominador=0;

    public Fraccion(int numerador, int denominador) {
        if (denominador != 0) {
            this.numerador = numerador;
            this.denominador=denominador;
        }
        else{
            System.out.println("El denominador no debe ser 0.");
        }
    }

    public  int getDenominador() {
        return denominador;
    }

    public  int getNumerador() {
        return numerador;
    }

    public  void setDenominador(int denominador) {
        this.denominador = denominador;
    }

    public  void setNumerador(int numerador) {
        this.numerador = numerador;
    }
    
}
