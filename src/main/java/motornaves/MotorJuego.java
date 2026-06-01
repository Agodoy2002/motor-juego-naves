 package motornaves;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase cerebro del juego. Controla el estado general de la partida
 * y gestiona la coleccion de entidades activas.
 * Estados posibles: MENU, JUGANDO, PAUSA, GAME_OVER.
 */
public class MotorJuego {

    /** Estados posibles del juego. */
    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER }

    private EstadoJuego estado;
    private List<EntidadVideojuego> entidades;
    private SistemaPuntuacion sistemaPuntuacion;
    private Jugador jugador;

    /**
     * Constructor del motor. Inicializa el juego en estado MENU.
     */
    public MotorJuego() {
        this.estado = EstadoJuego.MENU;
        this.entidades = new ArrayList<>();
        this.sistemaPuntuacion = new SistemaPuntuacion();
    }

    /**
     * Inicia una nueva partida con el jugador indicado.
     * Solo es posible si el estado es MENU o GAME_OVER.
     * @param jugador Nave del jugador que participara en la partida.
     */
    public void iniciarPartida(Jugador jugador) {
        if (this.estado != EstadoJuego.MENU && this.estado != EstadoJuego.GAME_OVER) {
            System.out.println("[MOTOR] No se puede iniciar: ya hay una partida en curso.");
            return;
        }
        this.jugador = jugador;
        this.estado = EstadoJuego.JUGANDO;
        agregarEntidad(jugador);
        System.out.println("[MOTOR] Partida iniciada. ¡Que empiece el juego!");
    }

    /**
     * Pausa el juego si esta en estado JUGANDO.
     */
    public void pausar() {
        if (this.estado == EstadoJuego.JUGANDO) {
            this.estado = EstadoJuego.PAUSA;
            System.out.println("[MOTOR] Juego en PAUSA.");
        }
    }

    /**
     * Reanuda el juego si esta en estado PAUSA.
     */
    public void reanudar() {
        if (this.estado == EstadoJuego.PAUSA) {
            this.estado = EstadoJuego.JUGANDO;
            System.out.println("[MOTOR] Juego REANUDADO.");
        }
    }

    /**
     * Fuerza el fin de la partida cambiando el estado a GAME_OVER.
     */
    public void forzarGameOver() {
        this.estado = EstadoJuego.GAME_OVER;
        System.out.println("[MOTOR] GAME OVER.");
        sistemaPuntuacion.mostrarEstado();
    }

    /**
     * Ejecuta un tick del bucle de juego.
     * Actualiza todas las entidades activas, detecta colisiones
     * y elimina las entidades inactivas.
     */
    public void actualizar() {
        if (this.estado != EstadoJuego.JUGANDO) {
            System.out.println("[MOTOR] Estado actual: " + estado + ". No se actualiza.");
            return;
        }
        System.out.println("[MOTOR] --- Actualizando entidades ---");
        for (EntidadVideojuego e : entidades) {
            if (e.isActiva()) {
                if (e instanceof Enemigo) {
                    ((Enemigo) e).actualizarComportamiento(jugador);
                } else {
                    e.actualizar();
                }
            }
        }
        detectarColisiones();
        eliminarEntidadesInactivas();

        if (sistemaPuntuacion.getVidas() <= 0) {
            forzarGameOver();
        }
    }

    /**
     * Detecta colisiones AABB entre el jugador y los enemigos activos.
     * Si hay colision, el jugador recibe danio y el enemigo se desactiva.
     */
    private void detectarColisiones() {
        for (EntidadVideojuego e : entidades) {
            if (e instanceof Enemigo && e.isActiva()) {
                if (colisionan(jugador, e)) {
                    System.out.println("[COLISION] " + jugador.getNombre() + " choca con " + e.getNombre());
                    jugador.recibirDanio(10);
                    sistemaPuntuacion.perderVida();
                    e.setActiva(false);
                }
            }
        }
    }

    /**
     * Comprueba si dos entidades se solapan usando el algoritmo AABB.
     * @param a Primera entidad.
     * @param b Segunda entidad.
     * @return true si las entidades colisionan.
     */
    private boolean colisionan(EntidadVideojuego a, EntidadVideojuego b) {
        return a.getX() < b.getX() + b.getAncho()
            && a.getX() + a.getAncho() > b.getX()
            && a.getY() < b.getY() + b.getAlto()
            && a.getY() + a.getAlto() > b.getY();
    }

    /**
     * Elimina de la lista todas las entidades cuyo estado es inactivo
     * y registra los puntos correspondientes.
     */
    private void eliminarEntidadesInactivas() {
        Iterator<EntidadVideojuego> it = entidades.iterator();
        while (it.hasNext()) {
            EntidadVideojuego e = it.next();
            if (!e.isActiva()) {
                System.out.println("[MOTOR] Entidad eliminada de la lista: " + e.getNombre());
                if (e instanceof Enemigo) {
                    sistemaPuntuacion.registrarEnemigoEliminado();
                }
                it.remove();
            }
        }
    }

    /**
     * Añade una entidad a la lista de entidades activas del juego.
     * @param entidad Entidad a añadir.
     */
    public void agregarEntidad(EntidadVideojuego entidad) {
        entidades.add(entidad);
        System.out.println("[MOTOR] Entidad añadida: " + entidad.getNombre());
    }

    /**
     * Exporta el estado actual de la partida en formato JSON simulado.
     * @return String con el estado serializado.
     */
    public String quickSave() {
        String guardado = sistemaPuntuacion.exportarEstado(jugador);
        System.out.println("[MOTOR] Quick Save: " + guardado);
        return guardado;
    }

    /** @return Estado actual del juego. */
    public EstadoJuego getEstado() { return estado; }
    /** @return Sistema de puntuacion de la partida. */
    public SistemaPuntuacion getSistemaPuntuacion() { return sistemaPuntuacion; }
    /** @return Lista de entidades activas. */
    public List<EntidadVideojuego> getEntidades() { return entidades; }

    @Override
    public String toString() {
        return "[MOTOR] Estado:" + estado + " | Entidades:" + entidades.size() + " | " + sistemaPuntuacion;
    }
}