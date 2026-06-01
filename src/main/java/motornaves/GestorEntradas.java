package motornaves;

/**
 * Procesa los comandos simulados del jugador.
 */
public class GestorEntradas {

    private static final String ACCION_DISPARAR = "ACCION";
    private static final String ACCION_PAUSA    = "PAUSA";

    private Jugador jugador;
    private MotorJuego motor;

    public GestorEntradas(Jugador jugador, MotorJuego motor) {
        this.jugador = jugador;
        this.motor   = motor;
    }

    public void procesarEntrada(String comando) {
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

    public void pulsarBotonAccion() {
        procesarEntrada(ACCION_DISPARAR);
    }

    public void desplazarEntidad(String direccion) {
        procesarEntrada(direccion);
    }
} 