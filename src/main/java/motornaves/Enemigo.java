package motornaves;

/**
 * Representa un enemigo con comportamiento NPC automático.
 * Estados: PATRULLAR, PERSEGUIR, ATACAR.
 */
public class Enemigo extends EntidadVideojuego {

    public enum EstadoEnemigo { PATRULLAR, PERSEGUIR, ATACAR }

    private static final int DISTANCIA_PERSEGUIR = 10;
    private static final int DISTANCIA_ATACAR = 3;
    private static final int DANIO_ATAQUE = 15;

    private EstadoEnemigo estado;
    private int velocidad;

    public Enemigo(String nombre, int x, int y) {
        super(nombre, "ENEMIGO", x, y, 28, 28, 50);
        this.estado = EstadoEnemigo.PATRULLAR;
        this.velocidad = 2;
    }

    @Override
    public void actualizar() {
        System.out.println("[ENEMIGO] " + getNombre() + " | Estado:" + estado + " | Pos:(" + getX() + "," + getY() + ")");
    }

    public void actualizarComportamiento(Jugador jugador) {
        int distancia = calcularDistancia(jugador);
        EstadoEnemigo estadoAnterior = this.estado;

        if (distancia <= DISTANCIA_ATACAR) {
            this.estado = EstadoEnemigo.ATACAR;
        } else if (distancia <= DISTANCIA_PERSEGUIR) {
            this.estado = EstadoEnemigo.PERSEGUIR;
        } else {
            this.estado = EstadoEnemigo.PATRULLAR;
        }

        if (!estadoAnterior.equals(this.estado)) {
            System.out.println("[ENEMIGO] " + getNombre() + " cambia estado: " + estadoAnterior + " -> " + this.estado);
        }

        ejecutarComportamiento(jugador);
    }

    private void ejecutarComportamiento(Jugador jugador) {
        switch (this.estado) {
            case PATRULLAR:
                setX(getX() + velocidad);
                System.out.println("[ENEMIGO] " + getNombre() + " patrulla -> Pos:(" + getX() + "," + getY() + ")");
                break;
            case PERSEGUIR:
                moverHaciaJugador(jugador);
                break;
            case ATACAR:
                jugador.recibirDanio(DANIO_ATAQUE);
                System.out.println("[ENEMIGO] " + getNombre() + " ataca al jugador por " + DANIO_ATAQUE + " de daño.");
                break;
        }
    }

    private void moverHaciaJugador(Jugador jugador) {
        if (getX() < jugador.getX()) setX(getX() + velocidad);
        else if (getX() > jugador.getX()) setX(getX() - velocidad);
        if (getY() < jugador.getY()) setY(getY() + velocidad);
        else if (getY() > jugador.getY()) setY(getY() - velocidad);
        System.out.println("[ENEMIGO] " + getNombre() + " persigue al jugador -> Pos:(" + getX() + "," + getY() + ")");
    }

    private int calcularDistancia(Jugador jugador) {
        int dx = Math.abs(this.getX() - jugador.getX());
        int dy = Math.abs(this.getY() - jugador.getY());
        return dx + dy;
    }

    public EstadoEnemigo getEstado() { return estado; }
}