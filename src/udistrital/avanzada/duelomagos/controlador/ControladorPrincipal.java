package udistrital.avanzada.duelomagos.controlador;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import udistrital.avanzada.duelomagos.modelo.CampoDuelo;
import udistrital.avanzada.duelomagos.modelo.Hechizo;
import udistrital.avanzada.duelomagos.modelo.Mago;
import udistrital.avanzada.duelomagos.modelo.Torneo;
import udistrital.avanzada.duelomagos.modelo.util.CargadorProperties;
import udistrital.avanzada.duelomagos.modelo.util.CargadorProperties.MagoConfig;
import udistrital.avanzada.duelomagos.vista.PanelDuelo;
import udistrital.avanzada.duelomagos.vista.VistaPrincipal;

/**
 * Controlador principal: media entre la Vista y el Modelo. Maneja carga de datos,
 * orquesta el Torneo en un hilo separado y actualiza la vista con invokeLater.
 */
public class ControladorPrincipal implements DueloLanzadoListener {

    /**
     * Método estático para lanzar la aplicación.
     * Crea la vista, el controlador y muestra la ventana principal.
     */
    public static void launch() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VistaPrincipal vista = new VistaPrincipal();
            new ControladorPrincipal(vista);
            vista.setVisible(true);
        });
    }

    /** Vista principal. */
    private final VistaPrincipal vista;
    /** Archivo de propiedades seleccionado por el usuario. */
    private File archivoProperties;
    /** Hechizos configurados. */
    private List<Hechizo> hechizos;
    /** Configuraciones de magos. */
    private List<MagoConfig> magosConfig;

    /**
     * Crea el controlador y enlaza los eventos de la vista.
     * @param vista vista principal
     */
    public ControladorPrincipal(VistaPrincipal vista) {
        this.vista = vista;
        this.vista.setControlador(this);
        this.hechizos = new ArrayList<>();
        this.magosConfig = new ArrayList<>();
    }

    /**
     * Abre un JFileChooser para seleccionar un archivo .properties y carga la configuración.
     */
    public void seleccionarYcargarProperties() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(vista);
        if (result == JFileChooser.APPROVE_OPTION) {
            this.archivoProperties = chooser.getSelectedFile();
            try {
                Properties props = CargadorProperties.cargar(archivoProperties);
                this.magosConfig = CargadorProperties.construirMagosConfig(props);
                this.hechizos = CargadorProperties.construirHechizos(props);
                
                // Validar datos cargados
                if (magosConfig.size() < 2 || hechizos.isEmpty()) {
                    vista.getPanelDuelo().mostrarMensaje("Advertencia: Archivo cargado pero incompleto. Se requiere al menos 2 magos y 1 hechizo válido.");
                    this.archivoProperties = null; // Marcar como no válido
                } else {
                    vista.getPanelDuelo().mostrarMensaje("Archivo cargado correctamente: " + archivoProperties.getName());
                    vista.getPanelDuelo().mostrarParticipantes(magosConfig);
                    vista.getPanelDuelo().configurarPuntajeMax(udistrital.avanzada.duelomagos.modelo.CampoDuelo.PUNTAJE_VICTORIA);
                }
            } catch (IOException ex) {
                vista.getPanelDuelo().mostrarMensaje("Error al cargar propiedades: " + ex.getMessage());
                this.archivoProperties = null; // Limpiar referencia en caso de error
            }
        }
    }

    /**
     * Inicia el torneo en un hilo separado para no bloquear el EDT.
     */
    public void iniciarTorneo() {
        if (archivoProperties == null) {
            vista.getPanelDuelo().mostrarMensaje("Debe cargar un archivo properties primero.");
            return;
        }
        if (magosConfig.size() < 2) {
            vista.getPanelDuelo().mostrarMensaje("Error: El archivo properties debe contener al menos dos magos válidos.");
            return;
        }
        if (hechizos.isEmpty()) {
            vista.getPanelDuelo().mostrarMensaje("Error: El archivo properties debe contener al menos un hechizo válido.");
            return;
        }
        Thread hilo = new Thread(() -> ejecutarTorneo(), "Hilo-Torneo");
        hilo.start();
    }

    private void ejecutarTorneo() {
        Torneo torneo = new Torneo(hechizos);
        torneo.addListener(this);
        List<Mago> participantes = new ArrayList<>();
        for (MagoConfig cfg : magosConfig) {
            participantes.add(new Mago(cfg.getNombre(), cfg.getCasa(), null));
        }
        Mago campeon = torneo.iniciarTorneo(participantes);
        SwingUtilities.invokeLater(() -> vista.getPanelDuelo().mostrarMensaje("Campeon: " + campeon.getNombre()));
    }

    // Implementaciones de eventos del Modelo, asegurando actualización en EDT

    @Override
    public void onTurno(CampoDuelo campo, Mago atacante, Mago defensor) {
        SwingUtilities.invokeLater(() -> vista.getPanelDuelo().mostrarTurno(atacante, defensor));
    }

    @Override
    public void onHechizoLanzado(CampoDuelo campo, Mago atacante, Mago defensor, Hechizo hechizo, int puntosGanados) {
        SwingUtilities.invokeLater(() -> vista.getPanelDuelo().mostrarHechizo(atacante, defensor, hechizo, puntosGanados));
    }

    @Override
    public void onPuntajesActualizados(CampoDuelo campo, Mago magoA, Mago magoB) {
        SwingUtilities.invokeLater(() -> vista.getPanelDuelo().actualizarPuntajes(magoA, magoB));
    }

    @Override
    public void onGanador(CampoDuelo campo, Mago ganador, Mago perdedor) {
        SwingUtilities.invokeLater(() -> vista.getPanelDuelo().mostrarGanador(ganador, perdedor));
    }

    /**
     * @return panel de duelo asociado a la vista
     */
    public PanelDuelo getPanelDuelo() {
        return vista.getPanelDuelo();
    }
}


