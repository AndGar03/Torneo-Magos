package udistrital.avanzada.duelomagos.modelo;

/**
 * Representa un Hechizo disponible en el duelo.
 */
public class Hechizo {

    /** Nombre del hechizo. */
    private final String nombre;
    /** Poder base del hechizo (puntos potenciales). */
    private final int poderBase;

    /**
     * Crea un nuevo hechizo.
     * @param nombre nombre del hechizo
     * @param poderBase poder base del hechizo (no negativo)
     */
    public Hechizo(String nombre, int poderBase) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del hechizo es obligatorio");
        }
        if (poderBase < 0) {
            throw new IllegalArgumentException("El poder base no puede ser negativo");
        }
        this.nombre = nombre;
        this.poderBase = poderBase;
    }

    /**
     * @return nombre del hechizo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return poder base del hechizo
     */
    public int getPoderBase() {
        return poderBase;
    }
}


