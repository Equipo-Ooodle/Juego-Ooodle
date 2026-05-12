package co.edu.poli.ooodle.modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Representa una partida del juego Ooodle.
 * Contiene la solución generada, operadores matemáticos,
 * intentos realizados y el usuario asociado a la partida.
 * 
 * La partida genera una ecuación matemática usando
 * números aleatorios del 1 al 12 sin repetirse.
 * 
 * @author Mia
 * @version 1.0
 */
public class Partida {

    /**
     * Lista con la solución de la partida.
     */
	private final List<Integer> solucion;

    /**
     * Cantidad de intentos realizados por el jugador.
     */
    private int intentos = 0;

    /**
     * Número máximo de intentos permitidos.
     */
    private static final int maxIntentos = 6;

    /**
     * Operadores matemáticos usados en la ecuación.
     */
    private final List<String> operadores;

    /**
     * Operadores posibles para generar la ecuación.
     */
    private final String[] posibles = {"+", "-", "x"};

    /**
     * Usuario asociado a la partida.
     */
    private final Usuario usuario;

    /**
     * Resultado final de la partida.
     */
    private String resultado;
    
    /**
     * Constructor que crea una nueva partida generando
     * automáticamente solución y operadores.
     * 
     * @param usuario usuario que jugará la partida
     */
    public Partida(Usuario usuario) {
        this.usuario = usuario;
        this.intentos = 0;
        this.solucion = generarSolucion();     
        this.operadores = generarOperadores(); 
    }

    /**
     * Constructor usado para reconstruir partidas
     * previamente guardadas.
     * 
     * @param usuario usuario asociado
     * @param solucion solución de la partida
     * @param intentos intentos realizados
     * @param resultado resultado final
     */
    public Partida(Usuario usuario, List<Integer> solucion, int intentos, String resultado) {
        this.usuario = usuario;
        this.solucion = new ArrayList<>(solucion);
        this.operadores = generarOperadores(); 
        this.intentos = intentos;
        this.resultado = resultado;
    }

    /**
     * Genera una solución aleatoria con 4 números
     * distintos entre 1 y 12.
     * 
     * @return lista con la solución generada
     */
    private List<Integer> generarSolucion() {
        Random random = new Random();
        List<Integer> nums = new ArrayList<>();

        while (nums.size() < 4) {
            int num = random.nextInt(12) + 1;

            if (!nums.contains(num)) {
                nums.add(num);
            }
        }

        return nums;
    }
    
    /**
     * Genera y mezcla aleatoriamente los operadores
     * matemáticos de la ecuación.
     * 
     * @return lista de operadores mezclados
     */
    private List<String> generarOperadores() {

        List<String> ops = new ArrayList<>(Arrays.asList(posibles));
        Collections.shuffle(ops);

        return ops;
    }
    
    /**
     * Calcula el resultado de la ecuación respetando
     * la prioridad matemática de multiplicación antes
     * que suma o resta.
     * 
     * @return resultado de la operación matemática
     */
    public int calcularResultado() {
    	//Aquí estamos verificando que el programa primero haga la multipliación, como se establece en la matématica real a la hora de hacer
    	//una operación (dividir o multiplicar > restar o sumar. Todo para evitar resutlados de ecuaciones mal planteados.
        List<Integer> numeros = new ArrayList<>(solucion);
        List<String> ops = new ArrayList<>(operadores);

        for (int i = 0; i < ops.size(); i++) {
            if (ops.get(i).equals("x")) {
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
    
    /**
     * Devuelve la ecuación visible para el jugador
     * ocultando los números de la solución.
     * 
     * @return ecuación visible
     */
    public String getEcuacionVisible() {
        return "_ " + operadores.get(0) + " _ "
             + operadores.get(1) + " _ "
             + operadores.get(2) + " _ = "
             + calcularResultado();
    }

    /**
     * Valida un número ingresado por el jugador.
     * 
     * @param posicion posición evaluada
     * @param valor número ingresado
     * @return estado del número:
     *         VERDE, AMARILLO o GRIS
     */
    public String validarNumero(int posicion, int valor) {
        if (valor == solucion.get(posicion)) {
            return "VERDE";
        } else if (solucion.contains(valor)) {
            return "AMARILLO";
        }
        return "GRIS";
    }

    /**
     * Verifica si el jugador ganó la partida.
     * 
     * @param intento intento realizado
     * @return true si coincide con la solución
     */
    public boolean gano(List<Integer> intento) {
        for (int i = 0; i < 4; i++) {
            if (intento.get(i) != solucion.get(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Incrementa el número de intentos.
     * 
     * @return cantidad actual de intentos
     */
    public int sumarIntento() {
        intentos++;
        return intentos;
    }

    /**
     * Obtiene la cantidad de intentos realizados.
     * 
     * @return intentos realizados
     */
    public int getIntentos() {
        return intentos;
    }
    
    /**
     * Obtiene el número máximo de intentos permitidos.
     * 
     * @return máximo de intentos
     */
    public int getMaxIntentos() {
        return maxIntentos;
    }

    /**
     * Obtiene una copia de la solución.
     * 
     * @return lista con la solución
     */
    public List<Integer> getSolucion() {
        return new ArrayList<>(solucion); 
    }
    
    /**
     * Obtiene el resultado de la partida.
     * 
     * @return resultado final
     */
    public String getResultado() {
        return resultado;
    }
    
    /**
     * Obtiene el usuario asociado a la partida.
     * 
     * @return usuario de la partida
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene una copia de los operadores matemáticos.
     * 
     * @return lista de operadores
     */
    public List<String> getOperadores() {
        return new ArrayList<>(operadores); // 👈 copia
    }
    
    /**
     * Define el resultado de la partida.
     * 
     * @param resultado resultado final
     * @return instancia actual de la partida
     */
    public Partida setResultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

}