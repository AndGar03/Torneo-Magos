package udistrital.avanzada.duelomagos.controlador;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Punto de entrada de la aplicación. Inicializa LAF y delega al controlador.
 */
public final class Launcher {

    private Launcher() { }

    /**
     * Método main.
     * @param args argumentos
     */
    public static void main(String[] args) {
        configurarLookAndFeel();
        ControladorPrincipal.launch();
    }

    private static void configurarLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            // Si falla Nimbus, se usa el LAF por defecto sin interrumpir la app
        }
    }
}


