 package motornaves;

/**
 * Representa la nave del jugador en el juego.
 * Hereda de EntidadVideojuego y añade movimiento, disparo y puntuacion.
 */
public class Jugador extends EntidadVideojuego {

    private int puntos;
    private int escudo;
    private int velocidad;

    /**
     * Constructor del jugador.
     * @param nombre Nombre de la nave del jugador.
     * @param x Posicion inicial en el eje X.
     * @param y Posicion inicial en el eje Y.
     */
    public Jugador(String nombre, int x, int y) {
        super(nombre, "JUGADOR", x, y, 32, 32, 100);
        this.puntos = 0;
        this.escudo = 50;
        this.velocidad = 3;
    }

    /**
     * Actualiza el estado del jugador e imprime su informacion actual.
     */
    @Override
    public void actualizar() {
        System.out.println("[JUGADOR] " + getNombre() + " actualizado en Pos:(" + getX() + "," + getY() + ") | Vida:" + getVida() + " | Escudo:" + escudo + " | Puntos:" + puntos);
    }

    /**
     * Mueve la nave del jugador en la direccion indicada.
     * @param direccion Direccion del movimiento: ARRIBA, ABAJO, IZQUIERDA, DERECHA.
     */
    public void mover(String direccion) {
        switch (direccion.toUpperCase()) {
            case "ARRIBA":    setY(getY() - velocidad); break;
            case "ABAJO":     setY(getY() + velocidad); break;
            case "IZQUIERDA": setX(getX() - velocidad); break;
            case "DERECHA":   setX(getX() + velocidad); break;
            default: System.out.println("[JUGADOR] Direccion no reconocida: " + direccion);
        }
        System.out.println("[JUGADOR] Movimiento " + direccion + " -> Pos:(" + getX() + "," + getY() + ")");
    }

    /**
     * Simula el disparo de la nave del jugador.
     */
    public void disparar() {
        System.out.println("[JUGADOR] " + getNombre() + " dispara desde Pos:(" + getX() + "," + getY() + ")");
    }

    /**
     * Suma puntos al marcador del jugador.
     * @param cantidad Cantidad de puntos a sumar. Debe ser positiva.
     */
    public void sumarPuntos(int cantidad) {
        if (cantidad < 0) return;
        this.puntos += cantidad;
        System.out.println("[JUGADOR] +" + cantidad + " puntos. Total: " + puntos);
    }

    /** @return Puntos acumulados del jugador. */
    public int getPuntos() { return puntos; }
    /** @return Puntos de escudo actuales. */
    public int getEscudo() { return escudo; }
    /** @return Velocidad de movimiento. */
    public int getVelocidad() { return velocidad; }
    /** @param escudo Nuevos puntos de escudo. */
    public void setEscudo(int escudo) { this.escudo = escudo; }

    @Override
    public String toString() {
        return super.toString() + " | Escudo:" + escudo + " | Puntos:" + puntos;
    }
}
