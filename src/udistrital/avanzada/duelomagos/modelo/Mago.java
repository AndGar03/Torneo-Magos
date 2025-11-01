package udistrital.avanzada.duelomagos.modelo;

import java.util.Objects;

/**
 * Entidad que representa un mago participante del duelo. Implementa Runnable
 * para permitir la ejecución concurrente de sus turnos a través del monitor
 * {@link CampoDuelo}.
 */
public class Mago implements Runnable {

    /** Nombre del mago. */
    private final String nombre;
    /** Casa mágica del mago (agrupación/afiliación). */
    private final String casaMagica;
    /** Puntos acumulados durante el duelo. */
    private volatile int puntosAcumulados;
    /** Número de hechizos lanzados. */
    private volatile int hechizosLanzados;
    /** Rival actual en el duelo. */
    private volatile Mago rival;
    /** Si el mago está temporalmente aturdido. */
    private volatile boolean estaAturdido;
    /** Campo de duelo (monitor) en el que se ejecuta el combate. */
    private CampoDuelo campoDuelo;

    /**
     * Crea un mago.
     * @param nombre nombre del mago
     * @param casaMagica casa mágica del mago
     * Nota: El campo de duelo se puede inyectar posteriormente mediante {@link #setCampoDuelo(CampoDuelo)}
     */
    public Mago(String nombre, String casaMagica, CampoDuelo campoDuelo) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del mago es obligatorio");
        }
        if (casaMagica == null || casaMagica.isBlank()) {
            throw new IllegalArgumentException("La casa mágica es obligatoria");
        }
        this.nombre = nombre;
        this.casaMagica = casaMagica;
        this.campoDuelo = campoDuelo;
        this.puntosAcumulados = 0;
        this.hechizosLanzados = 0;
        this.estaAturdido = false;
    }

    /**
     * Bucle de ejecución del mago. Delegará la gestión del turno en el monitor
     * hasta que se declare un ganador del duelo.
     */
    @Override
    public void run() {
        if (campoDuelo == null) {
            throw new IllegalStateException("El mago no tiene CampoDuelo asociado");
        }
        while (!campoDuelo.hayGanador()) {
            campoDuelo.gestionarTurno(this);
        }
    }

    /**
        * Marca al mago como aturdido o no aturdido.
        * @param aturdido nuevo estado de aturdimiento
        */
    public void setEstaAturdido(boolean aturdido) {
        this.estaAturdido = aturdido;
    }

    /**
     * @return si el mago está aturdido
     */
    public boolean isEstaAturdido() {
        return estaAturdido;
    }

    /**
     * @return nombre del mago
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return casa mágica del mago
     */
    public String getCasaMagica() {
        return casaMagica;
    }

    /**
     * @return puntos acumulados
     */
    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    /**
     * Suma puntos al acumulado del mago.
     * @param puntos puntos a sumar (no negativo)
     */
    public void agregarPuntos(int puntos) {
        if (puntos < 0) {
            throw new IllegalArgumentException("Los puntos no pueden ser negativos");
        }
        this.puntosAcumulados += puntos;
    }

    /**
     * Reinicia los puntos acumulados del mago a cero.
     */
    public void resetearPuntos() {
        this.puntosAcumulados = 0;
        this.hechizosLanzados = 0;
        this.estaAturdido = false;
    }

    /**
     * Incrementa el contador de hechizos lanzados.
     */
    public void incrementarHechizosLanzados() {
        this.hechizosLanzados++;
    }

    /**
     * @return número de hechizos lanzados
     */
    public int getHechizosLanzados() {
        return hechizosLanzados;
    }

    /**
     * @return rival actual
     */
    public Mago getRival() {
        return rival;
    }

    /**
     * Establece el rival actual.
     * @param rival mago rival
     */
    public void setRival(Mago rival) {
        this.rival = rival;
    }

    /**
     * @return campo de duelo asociado
     */
    public CampoDuelo getCampoDuelo() {
        return campoDuelo;
    }

    /**
     * Establece el campo de duelo asociado.
     * @param campo campo de duelo
     */
    public void setCampoDuelo(CampoDuelo campo) {
        this.campoDuelo = Objects.requireNonNull(campo);
    }

    @Override
    public String toString() {
        return nombre + " (" + casaMagica + ")";
    }
}


