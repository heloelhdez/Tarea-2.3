/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author heloe
 */
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static Fraccion[][] A;
    public static Fraccion[][] B;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = Integer.parseInt(input.nextLine());
        //System.out.println("n = "+n);
        A = new Fraccion[n][n];
        B = new Fraccion[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
              A[i][j] = new Fraccion(Integer.parseInt(input.nextLine()),Integer.parseInt(input.nextLine()));
            }
        }
        
        int k = Integer.parseInt(input.nextLine());
        //System.out.println("k = "+k);
        System.out.println("P1");
        imprimeMatriz(A);
        B=A;
        for (int i = 2; i <= k; i++) {
            System.out.println("P"+i);
            B = multiplicaMatriz(A,B);
            imprimeMatriz(simplificaMatriz(B));
        }
        
        /*Fraccion sumada = sumaFraccion(A[0][0], A[1][1]);
        imprimeFraccion(sumada);*/
        
    }
    
    public static Fraccion multiplicaFraccion(Fraccion uno, Fraccion dos){
        return new Fraccion(uno.getNumerador()*dos.getNumerador(), uno.getDenominador()*dos.getDenominador());
    }
    public static void imprimeMatriz(Fraccion[][] A){
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                imprimeFraccion(A[i][j]);
            }
            System.out.println("");
        }
    }
    public static Fraccion[][] simplificaMatriz(Fraccion[][] A){
        Fraccion[][] temporal = matrizDeCeros(A.length);
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
               temporal[i][j]=simplifica(A[i][j]);
            }
        }
        return temporal;
    }
    public static void imprimeFraccion(Fraccion uno){
        System.out.print(" "+uno.getNumerador()+"/"+uno.getDenominador()+" ");
    }
    
    public static Fraccion[][] multiplicaMatriz(Fraccion[][] A, Fraccion[][] B){
        Fraccion[][] temporal = matrizDeCeros(A.length);
        for (int i = 0; i < A.length; i++)
        {
            for (int j = 0; j < A.length; j++)
            {
                for (int k = 0; k < A.length; k++)
                {
                    temporal[i][j] = sumaFraccion(temporal[i][j], multiplicaFraccion(A[i][k], B[k][j]));
                    //c[i][j] = c[i][j] + numerador[i][k] * denominador[k][j];
                }
            }
        }
        return temporal;
    }
    public static Fraccion[][] matrizDeCeros(int longitud){
        Fraccion[][] temporal = new Fraccion[longitud][longitud];
        for (int i = 0; i < longitud; i++) {
            for (int j = 0; j < longitud; j++) {
                temporal[i][j] = new Fraccion(0,1);
            }
        }
        return temporal;
    }
    public static Fraccion sumaFraccion(Fraccion uno, Fraccion dos){
            return new Fraccion((uno.getNumerador()*dos.getDenominador())+(dos.getNumerador()*uno.getDenominador()), uno.getDenominador()*dos.getDenominador());
    }
    
    public static int MCD(int numerador, int denominador) {
    return denominador == 0 ? numerador : MCD(denominador, numerador % denominador);
    }

    public static Fraccion simplifica(Fraccion uno) {
    long MCD = MCD(uno.getNumerador(), uno.getDenominador());
        return new Fraccion((int)(uno.getNumerador()/MCD),(int)(uno.getDenominador()/MCD));
    }

}
