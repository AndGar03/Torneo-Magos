# 🎮 Nueva Interfaz Estilo Pokémon

## Cambios Implementados

La interfaz gráfica ha sido completamente rediseñada para emular el estilo de los juegos Pokémon de tercera generación.

### ✨ Características Nuevas

1. **Interfaz Visual Estilo Pokémon**
   - Dos paneles principales para cada mago con colores distintivos
   - Imágenes de los magos que se cargan automáticamente
   - Resaltado dinámico del mago cuyo turno es activo (borde amarillo)

2. **Sistema de Mensajes Mejorado**
   - Solo se muestra un mensaje a la vez (el último evento)
   - Caja de mensajes estilo Pokémon en la parte inferior
   - Texto legible con mejor estilo

3. **Barras de Progreso**
   - Barras de HP/Puntos mejoradas con etiquetas
   - Muestran "0/250" para mejor legibilidad
   - Colores distintivos para cada mago

## 📸 Agregar Imágenes de los Magos

### Ubicación de los Archivos

Coloque las imágenes en la siguiente ruta:
```
Torneo-Magos/resources/
├── mago_a.png   (Imagen del mago del lado izquierdo)
└── mago_b.png   (Imagen del mago del lado derecho)
```

### Especificaciones Recomendadas

- **Formato:** PNG
- **Tamaño:** 200x200 píxeles (o similar)
- **Transparencia:** Opcional pero recomendada
- **Escalado:** Automático

### Instrucciones

1. Cree o descargue imágenes para cada mago
2. Renombre las imágenes como `mago_a.png` y `mago_b.png`
3. Colóquelas en el directorio `Torneo-Magos/resources/`
4. Ejecute la aplicación - las imágenes se cargarán automáticamente

**Nota:** Si no se encuentran las imágenes, el programa mostrará un mensaje indicando dónde colocarlas.

## 🎨 Colores de la Interfaz

- **Mago Izquierdo (A):** Azul claro
- **Mago Derecho (B):** Rojo claro
- **Fondo:** Gris oscuro
- **Resaltado de turno:** Borde amarillo brillante
- **Mensajes:** Texto amarillo sobre fondo oscuro

## 📋 Estructura de la Interfaz

```
┌─────────────────────────────────────────┐
│     ⚔ TORNEO DE MAGOS ⚔                │ Banner Superior
├─────────────────────────────────────────┤
│                                         │
│  ┌──────────┐      ┌──────────┐        │
│  │ Mago A   │      │ Mago B   │        │ Nombres
│  ├──────────┤      ├──────────┤        │
│  │ Imagen   │      │ Imagen   │        │ Imágenes de los magos
│  │ Mago A   │      │ Mago B   │        │
│  ├──────────┤      ├──────────┤        │
│  │ Casa: X  │      │ Casa: Y  │        │ Casas mágicas
│  └──────────┘      └──────────┘        │
│                                         │
│  HP: [████████░░]  HP: [██████░░░░]    │ Barras de progreso
│                                         │
├─────────────────────────────────────────┤
│ ¿Qué hará Mago X?                       │ Caja de mensajes
└─────────────────────────────────────────┘
```

## 🔄 Comportamiento Dinámico

- Cuando es el turno de un mago, su panel se resalta con borde amarillo
- Los mensajes se actualizan en tiempo real mostrando el último evento
- Las barras de puntos se actualizan automáticamente
- Las imágenes se cargan al inicio de cada duelo

¡Disfrute de la nueva experiencia de duelo mágico!

