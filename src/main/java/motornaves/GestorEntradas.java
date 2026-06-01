 package motornaves;

/**
 * Procesa los comandos simulados del jugador.
 * Traduce entradas en acciones sobre el jugador y el motor de juego.
 */
public class GestorEntradas {

    private static final String ACCION_DISPARAR = "ACCION";
    private static final String ACCION_PAUSA    = "PAUSA";

    private Jugador jugador;
    private MotorJuego motor;

    /**
     * Constructor del gestor de entradas.
     * @param jugador Referencia al jugador que recibira los comandos.
     * @param motor Referencia al motor para gestionar pausa y reanudacion.
     */
    public GestorEntradas(Jugador jugador, MotorJuego motor) {
        this.jugador = jugador;
        this.motor   = motor;
    }

    /**
     * Procesa un comando de entrada y ejecuta la accion correspondiente.
     * @param comando Comando a procesar (ARRIBA, ABAJO, IZQUIERDA, DERECHA, ACCION, PAUSA, REANUDAR).
     */
    public void procesarEntrada(String comando) {
        if (comando == null || comando.isEmpty()) {
            System.out.println("[INPUT] Comando vacio ignorado.");
            return;
        }
        System.out.println("[INPUT] Comando recibido: " + comando);
        switch (comando.toUpperCase()) {
            case "ARRIBA":
            case "ABAJO":
            case "IZQUIERDA":
            case "DERECHA":
                jugador.mover(comando);
                break;
            case ACCION_DISPARAR:
                jugador.disparar();
                break;
            case ACCION_PAUSA:
                motor.pausar();
                break;
            case "REANUDAR":
                motor.reanudar();
                break;
            default:
                System.out.println("[INPUT] Comando no reconocido: " + comando);
        }
    }

    /**
     * Simula la pulsacion del boton de accion (disparo).
     */
    public void pulsarBotonAccion() {
        procesarEntrada(ACCION_DISPARAR);
    }

    /**
     * Simula el desplazamiento de la nave en una direccion.
     * @param direccion Direccion del desplazamiento.
     */
    public void desplazarEntidad(String direccion) {
        procesarEntrada(direccion);
    }
}