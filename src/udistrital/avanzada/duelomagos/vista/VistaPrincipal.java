package udistrital.avanzada.duelomagos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import udistrital.avanzada.duelomagos.controlador.ControladorPrincipal;

/**
 * Ventana principal de la aplicación.
 */
public class VistaPrincipal extends JFrame {

    /** Panel central para visualizar el duelo. */
    private final PanelDuelo panelDuelo;
    /** Botón para cargar archivo properties. */
    private final JButton btnCargar;
    /** Botón para iniciar el torneo. */
    private final JButton btnIniciar;
    /** Controlador asignado. */
    private ControladorPrincipal controlador;

    /** Crea la vista principal. */
    public VistaPrincipal() {
        super("Duelo de Magos - Taller 3");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        panelDuelo = new PanelDuelo();
        add(panelDuelo, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(240, 240, 250));
        btnCargar = new JButton("Cargar Properties...");
        btnIniciar = new JButton("Iniciar Torneo");
        btnCargar.setBackground(new Color(63, 81, 181));
        btnCargar.setForeground(Color.WHITE);
        btnIniciar.setBackground(new Color(0, 150, 136));
        btnIniciar.setForeground(Color.WHITE);
        panelBotones.add(btnCargar);
        panelBotones.add(btnIniciar);
        add(panelBotones, BorderLayout.SOUTH);

        panelDuelo.setPreferredSize(new Dimension(760, 520));
        pack();
        setLocationRelativeTo(null);

        inicializarEventos();
    }

    private void inicializarEventos() {
        btnCargar.addActionListener(_ -> {
            if (controlador != null) {
                controlador.seleccionarYcargarProperties();
            }
        });
        btnIniciar.addActionListener(_ -> {
            if (controlador != null) {
                controlador.iniciarTorneo();
            }
        });
    }

    /**
     * Asigna el controlador a la vista.
     * @param controlador instancia del controlador
     */
    public void setControlador(ControladorPrincipal controlador) {
        this.controlador = controlador;
    }

    /**
     * @return panel de duelo
     */
    public PanelDuelo getPanelDuelo() {
        return panelDuelo;
    }

    /** Punto de entrada alterno para probar vista aislada. */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VistaPrincipal().setVisible(true));
    }
}


