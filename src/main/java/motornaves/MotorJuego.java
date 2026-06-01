package motornaves;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Clase cerebro del juego. Controla el estado general y las entidades.
 */
public class MotorJuego {

    public enum EstadoJuego { MENU, JUGANDO, PAUSA, GAME_OVER }

    private EstadoJuego estado;
    private List<EntidadVideojuego> entidades;
    private SistemaPuntuacion sistemaPuntuacion;
    private Jugador jugador;

    public MotorJuego() {
        this.estado = EstadoJuego.MENU;
        this.entidades = new ArrayList<>();
        this.sistemaPuntuacion = new SistemaPuntuacion();
    }

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

    public void pausar() {
        if (this.estado == EstadoJuego.JUGANDO) {
            this.estado = EstadoJuego.PAUSA;
            System.out.println("[MOTOR] Juego en PAUSA.");
        }
    }

    public void reanudar() {
        if (this.estado == EstadoJuego.PAUSA) {
            this.estado = EstadoJuego.JUGANDO;
            System.out.println("[MOTOR] Juego REANUDADO.");
        }
    }

    public void forzarGameOver() {
        this.estado = EstadoJuego.GAME_OVER;
        System.out.println("[MOTOR] GAME OVER.");
        sistemaPuntuacion.mostrarEstado();
    }

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

    private boolean colisionan(EntidadVideojuego a, EntidadVideojuego b) {
        return a.getX() < b.getX() + b.getAncho()
            && a.getX() + a.getAncho() > b.getX()
            && a.getY() < b.getY() + b.getAlto()
            && a.getY() + a.getAlto() > b.getY();
    }

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

    public void agregarEntidad(EntidadVideojuego entidad) {
        entidades.add(entidad);
        System.out.println("[MOTOR] Entidad añadida: " + entidad.getNombre());
    }

    public String quickSave() {
        String guardado = sistemaPuntuacion.exportarEstado(jugador);
        System.out.println("[MOTOR] Quick Save: " + guardado);
        return guardado;
    }

    public EstadoJuego getEstado() { return estado; }
    public SistemaPuntuacion getSistemaPuntuacion() { return sistemaPuntuacion; }
    public List<EntidadVideojuego> getEntidades() { return entidades; }
}