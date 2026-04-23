package co.edu.poli.ooodle.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Partida {
    private final List<Integer> solucion = new ArrayList<>();
    private int intentos = 0;
    private static final int maxIntentos = 6;
    private final List<String> operadores = new ArrayList<>();
    private final String[] posibles = {"+", "-", "*"};
    private Usuario usuario;

    public Partida() {
        generarPartida();
        generarOperadores();
    }

    private List<Integer> generarPartida() {
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
        operadores.clear();

        List<String> temp = new ArrayList<>(List.of(posibles));
        Collections.shuffle(temp);

        operadores.addAll(temp);
        return operadores;
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
        return solucion;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}