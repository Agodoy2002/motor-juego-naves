
package motornaves;

/**
 * Clase abstracta base para todas las entidades del juego.
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

    public EntidadVideojuego(String nombre, String tipo, int x, int y, int ancho, int alto, int vida) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.vida = vida;
        this.activa = true;
        this.imagenRuta = "assets/" + tipo.toLowerCase() + ".png";
    }

    public abstract void actualizar();

    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public String getImagenRuta() { return imagenRuta; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVida() { return vida; }
    public boolean isActiva() { return activa; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setVida(int vida) { this.vida = vida; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida <= 0) {
            this.vida = 0;
            this.activa = false;
            System.out.println("[ENTIDAD] " + nombre + " ha sido eliminada.");
        }
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + nombre + " | Pos:(" + x + "," + y + ") | Vida:" + vida;
    }
}