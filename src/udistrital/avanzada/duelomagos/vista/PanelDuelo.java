package udistrital.avanzada.duelomagos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import udistrital.avanzada.duelomagos.modelo.Hechizo;
import udistrital.avanzada.duelomagos.modelo.Mago;
import udistrital.avanzada.duelomagos.modelo.util.CargadorProperties.MagoConfig;

/**
 * Panel que visualiza el estado del duelo y un registro textual de eventos.
 * La vista es "tonta": solo muestra datos provistos por el controlador.
 */
public class PanelDuelo extends JPanel {

    /** Banner superior. */
    private final JLabel banner;
    /** Etiqueta de mago A. */
    private final JLabel lblMagoA;
    /** Etiqueta de mago B. */
    private final JLabel lblMagoB;
    /** Etiqueta de puntaje A. */
    private final JLabel lblPuntosA;
    /** Etiqueta de puntaje B. */
    private final JLabel lblPuntosB;
    /** Barra de progreso de A. */
    private final JProgressBar barA;
    /** Barra de progreso de B. */
    private final JProgressBar barB;
    /** Área de log de eventos. */
    private final JTextArea txtLog;

    /** Crea el panel con disposición y estilos mejorados. */
    public PanelDuelo() {
        setLayout(new BorderLayout(8, 8));

        banner = new JLabel("Duelo de Magos", SwingConstants.CENTER);
        banner.setFont(banner.getFont().deriveFont(Font.BOLD, 22f));
        banner.setOpaque(true);
        banner.setBackground(new Color(30, 30, 60));
        banner.setForeground(new Color(220, 220, 255));
        banner.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel panelTop = new JPanel(new GridLayout(3, 2, 8, 8));
        panelTop.setBorder(BorderFactory.createTitledBorder("Participantes"));
        lblMagoA = new JLabel("Mago A", SwingConstants.CENTER);
        lblMagoB = new JLabel("Mago B", SwingConstants.CENTER);
        lblMagoA.setFont(lblMagoA.getFont().deriveFont(Font.BOLD));
        lblMagoB.setFont(lblMagoB.getFont().deriveFont(Font.BOLD));
        lblPuntosA = new JLabel("Puntos A: 0", SwingConstants.CENTER);
        lblPuntosB = new JLabel("Puntos B: 0", SwingConstants.CENTER);
        barA = new JProgressBar(0, 250);
        barB = new JProgressBar(0, 250);
        barA.setForeground(new Color(76, 175, 80));
        barB.setForeground(new Color(244, 67, 54));
        panelTop.add(lblMagoA);
        panelTop.add(lblMagoB);
        panelTop.add(lblPuntosA);
        panelTop.add(lblPuntosB);
        panelTop.add(barA);
        panelTop.add(barB);

        txtLog = new JTextArea(14, 50);
        txtLog.setEditable(false);
        txtLog.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(txtLog);
        scroll.setBorder(BorderFactory.createTitledBorder("Eventos"));

        add(banner, BorderLayout.NORTH);
        add(panelTop, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    /** Configura el puntaje máximo para las barras de progreso. */
    public void configurarPuntajeMax(int max) {
        barA.setMaximum(max);
        barB.setMaximum(max);
    }

    /**
     * Muestra participantes obtenidos del archivo de propiedades.
     * @param magos lista de configuraciones de magos
     */
    public void mostrarParticipantes(List<MagoConfig> magos) {
        if (magos.size() >= 1) {
            lblMagoA.setText(magos.get(0).getNombre() + " (" + magos.get(0).getCasa() + ")");
        }
        if (magos.size() >= 2) {
            lblMagoB.setText(magos.get(1).getNombre() + " (" + magos.get(1).getCasa() + ")");
        }
        txtLog.append("Participantes actualizados\n");
    }

    /**
     * Muestra un mensaje informativo en el log.
     * @param mensaje texto
     */
    public void mostrarMensaje(String mensaje) {
        txtLog.append(mensaje + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Muestra el turno actual.
     * @param atacante mago que ataca
     * @param defensor mago que defiende
     */
    public void mostrarTurno(Mago atacante, Mago defensor) {
        txtLog.append("Turno de " + atacante.getNombre() + " contra " + defensor.getNombre() + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Muestra el hechizo lanzado y puntos obtenidos.
     */
    public void mostrarHechizo(Mago atacante, Mago defensor, Hechizo hechizo, int puntos) {
        txtLog.append(atacante.getNombre() + " lanza " + hechizo.getNombre() + " (" + puntos + " pts)\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Actualiza las etiquetas y barras de puntajes acumulados.
     */
    public void actualizarPuntajes(Mago magoA, Mago magoB) {
        lblPuntosA.setText("Puntos A: " + magoA.getPuntosAcumulados());
        lblPuntosB.setText("Puntos B: " + magoB.getPuntosAcumulados());
        barA.setValue(magoA.getPuntosAcumulados());
        barB.setValue(magoB.getPuntosAcumulados());
    }

    /**
     * Muestra el ganador del duelo actual.
     */
    public void mostrarGanador(Mago ganador, Mago perdedor) {
        txtLog.append("Ganador del duelo: " + ganador.getNombre() + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Muestra que inicia un nuevo duelo con separador visual.
     */
    public void mostrarDueloIniciado(Mago magoA, Mago magoB) {
        // Actualizar las etiquetas superiores con los magos que compiten ahora
        lblMagoA.setText(magoA.getNombre() + " (" + magoA.getCasaMagica() + ")");
        lblMagoB.setText(magoB.getNombre() + " (" + magoB.getCasaMagica() + ")");
        
        // Reiniciar los puntajes mostrados
        lblPuntosA.setText("Puntos A: 0");
        lblPuntosB.setText("Puntos B: 0");
        barA.setValue(0);
        barB.setValue(0);
        
        // Agregar separador visual en el log
        txtLog.append("\n" + "=".repeat(80) + "\n");
        txtLog.append("DUELO: " + magoA.getNombre() + " vs " + magoB.getNombre() + "\n");
        txtLog.append("=".repeat(80) + "\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }

    /**
     * Muestra que un mago está aturdido.
     */
    public void mostrarMagoAturdido(Mago mago) {
        txtLog.append(">>> " + mago.getNombre() + " está ATURDIDO y no puede moverse <<<\n");
        txtLog.setCaretPosition(txtLog.getDocument().getLength());
    }
}


