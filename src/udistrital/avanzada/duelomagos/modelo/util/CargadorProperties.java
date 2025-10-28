package udistrital.avanzada.duelomagos.modelo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import udistrital.avanzada.duelomagos.modelo.Hechizo;

/**
 * Utilidad para cargar la configuración del torneo desde un archivo .properties.
 * No usa clases Swing; el Controlador debe proporcionar el archivo seleccionado
 * (OCP: sin rutas quemadas y sin dependencia de UI).
 */
public final class CargadorProperties {

    private CargadorProperties() { }

    /**
     * Contenedor inmutable para configuración de un mago (sin dependencias del modelo).
     */
    public static final class MagoConfig {
        private final String nombre;
        private final String casa;
        public MagoConfig(String nombre, String casa) {
            this.nombre = Objects.requireNonNull(nombre);
            this.casa = Objects.requireNonNull(casa);
        }
        public String getNombre() { return nombre; }
        public String getCasa() { return casa; }
    }

    /**
     * Carga las propiedades desde un archivo.
     * @param file archivo .properties
     * @return instancia de Properties
     * @throws IOException si hay errores de lectura
     */
    public static Properties cargar(File file) throws IOException {
        Objects.requireNonNull(file, "El archivo no puede ser nulo");
        try (InputStream in = new FileInputStream(file)) {
            Properties props = new Properties();
            props.load(in);
            return props;
        }
    }

    /**
     * Construye la lista de configuraciones de magos a partir de las propiedades.
     * @param props propiedades
     * @return lista de configuraciones de magos
     */
    public static List<MagoConfig> construirMagosConfig(Properties props) {
        int count = Integer.parseInt(props.getProperty("magos.count", "0"));
        List<MagoConfig> magos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String nombre = props.getProperty("magos." + i + ".nombre", "Mago" + i);
            String casa = props.getProperty("magos." + i + ".casa", "Casa" + i);
            magos.add(new MagoConfig(nombre, casa));
        }
        if (magos.isEmpty()) {
            magos.add(new MagoConfig("Mago A", "Roja"));
            magos.add(new MagoConfig("Mago B", "Azul"));
        }
        return magos;
    }

    /**
     * Construye la lista de hechizos a partir de las propiedades.
     * @param props propiedades
     * @return lista de hechizos
     */
    public static List<Hechizo> construirHechizos(Properties props) {
        int count = Integer.parseInt(props.getProperty("hechizos.count", "0"));
        List<Hechizo> hechizos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String nombre = props.getProperty("hechizos." + i + ".nombre", "Hechizo" + i);
            int poder = Integer.parseInt(props.getProperty("hechizos." + i + ".poder", "30"));
            hechizos.add(new Hechizo(nombre, poder));
        }
        if (hechizos.isEmpty()) {
            hechizos.add(new Hechizo("Chispa", 20));
            hechizos.add(new Hechizo("Onda", 25));
        }
        return hechizos;
    }
}


