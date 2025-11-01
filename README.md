# Torneo de Magos

Un simulador de torneo de duelos mágicos con interfaz gráfica estilo Game Boy Advance, desarrollado en Java.

## Descripción

Este proyecto simula un torneo de duelos entre magos donde los participantes se enfrentan en combates por turnos. El último mago en pie se corona como campeón. La aplicación cuenta con una interfaz gráfica inspirada en los juegos Pokémon de tercera generación, donde las imágenes de los magos se alternan según el turno y los eventos se muestran en una caja de mensajes.

## Características

- **Interfaz estilo GBA**: Pantalla completa con imagen central que alterna según el turno
- **Sistema de turnos**: Cada mago lanza hechizos por turno con tiempos de espera realistas
- **Configuración flexible**: Carga de magos y hechizos desde archivos `.properties`
- **Barras de progreso**: Seguimiento visual de los puntos acumulados
- **Sistema de estatus**: Notificaciones cuando un mago está aturdido
- **Separadores visuales**: Distinción clara entre diferentes duelos del torneo

## Requisitos

- **Java**: JDK 17 o superior (recomendado Java 21)
- **Sistema Operativo**: Windows, Linux o macOS

## Instalación

1. Clona o descarga este repositorio
2. Asegúrate de tener Java instalado:
   ```bash
   java -version
   ```
3. Compila el proyecto:
   ```bash
   javac -d build/classes -encoding UTF-8 -sourcepath src src/udistrital/avanzada/duelomagos/**/*.java
   ```

## Uso

### Ejecutar la aplicación

```bash
java -cp build/classes udistrital.avanzada.duelomagos.controlador.Launcher
```

### Configurar el torneo

1. **Cargar archivo properties**: 
   - Haz clic en "Cargar Properties..."
   - Selecciona el archivo `torneo.properties` (incluido en el proyecto)

2. **Iniciar el torneo**:
   - Haz clic en "Iniciar Torneo"
   - Observa cómo los magos compiten hasta encontrar un campeón

### Archivo de configuración

El archivo `torneo.properties` permite configurar:

#### Magos

```properties
magos.count=8
magos.0.nombre=Harry Potter
magos.0.casa=Gryffindor
magos.1.nombre=Draco Malfoy
magos.1.casa=Slytherin
# ... más magos
```

#### Hechizos

```properties
hechizos.count=12
hechizos.0.nombre=Expelliarmus
hechizos.0.poder=45
hechizos.1.nombre=Petrificus Totalus
hechizos.1.poder=35
# ... más hechizos
```

**Requisitos mínimos:**
- Al menos 2 magos
- Al menos 1 hechizo

## Personalización

### Agregar imágenes de magos

Para que las imágenes se muestren en la interfaz:

1. Crea un directorio `resources/` en la raíz del proyecto (si no existe)
2. Coloca las imágenes con estos nombres:
   - `resources/mago_a.png` - Imagen para el mago del lado izquierdo
   - `resources/mago_b.png` - Imagen para el mago del lado derecho

**Especificaciones recomendadas:**
- Formato: PNG con transparencia
- Tamaño: 200x200 píxeles o similar
- Las imágenes se escalarán automáticamente

Si las imágenes no se encuentran, el programa mostrará un mensaje indicando dónde colocarlas.

## Estructura del Proyecto

```
Torneo-Magos/
├── src/
│   └── udistrital/avanzada/duelomagos/
│       ├── controlador/      # Lógica de control y eventos
│       ├── modelo/           # Lógica de negocio (Mago, Hechizo, Torneo)
│       └── vista/            # Interfaz gráfica
├── resources/                # Imágenes de magos (opcional)
├── build/                    # Archivos compilados
├── torneo.properties         # Configuración del torneo
└── README.md                 # Este archivo
```

## Reglas del Torneo

1. **Formato**: Sistema de eliminación directa
   - Los magos se enfrentan en parejas
   - El ganador avanza a la siguiente ronda
   - El último mago en pie es el campeón

2. **Puntos de Victoria**: 250 puntos
   - El primer mago en alcanzar 250 puntos gana el duelo
   - Si ningún mago alcanza 250, gana el que tenga más puntos

3. **Sistema de Turnos**:
   - Cada turno dura aproximadamente 1 segundo
   - Los hechizos tienen poder variable (±50%)
   - Los magos pueden quedar aturdidos temporalmente

4. **Reinicio de Puntos**: 
   - Los puntos se reinician al inicio de cada nuevo duelo
   - Esto asegura que todos los duelos empiecen en igualdad de condiciones

## Solución de Problemas

### Error de versión de Java

Si ves un error como:
```
UnsupportedClassVersionError: class file version 69.0
```

**Solución**: Asegúrate de usar la misma versión de Java para compilar y ejecutar, o compila con una versión compatible.

### No se muestran las imágenes

- Verifica que el directorio `resources/` existe en la raíz del proyecto
- Asegúrate de que los archivos se llaman exactamente `mago_a.png` y `mago_b.png`
- Las imágenes deben estar en formato PNG

### El torneo no inicia

- Verifica que el archivo `.properties` tenga al menos 2 magos válidos
- Asegúrate de que haya al menos 1 hechizo válido
- Revisa que los nombres y valores no estén vacíos

## Créditos

Desarrollado como proyecto educativo para demostrar:
- Programación orientada a objetos
- Patrones de diseño (Observer, MVC)
- Concurrencia en Java (Threads, sincronización)
- Interfaz gráfica con Swing

## Licencia

Este proyecto es de uso educativo.
