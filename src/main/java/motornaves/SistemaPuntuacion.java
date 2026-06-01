package motornaves;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la puntuación, vidas y logros de la partida.
 */
public class SistemaPuntuacion {

    private static final int PUNTOS_POR_ENEMIGO = 100;
    private static final int PUNTOS_POR_MONEDA = 25;
    private static final int LOGRO_ENEMIGOS_ELIMINADOS = 3;

    private int puntuacion;
    private int vidas;
    private int enemigosEliminados;
    private List<String> logrosDesbloqueados;

    public SistemaPuntuacion() {
        this.puntuacion = 0;
        this.vidas = 3;
        this.enemigosEliminados = 0;
        this.logrosDesbloqueados = new ArrayList<>();
    }

    public void registrarEnemigoEliminado() {
        this.puntuacion += PUNTOS_POR_ENEMIGO;
        this.enemigosEliminados++;
        System.out.println("[PUNTUACION] Enemigo eliminado. +" + PUNTOS_POR_ENEMIGO + " puntos. Total: " + puntuacion);
        verificarLogros();
    }

    public void registrarMonedaRecogida() {
        this.puntuacion += PUNTOS_POR_MONEDA;
        System.out.println("[PUNTUACION] Moneda recogida. +" + PUNTOS_POR_MONEDA + " puntos. Total: " + puntuacion);
    }

    public void perderVida() {
        if (this.vidas > 0) {
            this.vidas--;
            System.out.println("[PUNTUACION] Vida perdida. Vidas restantes: " + vidas);
        }
    }

    private void verificarLogros() {
        if (enemigosEliminados >= LOGRO_ENEMIGOS_ELIMINADOS
                && !logrosDesbloqueados.contains("EXTERMINADOR")) {
            desbloquearLogro("EXTERMINADOR", "Elimina " + LOGRO_ENEMIGOS_ELIMINADOS + " enemigos");
        }
    }

    private void desbloquearLogro(String id, String descripcion) {
        logrosDesbloqueados.add(id);
        System.out.println("[LOGRO DESBLOQUEADO] ★ " + id + ": " + descripcion);
    }

    public String exportarEstado(Jugador jugador) {
        return "{\"puntuacion\":" + puntuacion +
               ",\"vidas\":" + vidas +
               ",\"enemigosEliminados\":" + enemigosEliminados +
               ",\"jugadorX\":" + jugador.getX() +
               ",\"jugadorY\":" + jugador.getY() +
               ",\"jugadorVida\":" + jugador.getVida() + "}";
    }

    public void mostrarEstado() {
        System.out.println("[PUNTUACION] Puntos:" + puntuacion + " | Vidas:" + vidas + " | Enemigos eliminados:" + enemigosEliminados);
        System.out.println("[PUNTUACION] Logros: " + (logrosDesbloqueados.isEmpty() ? "Ninguno" : logrosDesbloqueados));
    }

    public int getPuntuacion() { return puntuacion; }
    public int getVidas() { return vidas; }
    public int getEnemigosEliminados() { return enemigosEliminados; }
    public List<String> getLogrosDesbloqueados() { return logrosDesbloqueados; }
}