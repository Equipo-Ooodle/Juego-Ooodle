package co.edu.poli.ooodle.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Partida {
	private final List<Integer> solucion;
    private int intentos = 0;
    private static final int maxIntentos = 6;
    private final List<String> operadores;
    private final String[] posibles = {"+", "-", "*"};
    private final Usuario usuario;
    private String resultado;
    
    public Partida(Usuario usuario) {
        this.usuario = usuario;
        this.intentos = 0;
        this.solucion = generarSolucion();
        this.operadores = generarOperadores();
    }

    public Partida(Usuario usuario, List<Integer> solucion, int intentos, String resultado) {
        this.usuario = usuario;
        this.solucion = new ArrayList<>(solucion);
        this.operadores = generarOperadores(); // o cargar si luego decides guardarlos
        this.intentos = intentos;
        this.resultado = resultado;
    }

    private List<Integer> generarSolucion() {
        Random random = new Random();

        while (solucion.size() < 4) {
            int num = random.nextInt(12) + 1;
            if (!solucion.contains(num)) {
                solucion.add(num);
            }
        }

        return solucion;
    }
    
    private List<String> generarOperadores() {

        List<String> ops = new ArrayList<>(Arrays.asList(posibles));
        Collections.shuffle(ops);

        return ops;
    }
    
    

    public int calcularResultado() {
    	//Aquí estamos verificando que el programa primero haga la multipliación, como se establece en la matématica real a la hora de hacer
    	//una operación (dividir o multiplicar > restar o sumar. Todo para evitar resutlados de ecuaciones mal planteados.
        List<Integer> numeros = new ArrayList<>(solucion);
        List<String> ops = new ArrayList<>(operadores);

        for (int i = 0; i < ops.size(); i++) {
            if (ops.get(i).equals("*")) {
                int mult = numeros.get(i) * numeros.get(i + 1);

                numeros.set(i, mult);
                numeros.remove(i + 1);
                ops.remove(i);

                i--;
            }
        }

        // Luego suma y resta
        int resultado = numeros.get(0);

        for (int i = 0; i < ops.size(); i++) {
            switch (ops.get(i)) {
                case "+":
                    resultado += numeros.get(i + 1);
                    break;

                case "-":
                    resultado -= numeros.get(i + 1);
                    break;
            }
        }

        return resultado;
    }
    
    public String getEcuacionVisible() {
        return "_ " + operadores.get(0) + " _ "
             + operadores.get(1) + " _ "
             + operadores.get(2) + " _ = "
             + calcularResultado();
    }

    public String validarNumero(int posicion, int valor) {
        if (valor == solucion.get(posicion)) {
            return "VERDE";
        } else if (solucion.contains(valor)) {
            return "AMARILLO";
        }
        return "GRIS";
    }

    public boolean gano(List<Integer> intento) {
        for (int i = 0; i < 4; i++) {
            if (intento.get(i) != solucion.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int sumarIntento() {
        intentos++;
        return intentos;
    }

    public int getIntentos() {
        return intentos;
    }
    
    

    public int getMaxIntentos() {
        return maxIntentos;
    }

    public List<Integer> getSolucion() {
        return new ArrayList<>(solucion); 
    }
    
    
    
    
    public String getResultado() {
        return resultado;
    }
    
    
    
    public Usuario getUsuario() {
        return usuario;
    }

    
    public List<String> getOperadores() {
        return new ArrayList<>(operadores); // 👈 copia
    }
    
    public Partida setResultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

   
}