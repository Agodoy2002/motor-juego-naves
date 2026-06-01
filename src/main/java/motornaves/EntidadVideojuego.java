package motornaves;

/**
 * Clase abstracta base para todas las entidades del juego.
 * Define los atributos comunes: posicion, tamanio, vida, nombre y tipo.
 * Toda entidad visible en el mundo del juego hereda de esta clase.
 */
public abstract class EntidadVideojuego {

    private String nombre;
    private String tipo;
    private String imagenRuta;
    private int x;
    private int y;
    private int ancho;
    private int alto;
    private int vida;
    private boolean activa;

    /**
     * Constructor base para todas las entidades.
     * @param nombre Nombre identificativo de la entidad.
     * @param tipo Tipo de entidad (JUGADOR, ENEMIGO...).
     * @param x Posicion inicial en el eje X.
     * @param y Posicion inicial en el eje Y.
     * @param ancho Ancho del sprite en pixeles.
     * @param alto Alto del sprite en pixeles.
     * @param vida Puntos de vida iniciales.
     */
    public EntidadVideojuego(String nombre, String tipo, int x, int y, int ancho, int alto, int vida) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = Math.max(0, x);
        this.y = Math.max(0, y);
        this.ancho = ancho;
        this.alto = alto;
        this.vida = vida;
        this.activa = true;
        this.imagenRuta = "assets/" + tipo.toLowerCase() + ".png";
    }

    /**
     * Metodo abstracto que cada entidad implementa para actualizar
     * su estado en cada tick del juego.
     */
    public abstract void actualizar();

    /**
     * Aplica danio a la entidad y la desactiva si la vida llega a cero.
     * @param danio Cantidad de puntos de vida a restar. Debe ser positivo.
     */
    public void recibirDanio(int danio) {
        if (danio < 0) return;
        this.vida -= danio;
        if (this.vida <= 0) {
            this.vida = 0;
            this.activa = false;
            System.out.println("[ENTIDAD] " + nombre + " ha sido eliminada.");
        }
    }

    /** @return Nombre de la entidad. */
    public String getNombre() { return nombre; }
    /** @return Tipo de la entidad. */
    public String getTipo() { return tipo; }
    /** @return Ruta de la imagen asociada. */
    public String getImagenRuta() { return imagenRuta; }
    /** @return Posicion en el eje X. */
    public int getX() { return x; }
    /** @return Posicion en el eje Y. */
    public int getY() { return y; }
    /** @return Ancho del sprite. */
    public int getAncho() { return ancho; }
    /** @return Alto del sprite. */
    public int getAlto() { return alto; }
    /** @return Puntos de vida actuales. */
    public int getVida() { return vida; }
    /** @return true si la entidad sigue activa en el juego. */
    public boolean isActiva() { return activa; }

    /**
     * Establece la posicion en el eje X. No acepta valores negativos.
     * @param x Nueva posicion en el eje X.
     */
    public void setX(int x) {
        if (x < 0) {
            System.out.println("[ENTIDAD] setX rechazado: coordenada X negativa (" + x + ")");
            return;
        }
        this.x = x;
    }

    /**
     * Establece la posicion en el eje Y. No acepta valores negativos.
     * @param y Nueva posicion en el eje Y.
     */
    public void setY(int y) {
        if (y < 0) {
            System.out.println("[ENTIDAD] setY rechazado: coordenada Y negativa (" + y + ")");
            return;
        }
        this.y = y;
    }

    /** @param vida Nuevos puntos de vida. */
    public void setVida(int vida) { this.vida = vida; }
    /** @param activa Estado de activacion de la entidad. */
    public void setActiva(boolean activa) { this.activa = activa; }

    @Override
    public String toString() {
        return "[" + tipo + "] " + nombre + " | Pos:(" + x + "," + y + ") | Vida:" + vida;
    }
} 