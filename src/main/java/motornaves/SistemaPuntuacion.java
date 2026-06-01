 package motornaves;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la puntuacion, vidas y logros de la partida.
 * Tambien permite exportar el estado actual como Quick Save.
 */
public class SistemaPuntuacion {

    private static final int PUNTOS_POR_ENEMIGO = 100;
    private static final int PUNTOS_POR_MONEDA = 25;
    private static final int LOGRO_ENEMIGOS_ELIMINADOS = 3;

    private int puntuacion;
    private int vidas;
    private int enemigosEliminados;
    private List<String> logrosDesbloqueados;

    /**
     * Constructor. Inicializa la puntuacion a 0 y las vidas a 3.
     */
    public SistemaPuntuacion() {
        this.puntuacion = 0;
        this.vidas = 3;
        this.enemigosEliminados = 0;
        this.logrosDesbloqueados = new ArrayList<>();
    }

    /**
     * Registra la eliminacion de un enemigo sumando puntos y verificando logros.
     */
    public void registrarEnemigoEliminado() {
        this.puntuacion += PUNTOS_POR_ENEMIGO;
        this.enemigosEliminados++;
        System.out.println("[PUNTUACION] Enemigo eliminado. +" + PUNTOS_POR_ENEMIGO + " puntos. Total: " + puntuacion);
        verificarLogros();
    }

    /**
     * Registra la recogida de una moneda sumando puntos.
     */
    public void registrarMonedaRecogida() {
        this.puntuacion += PUNTOS_POR_MONEDA;
        System.out.println("[PUNTUACION] Moneda recogida. +" + PUNTOS_POR_MONEDA + " puntos. Total: " + puntuacion);
    }

    /**
     * Resta una vida al jugador si quedan vidas disponibles.
     */
    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
            System.out.println("[PUNTUACION] Vida perdida. Vidas restantes: " + vidas);
        }
    }

    /**
     * Verifica si se cumplen condiciones para desbloquear logros.
     */
    private void verificarLogros() {
        if (enemigosEliminados >= LOGRO_ENEMIGOS_ELIMINADOS
                && !logrosDesbloqueados.contains("EXTERMINADOR")) {
            desbloquearLogro("EXTERMINADOR", "Elimina " + LOGRO_ENEMIGOS_ELIMINADOS + " enemigos");
        }
    }

    /**
     * Desbloquea un logro y lo registra en la lista.
     * @param id Identificador unico del logro.
     * @param descripcion Descripcion del logro desbloqueado.
     */
    private void desbloquearLogro(String id, String descripcion) {
        logrosDesbloqueados.add(id);
        System.out.println("[LOGRO DESBLOQUEADO] ★ " + id + ": " + descripcion);
    }

    /**
     * Exporta el estado actual de la partida en formato JSON simulado.
     * @param jugador Referencia al jugador para incluir sus coordenadas y vida.
     * @return String con el estado serializado.
     */
    public String exportarEstado(Jugador jugador) {
        return "{\"puntuacion\":" + puntuacion +
               ",\"vidas\":" + vidas +
               ",\"enemigosEliminados\":" + enemigosEliminados +
               ",\"jugadorX\":" + jugador.getX() +
               ",\"jugadorY\":" + jugador.getY() +
               ",\"jugadorVida\":" + jugador.getVida() + "}";
    }

    /**
     * Imprime por consola el estado actual de puntuacion y logros.
     */
    public void mostrarEstado() {
        System.out.println("[PUNTUACION] Puntos:" + puntuacion + " | Vidas:" + vidas + " | Enemigos eliminados:" + enemigosEliminados);
        System.out.println("[PUNTUACION] Logros: " + (logrosDesbloqueados.isEmpty() ? "Ninguno" : logrosDesbloqueados));
    }

    /** @return Puntuacion acumulada. */
    public int getPuntuacion() { return puntuacion; }
    /** @return Vidas restantes. */
    public int getVidas() { return vidas; }
    /** @return Numero de enemigos eliminados. */
    public int getEnemigosEliminados() { return enemigosEliminados; }
    /** @return Lista de logros desbloqueados. */
    public List<String> getLogrosDesbloqueados() { return logrosDesbloqueados; }

    @Override
    public String toString() {
        return "Puntos:" + puntuacion + " | Vidas:" + vidas + " | Logros:" + logrosDesbloqueados;
    }
}