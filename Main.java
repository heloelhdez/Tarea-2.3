/**
 *
 * @author Heloel Hernandez Santos A07007415
 * @author Jose Pablo Ortiz Lack A01099655
 */
import java.util.Scanner;
public class Main {

    public static Fraccion[][] A;
    public static Fraccion[][] B;
    public static Fraccion[][] C;
    public static Fraccion[][] Identidad;
    public static Fraccion[][] G;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = Integer.parseInt(input.nextLine());
        //System.out.println("n = "+n);
        A = new Fraccion[n][n];
        B = new Fraccion[n][n];
        Identidad = matrizIdentidad(n);

        for (int i = 0; i < n; i++) {
            
            for (int j = 0; j < n; j++) {
                
                A[i][j] = new Fraccion(Integer.parseInt(input.nextLine()),Integer.parseInt(input.nextLine()));
            }
        }
        
        int k = Integer.parseInt(input.nextLine());
        //System.out.println("k = "+k);
        System.out.println("P1");
        imprimeMatriz(A);
        System.out.println();
        B=A;
        System.out.println();
        for (int i = 2; i <= k; i++) {
            
            System.out.println("P"+i);
            B = multiplicaMatriz(A,B);
            imprimeMatriz(simplificaMatriz(B));
            System.out.println();
        }
        
        //Calculo del vector de punto fijo

        //Paso 1: Le restamos la matriz identidad a la matriz de transicion
        C = sumaMatriz(A, Identidad);
        
        //Paso 2: Transponemos la matriz
        C = transponerMatriz(C);
        
        //Paso 3: Transferimos la matriz transpuesta a una nueva matriz para resolver por Gauss-Jordan
        G = matrizDeCeros(n+1); //Matriz nueva donde resolveremos por Gauss-Jordan
        
        //La primera fila de la matriz de resolucion se llena de unos (dado que x+y+z+w+...+n = 1)
        for(int i = 0; i < n+1; i++) {
            G[0][i] = new Fraccion(1,1);
        }
        //Transferimos matriz transpuesta a matriz de resolucion
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                G[i+1][j] = C[i][j];
            }
            G[i][n] = new Fraccion(0,1);
        }

        //Paso 4: Resolvemos por Gauss-Jordan la Matriz
        imprimeFraccion(divideFraccion(new Fraccion(2,4), new Fraccion(1,3)));
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
    
    public static Fraccion[][] multiplicaMatriz(Fraccion[][] A, Fraccion[][] B) {
        
        Fraccion[][] temporal = matrizDeCeros(A.length);
        for (int i = 0; i < A.length; i++) {
            
            for (int j = 0; j < A.length; j++) {
                
                for (int k = 0; k < A.length; k++) {
                    
                    temporal[i][j] = sumaFraccion(temporal[i][j], multiplicaFraccion(A[i][k], B[k][j]));
                }
            }
        }
        return temporal;
    }

    public static Fraccion[][] sumaMatriz(Fraccion[][] A, Fraccion[][] B){
        
        Fraccion[][] temporal = matrizDeCeros(A.length);
        for (int i = 0; i < A.length; i++) {
            
            for (int j = 0; j < A.length; j++) {
                
                temporal[i][j] = sumaFraccion(A[i][j], B[i][j]);
            }
        }
        return temporal;
    }

    public static Fraccion[][] transponerMatriz(Fraccion[][] A) {
        
        Fraccion[][] temporal = matrizDeCeros(A.length);
        for (int i = 0; i < A.length; i++) {
            
            for (int j = 0; j < A.length; j++) {
                
                temporal[i][j] = A[j][i];
            }
        }
        return temporal;
    }

    public static Fraccion[][] matrizIdentidad(int longitud){
        
        Fraccion[][] temporal = new Fraccion[longitud][longitud];
        for (int i = 0; i < longitud; i++) {
            
            for (int j = 0; j < longitud; j++) {
                if(i == j) {
                    temporal[i][j] = new Fraccion(-1,1);
                }
                else {
                    temporal[i][j] = new Fraccion(0,1);
                }
            }
        }
        return temporal;
    }

    public static Fraccion[][] matrizDeCeros(int longitud) {
        
        Fraccion[][] temporal = new Fraccion[longitud][longitud];
        for (int i = 0; i < longitud; i++) {
            
            for (int j = 0; j < longitud; j++) {
                
                temporal[i][j] = new Fraccion(0,1);
            }
        }
        return temporal;
    }

    public static Fraccion sumaFraccion(Fraccion uno, Fraccion dos) {
        Fraccion temporal = new Fraccion(0,1);
        
        temporal.setNumerador((uno.getNumerador()*dos.getDenominador())+(dos.getNumerador()*uno.getDenominador())); 
        temporal.setDenominador(uno.getDenominador()*dos.getDenominador());
        if(temporal.getDenominador() < 0) { //Si el denominador resulta ser negativo
            temporal.setNumerador(temporal.getNumerador() * (-1)); //Hacemos que el numerador sea negativo por legibilidad
            temporal.setDenominador(temporal.getDenominador() * (-1));
        }
        if(temporal.getNumerador() < 0) {//Si el numerador es negativo...

            if(temporal.getDenominador() < 0 ) { //Y el denominador tambien...
                
                temporal.setNumerador(temporal.getNumerador() * (-1)); //La fraccion es positiva, por lo que multiplicamos ambos por -1
                temporal.setDenominador(temporal.getDenominador() * (-1));
            }
        }

        return temporal;
    }

    public static Fraccion divideFraccion(Fraccion uno, Fraccion dos) {
        
        Fraccion temporal = new Fraccion(0,1);
        
        temporal.setNumerador(uno.getNumerador() * dos.getDenominador());
        temporal.setDenominador(uno.getDenominador() * dos.getNumerador());
        Fraccion resultado = simplifica(temporal);
        return resultado;
    }
    
    public static int MCD(int numerador, int denominador) {
        //Asegurarnos que el procedimiento se corra con puros numeros positivos
        return denominador == 0 ? numerador : MCD(denominador, numerador % denominador);
    }

    public static Fraccion simplifica(Fraccion uno) {
        
        long MCD = MCD(Math.abs(uno.getNumerador()), Math.abs(uno.getDenominador())); //Asegurarnos que el MCD se corra con puros numeros positivos
        return new Fraccion((int)(uno.getNumerador()/MCD),(int)(uno.getDenominador()/MCD));
    }

}
