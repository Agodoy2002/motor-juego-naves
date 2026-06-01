package motornaves;

/**
 * Clase principal. Simula el bucle de juego y las entradas del usuario por consola.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== MOTOR DE JUEGO - NAVES ESPACIALES ===\n");

        // Crear motor y jugador
        MotorJuego motor = new MotorJuego();
        Jugador jugador = new Jugador("Nave Alpha", 5, 5);

        // Iniciar partida
        motor.iniciarPartida(jugador);

        // Crear gestor de entradas
        GestorEntradas gestor = new GestorEntradas(jugador, motor);

        // Añadir enemigos
        Enemigo enemigo1 = new Enemigo("Cazador-01", 15, 5);
        Enemigo enemigo2 = new Enemigo("Cazador-02", 20, 10);
        motor.agregarEntidad(enemigo1);
        motor.agregarEntidad(enemigo2);

        System.out.println("\n--- TURNO 1: Movimiento del jugador ---");
        gestor.desplazarEntidad("ARRIBA");
        gestor.desplazarEntidad("DERECHA");
        gestor.pulsarBotonAccion();
        motor.actualizar();

        System.out.println("\n--- TURNO 2: Pausa y reanudación ---");
        gestor.procesarEntrada("PAUSA");
        motor.actualizar();
        gestor.procesarEntrada("REANUDAR");

        System.out.println("\n--- TURNO 3: Enemigo cercano ---");
        enemigo1.setX(6);
        enemigo1.setY(5);
        motor.actualizar();

        System.out.println("\n--- TURNO 4: Eliminar enemigo y logros ---");
        enemigo2.recibirDanio(50);
        motor.actualizar();
        motor.getSistemaPuntuacion().registrarEnemigoEliminado();
        motor.getSistemaPuntuacion().registrarEnemigoEliminado();
        motor.getSistemaPuntuacion().registrarEnemigoEliminado();

        System.out.println("\n--- QUICK SAVE ---");
        motor.quickSave();

        System.out.println("\n--- ESTADO FINAL ---");
        motor.getSistemaPuntuacion().mostrarEstado();

        System.out.println("\n--- FORZAR GAME OVER ---");
        motor.forzarGameOver();

        System.out.println("\n=== FIN DE LA SIMULACIÓN ===");
    }
}