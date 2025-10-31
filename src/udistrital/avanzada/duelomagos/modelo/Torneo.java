package udistrital.avanzada.duelomagos.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import udistrital.avanzada.duelomagos.modelo.observador.DueloListener;

/**
 * Gestiona la secuencia de duelos entre múltiples magos hasta obtener un campeón.
 */
public class Torneo {

    /** Lista inmutable de hechizos disponibles para todos los duelos. */
    private final List<Hechizo> hechizosCompartidos;
    /** Observadores de eventos de duelo a encadenar a cada CampoDuelo. */
    private final List<DueloListener> listeners;

    /**
     * Crea un torneo con la lista de hechizos a compartir por cada duelo.
     * @param hechizos lista de hechizos
     */
    public Torneo(List<Hechizo> hechizos) {
        this.hechizosCompartidos = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(hechizos)));
        this.listeners = new ArrayList<>();
    }

    /**
     * Registra un listener que será suscrito a cada CampoDuelo iniciado.
     * @param listener observador
     */
    public void addListener(DueloListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    /**
     * Inicia el torneo con la lista de magos recibida. El último ganador será el campeón.
     * Este método crea un CampoDuelo para cada enfrentamiento y ejecuta los hilos
     * de ambos magos hasta que haya un ganador.
     * @param participantes lista inicial de magos (debe tener al menos 2)
     * @return campeón del torneo
     */
    public Mago iniciarTorneo(List<Mago> participantes) {
        if (participantes == null || participantes.size() < 2) {
            throw new IllegalArgumentException("Se requieren al menos dos magos para un torneo");
        }
        List<Mago> enCompetencia = new ArrayList<>(participantes);
        while (enCompetencia.size() > 1) {
            Mago m1 = enCompetencia.remove(0);
            Mago m2 = enCompetencia.remove(0);
            CampoDuelo campo = new CampoDuelo(m1, m2, hechizosCompartidos);
            for (DueloListener l : listeners) {
                campo.addListener(l);
                l.onDueloIniciado(campo, m1, m2);
            }
            Thread t1 = new Thread(m1, "Duelo-" + m1.getNombre());
            Thread t2 = new Thread(m2, "Duelo-" + m2.getNombre());
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            // Decide el ganador: el que superó el puntaje de victoria, o por mayor puntaje
            Mago ganador = (m1.getPuntosAcumulados() > m2.getPuntosAcumulados()) ? m1 : m2;
            // Reiniciar puntos para el siguiente duelo
            m1.resetearPuntos();
            m2.resetearPuntos();
            enCompetencia.add(ganador);
        }
        return enCompetencia.get(0);
    }
}


