package udistrital.avanzada.duelomagos.modelo.observador;

import udistrital.avanzada.duelomagos.modelo.CampoDuelo;
import udistrital.avanzada.duelomagos.modelo.Hechizo;
import udistrital.avanzada.duelomagos.modelo.Mago;

/**
 * Interface para recibir eventos del duelo desde el Modelo.
 * Implementada por el Controlador para actualizar la Vista.
 */
public interface DueloListener {

    /**
     * Notifica que inicia o continúa el turno de un mago.
     * @param campo campo de duelo origen
     * @param atacante mago que ataca en este turno
     * @param defensor mago objetivo en este turno
     */
    void onTurno(CampoDuelo campo, Mago atacante, Mago defensor);

    /**
     * Notifica que un hechizo fue lanzado.
     * @param campo campo de duelo origen
     * @param atacante mago que lanzó el hechizo
     * @param defensor mago objetivo
     * @param hechizo hechizo lanzado
     * @param puntosGanados puntos sumados por el atacante
     */
    void onHechizoLanzado(CampoDuelo campo, Mago atacante, Mago defensor, Hechizo hechizo, int puntosGanados);

    /**
     * Notifica actualización de puntajes acumulados.
     * @param campo campo de duelo origen
     * @param magoA primer mago
     * @param magoB segundo mago
     */
    void onPuntajesActualizados(CampoDuelo campo, Mago magoA, Mago magoB);

    /**
     * Notifica el ganador del duelo.
     * @param campo campo de duelo origen
     * @param ganador mago ganador
     * @param perdedor mago perdedor
     */
    void onGanador(CampoDuelo campo, Mago ganador, Mago perdedor);
}


