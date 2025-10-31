package udistrital.avanzada.duelomagos.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import udistrital.avanzada.duelomagos.modelo.observador.DueloListener;

/**
 * Monitor que coordina el duelo entre dos magos. Controla los turnos usando
 * sincronización intrínseca (synchronized) y primitivas wait/notifyAll.
 */
public class CampoDuelo {

    /** Límite de puntos para declarar victoria. */
    public static final int PUNTAJE_VICTORIA = 250;

    /** Lista de hechizos disponibles. */
    private final List<Hechizo> hechizosDisponibles;
    /** Mago A en el campo. */
    private final Mago magoA;
    /** Mago B en el campo. */
    private final Mago magoB;
    /** Indica de quién es el turno actual. */
    private Mago turnoActual;
    /** Aleatoriedad para esperas y selección de hechizos. */
    private final Random random;
    /** Observadores de eventos de duelo. */
    private final List<DueloListener> listeners;
    /** Si existe un ganador confirmado. */
    private boolean hayGanador;

    /**
     * Crea el campo de duelo con dos magos y una lista de hechizos.
     * @param magoA primer mago
     * @param magoB segundo mago
     * @param hechizos lista de hechizos disponibles
     */
    public CampoDuelo(Mago magoA, Mago magoB, List<Hechizo> hechizos) {
        this.magoA = Objects.requireNonNull(magoA);
        this.magoB = Objects.requireNonNull(magoB);
        this.hechizosDisponibles = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(hechizos)));
        this.turnoActual = magoA;
        this.random = new Random();
        this.listeners = new ArrayList<>();
        this.hayGanador = false;
        this.magoA.setRival(magoB);
        this.magoB.setRival(magoA);
        this.magoA.setCampoDuelo(this);
        this.magoB.setCampoDuelo(this);
    }

    /**
     * Registra un observador de eventos del duelo.
     * @param listener observador
     */
    public synchronized void addListener(DueloListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    /**
     * Gestiona el turno del mago que ataca. Se bloquea si no es su turno.
     * Aplica las restricciones de tiempo indicadas y actualiza puntajes.
     * @param magoQueAtaca mago que intenta jugar su turno
     */
    public synchronized void gestionarTurno(Mago magoQueAtaca) {
        while (!hayGanador && turnoActual != magoQueAtaca) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (hayGanador) {
            return;
        }

        Mago atacante = magoQueAtaca;
        Mago defensor = atacante.getRival();

        notificarTurno(atacante, defensor);

        // Si el atacante está aturdido, espera 1 segundo
        if (atacante.isEstaAturdido()) {
            notificarMagoAturdido(atacante);
            dormirSinInterrumpir(1000);
            atacante.setEstaAturdido(false);
        }

        // Selección de hechizo y lanzamiento
        Hechizo hechizo = seleccionarHechizoAleatorio();

        // Espera simulando tiempo de conjuro y reacción del rival: 1 segundo
        dormirSinInterrumpir(1000);

        int puntos = calcularPuntos(hechizo);
        atacante.agregarPuntos(puntos);
        atacante.incrementarHechizosLanzados();
        notificarHechizo(atacante, defensor, hechizo, puntos);

        notificarPuntajes();

        if (atacante.getPuntosAcumulados() > PUNTAJE_VICTORIA) {
            hayGanador = true;
            notificarGanador(atacante, defensor);
            notifyAll();
            return;
        }

        // Cambiar turno
        turnoActual = defensor;
        notifyAll();
    }

    private void dormirSinInterrumpir(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Hechizo seleccionarHechizoAleatorio() {
        if (hechizosDisponibles.isEmpty()) {
            return new Hechizo("Golpe de varita", 5);
        }
        return hechizosDisponibles.get(random.nextInt(hechizosDisponibles.size()));
    }

    private int calcularPuntos(Hechizo hechizo) {
        // Variación +/- 50% del poder base para darle dinamismo
        int base = hechizo.getPoderBase();
        int variacion = (int) Math.round(base * (random.nextDouble() - 0.5));
        int total = Math.max(0, base + variacion);
        return total;
    }

    /**
     * @return si ya existe un ganador del duelo
     */
    public synchronized boolean hayGanador() {
        return hayGanador;
    }

    /**
     * @return primer mago
     */
    public Mago getMagoA() {
        return magoA;
    }

    /**
     * @return segundo mago
     */
    public Mago getMagoB() {
        return magoB;
    }

    private void notificarTurno(Mago atacante, Mago defensor) {
        for (DueloListener l : listeners) {
            l.onTurno(this, atacante, defensor);
        }
    }

    private void notificarHechizo(Mago atacante, Mago defensor, Hechizo hechizo, int puntos) {
        for (DueloListener l : listeners) {
            l.onHechizoLanzado(this, atacante, defensor, hechizo, puntos);
        }
    }

    private void notificarPuntajes() {
        for (DueloListener l : listeners) {
            l.onPuntajesActualizados(this, magoA, magoB);
        }
    }

    private void notificarGanador(Mago ganador, Mago perdedor) {
        for (DueloListener l : listeners) {
            l.onGanador(this, ganador, perdedor);
        }
    }

    private void notificarMagoAturdido(Mago mago) {
        for (DueloListener l : listeners) {
            l.onMagoAturdido(this, mago);
        }
    }
}


