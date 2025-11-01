package udistrital.avanzada.duelomagos.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import udistrital.avanzada.duelomagos.modelo.Hechizo;
import udistrital.avanzada.duelomagos.modelo.Mago;
import udistrital.avanzada.duelomagos.modelo.util.CargadorProperties.MagoConfig;

/**
 * Panel que visualiza el estado del duelo estilo Pokémon.
 * Interfaz con imágenes de magos y mensajes individuales.
 */
public class PanelDuelo extends JPanel {

    /** Banner superior. */
    private final JLabel banner;
    /** Etiqueta de mago A. */
    private final JLabel lblMagoA;
    /** Etiqueta de mago B. */
    private final JLabel lblMagoB;
    /** Imagen central que alterna entre mago A y B */
    private final JLabel lblImagenCentral;
    /** Icono cache para mago A */
    private ImageIcon iconoMagoA;
    /** Icono cache para mago B */
    private ImageIcon iconoMagoB;
    /** Etiqueta de puntaje A. */
    private final JLabel lblPuntosA;
    /** Etiqueta de puntaje B. */
    private final JLabel lblPuntosB;
    /** Barra de progreso de A. */
    private final JProgressBar barA;
    /** Barra de progreso de B. */
    private final JProgressBar barB;
    /** Área de mensaje único (estilo Pokémon). */
    private final JTextArea txtMensaje;
    /** Referencias a los magos actuales para detectar turnos */
    private Mago magoActualA;
    private Mago magoActualB;

    /** Crea el panel con disposición estilo Pokémon. */
    public PanelDuelo() {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45));

        // Banner superior
        banner = new JLabel("⚔ TORNEO DE MAGOS ⚔", SwingConstants.CENTER);
        banner.setFont(banner.getFont().deriveFont(Font.BOLD, 24f));
        banner.setOpaque(true);
        banner.setBackground(new Color(60, 60, 100));
        banner.setForeground(new Color(255, 255, 200));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // HUD superior con nombres y puntos
        JPanel panelHud = new JPanel(new GridLayout(2, 2, 8, 8));
        panelHud.setBackground(new Color(45, 45, 45));
        panelHud.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        lblMagoA = new JLabel("Mago A", SwingConstants.LEFT);
        lblMagoA.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        lblMagoA.setForeground(Color.WHITE);
        lblMagoB = new JLabel("Mago B", SwingConstants.RIGHT);
        lblMagoB.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        lblMagoB.setForeground(Color.WHITE);
        lblPuntosA = new JLabel("Puntos A: 0", SwingConstants.LEFT);
        lblPuntosA.setForeground(Color.WHITE);
        lblPuntosB = new JLabel("Puntos B: 0", SwingConstants.RIGHT);
        lblPuntosB.setForeground(Color.WHITE);

        // Inicializar barras (no visibles en HUD, pero usadas por lógica existente)
        barA = new JProgressBar(0, 250);
        barB = new JProgressBar(0, 250);

        panelHud.add(lblMagoA);
        panelHud.add(lblMagoB);
        panelHud.add(lblPuntosA);
        panelHud.add(lblPuntosB);

        // Imagen central de duelo
        lblImagenCentral = new JLabel("", SwingConstants.CENTER);
        lblImagenCentral.setOpaque(true);
        lblImagenCentral.setBackground(new Color(35, 35, 35));
        lblImagenCentral.setForeground(Color.WHITE);
        lblImagenCentral.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        lblImagenCentral.setText("<HTML><div style='text-align:center;color:white;font-size:16px;padding:20px;'>IMAGEN DEL DUELO<br/><br/>Coloque imágenes en:<br/>resources/mago_a.png y resources/mago_b.png</div></HTML>");

        // Caja de mensaje estilo Pokémon (solo última línea visible)
        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.setBackground(new Color(30, 30, 30));
        panelMensaje.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        txtMensaje = new JTextArea(2, 50);
        txtMensaje.setEditable(false);
        txtMensaje.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        txtMensaje.setBackground(new Color(30, 30, 30));
        txtMensaje.setForeground(new Color(255, 255, 150));
        txtMensaje.setLineWrap(true);
        txtMensaje.setWrapStyleWord(true);
        txtMensaje.setText("¡Esperando el inicio del torneo...");
        
        panelMensaje.add(txtMensaje, BorderLayout.CENTER);

        // Layout final
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(new Color(45, 45, 45));
        panelCentro.add(panelHud, BorderLayout.NORTH);
        panelCentro.add(lblImagenCentral, BorderLayout.CENTER);

        add(banner, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelMensaje, BorderLayout.SOUTH);
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
        // No hacer nada porque se actualizará en mostrarDueloIniciado
    }

    /**
     * Muestra un mensaje informativo.
     * @param mensaje texto
     */
    public void mostrarMensaje(String mensaje) {
        txtMensaje.setText(mensaje);
    }

    /**
     * Cambia la imagen central según el mago que ataca.
     */
    private void alternarImagenPorTurno(Mago atacante) {
        boolean esMagoA = (magoActualA != null && atacante.getNombre().equals(magoActualA.getNombre()));
        if (esMagoA) {
            if (iconoMagoA != null && iconoMagoA.getIconWidth() > 0) {
                lblImagenCentral.setIcon(iconoMagoA);
                lblImagenCentral.setText("");
            } else {
                lblImagenCentral.setIcon(null);
                lblImagenCentral.setText("[Turno de " + atacante.getNombre() + "]\n(resources/mago_a.png no encontrado)");
            }
        } else {
            if (iconoMagoB != null && iconoMagoB.getIconWidth() > 0) {
                lblImagenCentral.setIcon(iconoMagoB);
                lblImagenCentral.setText("");
            } else {
                lblImagenCentral.setIcon(null);
                lblImagenCentral.setText("[Turno de " + atacante.getNombre() + "]\n(resources/mago_b.png no encontrado)");
            }
        }
    }

    /**
     * Muestra el turno actual.
     * @param atacante mago que ataca
     * @param defensor mago que defiende
     */
    public void mostrarTurno(Mago atacante, Mago defensor) {
        alternarImagenPorTurno(atacante);
        txtMensaje.setText("Turno de " + atacante.getNombre() + " contra " + defensor.getNombre());
    }

    /**
     * Muestra el hechizo lanzado y puntos obtenidos.
     */
    public void mostrarHechizo(Mago atacante, Mago defensor, Hechizo hechizo, int puntos) {
        txtMensaje.setText(atacante.getNombre() + " lanza " + hechizo.getNombre() + " y obtiene " + puntos + " puntos!");
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
        txtMensaje.setText("¡" + ganador.getNombre() + " GANA el duelo!");
        // Nada adicional; la imagen permanecerá en el último turno
    }

    /**
     * Muestra que inicia un nuevo duelo.
     */
    public void mostrarDueloIniciado(Mago magoA, Mago magoB) {
        // Guardar referencias para poder resaltar
        magoActualA = magoA;
        magoActualB = magoB;
        
        // Actualizar las etiquetas superiores con los magos que compiten ahora
        lblMagoA.setText(magoA.getNombre());
        lblMagoB.setText(magoB.getNombre());
        
        // Reiniciar UI de puntajes
        lblPuntosA.setText("Puntos A: 0");
        lblPuntosB.setText("Puntos B: 0");
        barA.setValue(0);
        barB.setValue(0);
        
        // Cargar imágenes si existen
        cargarImagenes(magoA.getNombre(), magoB.getNombre());
        
        // Mensaje inicial
        txtMensaje.setText("Comienza el duelo: " + magoA.getNombre() + " vs " + magoB.getNombre());
    }

    /**
     * Carga las imágenes de los magos si existen.
     */
    private void cargarImagenes(String nombreMagoA, String nombreMagoB) {
        // Intentar cargar imagen para mago A
        try {
            iconoMagoA = new ImageIcon("resources/mago_a.png");
        } catch (Exception e) {
            iconoMagoA = null;
        }
        
        // Intentar cargar imagen para mago B
        try {
            iconoMagoB = new ImageIcon("resources/mago_b.png");
        } catch (Exception e) {
            iconoMagoB = null;
        }
    }

    /**
     * Muestra que un mago está aturdido.
     */
    public void mostrarMagoAturdido(Mago mago) {
        txtMensaje.setText(">>> " + mago.getNombre() + " está ATURDIDO y no puede moverse <<<");
    }
}


