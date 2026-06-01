 package motornaves;

/**
 * Representa un enemigo con comportamiento NPC automatico.
 * Cambia entre estados PATRULLAR, PERSEGUIR y ATACAR segun
 * la distancia Manhattan al jugador.
 */
public class Enemigo extends EntidadVideojuego {

    /** Estados posibles del comportamiento del enemigo. */
    public enum EstadoEnemigo { PATRULLAR, PERSEGUIR, ATACAR }

    private static final int DISTANCIA_PERSEGUIR = 10;
    private static final int DISTANCIA_ATACAR = 3;
    private static final int DANIO_ATAQUE = 15;

    private EstadoEnemigo estado;
    private int velocidad;

    /**
     * Constructor del enemigo.
     * @param nombre Nombre identificativo del enemigo.
     * @param x Posicion inicial en el eje X.
     * @param y Posicion inicial en el eje Y.
     */
    public Enemigo(String nombre, int x, int y) {
        super(nombre, "ENEMIGO", x, y, 28, 28, 50);
        this.estado = EstadoEnemigo.PATRULLAR;
        this.velocidad = 2;
    }

    /**
     * Actualiza el estado del enemigo e imprime su informacion actual.
     */
    @Override
    public void actualizar() {
        System.out.println("[ENEMIGO] " + getNombre() + " | Estado:" + estado + " | Pos:(" + getX() + "," + getY() + ")");
    }

    /**
     * Actualiza el comportamiento del enemigo segun su distancia al jugador.
     * Cambia de estado automaticamente y ejecuta la accion correspondiente.
     * @param jugador Referencia al jugador para calcular distancia y aplicar danio.
     */
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

    /**
     * Ejecuta la accion correspondiente al estado actual del enemigo.
     * @param jugador Referencia al jugador.
     */
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
                System.out.println("[ENEMIGO] " + getNombre() + " ataca al jugador por " + DANIO_ATAQUE + " de danio.");
                break;
        }
    }

    /**
     * Mueve al enemigo una posicion hacia el jugador en ambos ejes.
     * @param jugador Referencia al jugador.
     */
    private void moverHaciaJugador(Jugador jugador) {
        if (getX() < jugador.getX()) setX(getX() + velocidad);
        else if (getX() > jugador.getX()) setX(getX() - velocidad);
        if (getY() < jugador.getY()) setY(getY() + velocidad);
        else if (getY() > jugador.getY()) setY(getY() - velocidad);
        System.out.println("[ENEMIGO] " + getNombre() + " persigue al jugador -> Pos:(" + getX() + "," + getY() + ")");
    }

    /**
     * Calcula la distancia Manhattan entre el enemigo y el jugador.
     * @param jugador Referencia al jugador.
     * @return Distancia Manhattan calculada.
     */
    private int calcularDistancia(Jugador jugador) {
        int dx = Math.abs(this.getX() - jugador.getX());
        int dy = Math.abs(this.getY() - jugador.getY());
        return dx + dy;
    }

    /** @return Estado actual del enemigo. */
    public EstadoEnemigo getEstado() { return estado; }

    @Override
    public String toString() {
        return super.toString() + " | Estado:" + estado;
    }
}