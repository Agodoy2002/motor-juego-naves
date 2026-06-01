package motornaves;

/**
 * Representa la nave del jugador en el juego.
 */
public class Jugador extends EntidadVideojuego {

    private int puntos;
    private int escudo;
    private int velocidad;

    public Jugador(String nombre, int x, int y) {
        super(nombre, "JUGADOR", x, y, 32, 32, 100);
        this.puntos = 0;
        this.escudo = 50;
        this.velocidad = 3;
    }

    @Override
    public void actualizar() {
        System.out.println("[JUGADOR] " + getNombre() + " actualizado en Pos:(" + getX() + "," + getY() + ") | Vida:" + getVida() + " | Escudo:" + escudo + " | Puntos:" + puntos);
    }

    public void mover(String direccion) {
        switch (direccion.toUpperCase()) {
            case "ARRIBA":    setY(getY() - velocidad); break;
            case "ABAJO":     setY(getY() + velocidad); break;
            case "IZQUIERDA": setX(getX() - velocidad); break;
            case "DERECHA":   setX(getX() + velocidad); break;
            default: System.out.println("[JUGADOR] Dirección no reconocida: " + direccion);
        }
        System.out.println("[JUGADOR] Movimiento " + direccion + " -> Pos:(" + getX() + "," + getY() + ")");
    }

    public void disparar() {
        System.out.println("[JUGADOR] " + getNombre() + " dispara desde Pos:(" + getX() + "," + getY() + ")");
    }

    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
        System.out.println("[JUGADOR] +"+cantidad+" puntos. Total: " + puntos);
    }

    public int getPuntos() { return puntos; }
    public int getEscudo() { return escudo; }
    public int getVelocidad() { return velocidad; }
    public void setEscudo(int escudo) { this.escudo = escudo; }
}