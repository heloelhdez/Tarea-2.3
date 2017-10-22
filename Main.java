/**
 * Main.java
 * Programa que itera matrices de transicion de estados de un proceso de Markov n veces y obtiene el vector de punto fijo
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
        A = new Fraccion[n][n];
        B = new Fraccion[n][n];
        Identidad = matrizIdentidad(n);

        for (int i = 0; i < n; i++) {
            
            for (int j = 0; j < n; j++) {
                
                A[i][j] = new Fraccion(Integer.parseInt(input.nextLine()),Integer.parseInt(input.nextLine()));
            }
        }
        
        int k = Integer.parseInt(input.nextLine());
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
        G = matrizGauss(n + 1); //Matriz nueva donde resolveremos por Gauss-Jordan

        //La primera fila de la matriz de resolucion se llena de unos (dado que x+y+z+w+...+n = 1)
        for(int i = 0; i < n + 1; i++) {
            
            G[0][i] = new Fraccion(1,1);
        }
        //Transferimos matriz transpuesta a matriz de resolucion, quitando la primera fila
        for(int i = 1; i <= n - 1; i++) {
            
            for(int j = 0; j < n; j++) {

                G[i][j] = C[i][j];
            }
            G[i][n] = new Fraccion(0,1);
        }
        //Paso 4: Resolvemos sistema de ecuaciones por gauss-jordan
        G = gaussJordan(G, n);

        System.out.print("Vector de Punto Fijo: ( ");
        for(int i = 0; i < n; i++) {
            
            imprimeFraccion(G[i][n]);
            if(i+1 != n) {
                
                System.out.print(", ");
            }
        }
        System.out.println(" )");
        
    }
    
    public static Fraccion multiplicaFraccion(Fraccion uno, Fraccion dos){
        
        Fraccion temporal = new Fraccion(0,1);
        
        temporal.setNumerador(uno.getNumerador()*dos.getNumerador()); 
        
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

    public static void imprimeMatriz(Fraccion[][] A) {

        for (int i = 0; i < A.length; i++) {

            for (int j = 0; j < A[i].length; j++) {
                
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
    public static Fraccion[][] matrizGauss(int longitud) {
        
        Fraccion[][] temporal = new Fraccion[longitud - 1][longitud];
        
        for (int i = 0; i < longitud - 1; i++) {
            
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
        
        Fraccion resultado = simplifica(temporal);
        
        return resultado;
    }
    
    public static int MCD(int numerador, int denominador) {
        
        return denominador == 0 ? numerador : MCD(denominador, numerador % denominador);
    }

    public static Fraccion simplifica(Fraccion uno) {
        
        long MCD = MCD(Math.abs(uno.getNumerador()), Math.abs(uno.getDenominador())); //Asegurarnos que el MCD se corra con puros numeros positivos
        
        return new Fraccion((int)(uno.getNumerador()/MCD),(int)(uno.getDenominador()/MCD));
    }

    public static Fraccion[][] gaussJordan(Fraccion[][] G, int longitud) {
        
        int columnaInicial = 0;
        
        for(int fila = 0; fila < longitud; fila++) {

            //Si la fraccion en la columna inicial es 0, intentar cambiar con otra fraccion
            while(G[fila][columnaInicial].getNumerador() == 0) {

                boolean cambiado = false;
                
                int i = fila;
                
                while(!cambiado && i < longitud) {
                    
                    if(G[i][columnaInicial].getNumerador() != 0) {
                        
                        Fraccion[] temporal = G[i];
                        
                        G[i] = G[fila];
                        
                        G[fila] = temporal;
                        
                        cambiado = true;
                    }

                    i++;
                }

                //Si despues de intentar cambiar sigue siendo 0, aumentar el iterador de columna
                if(G[fila][columnaInicial].getNumerador() == 0) {
                    
                    columnaInicial++;
                }

            }
            
            //Si la fraccion no es 1, reducir a 1
            if(G[fila][columnaInicial].getNumerador() != 1) {

                Fraccion divisor = G[fila][columnaInicial];

                for(int i = columnaInicial; i < longitud + 1; i++) {
                    
                    G[fila][i] = divideFraccion(G[fila][i], divisor);
                }
            }

            //Asegurarnos que el resto de fracciones en la fila sean 0
            for(int i = 0; i < longitud; i++) {
                
                if(i!= fila && G[i][columnaInicial].getNumerador() != 0) {
                    
                    Fraccion multiplo = new Fraccion(G[i][columnaInicial].getNumerador(),G[i][columnaInicial].getDenominador());
                    
                    multiplo.setNumerador(multiplo.getNumerador() * (-1));

                    for(int j = columnaInicial; j < longitud+1; j++) {
                        
                        Fraccion multiplicacion = multiplicaFraccion(multiplo, G[fila][j]);
                        
                        G[i][j] = simplifica(sumaFraccion(G[i][j], multiplicacion));
                    }
    
                }
            }
            
            columnaInicial++;

        }

        return G;
        
    }

}
