# 🎭 Juego Memes 8M

Proyecto desarrollado en el módulo **DAW1** como parte de la asignatura.  
Un juego por consola en el que el jugador debe identificar la realidad verdadera asociada a un meme del 8M, eligiendo entre varias opciones.

---

## 📋 Descripción

El programa carga una serie de memes junto con sus realidades (verdadera y falsas) desde ficheros de datos en distintos formatos (TXT, JSON, XML). El jugador elige el número de rondas, visualiza cada meme con sus opciones, selecciona la que considera verdadera y ve su marcador actualizado tras cada ronda. Al finalizar, se muestra la puntuación final y queda registrada en un ranking.

---

## 🗂️ Estructura del proyecto
```
ProyectoMemes8M/
├── datos/
│   ├── memes.txt          # Lista de memes
│   ├── realidades.json    # Memes con sus realidades y opciones falsas
│   └── soluciones.xml     # Soluciones correctas indexadas por ID
├── documentacion/         # Manuales del proyecto
├── fuentes/               # Código fuente Java
├── resultados/
│   └── resultados.txt     # Ranking de puntuaciones
├── test/                  # Pruebas unitarias
├── Backlog.txt            # Historias de usuario y tareas
├── ComoUsar.txt           # Instrucciones de uso
└── Herramientas.txt       # Herramientas utilizadas
```

---

## ⚙️ Requisitos

- **Java** 11 o superior
- Librería **org.json** para el parseo de JSON
- Los ficheros `memes.txt`, `realidades.json` y `soluciones.xml` deben estar presentes en la carpeta `datos/`

---

## 🚀 Cómo ejecutar

1. Clona el repositorio:
```bash
git clone https://github.com/VictorRepollet/ProyectoMemes8M.git
```

2. Compila los fuentes desde la carpeta `fuentes/`:
```bash
javac -cp ".;ruta/a/org.json.jar" *.java
```

3. Ejecuta el programa:
```bash
java -cp ".;ruta/a/org.json.jar" JuegoMemes
```

> En Linux/Mac usa `:` en lugar de `;` en el classpath.

---

## 🎮 Cómo se juega

1. El programa verifica que todos los ficheros necesarios estén presentes.
2. El jugador elige cuántas rondas quiere jugar (entre 5 y 10).
3. En cada ronda se muestra un meme con varias opciones de realidad mezcladas.
4. El jugador elige la opción que considera verdadera.
5. Se muestra si ha acertado o fallado, y el marcador actualizado.
6. Al finalizar todas las rondas, se muestra la puntuación final.
7. El jugador introduce su nombre y la puntuación queda guardada en el ranking.

---

## 📁 Clases principales

| Clase | Responsabilidad |
|---|---|
| `JuegoMemes` | Punto de entrada y flujo principal del programa |
| `ComprobadorFicheros` | Verifica la existencia de los ficheros necesarios |
| `LeerFicheros` | Lectura, escritura de ficheros y lógica del juego |
| `Memes` | Modelo de datos básico de un meme |
| `MemesRealidades` | Modelo extendido con realidad, referencias y opciones falsas |

---

## 📌 Backlog

| ID | Historia de usuario | Estado |
|---|---|---|
| HU1 | Comprobar existencia de fichero datos | ✅ Completada |
| HU2 | Comprobar existencia de fichero resultados | ✅ Completada |
| HU3 | Leer fichero memes y generar estructura | ✅ Completada |
| HU4 | Leer fichero realidades y generar estructura | ✅ Completada |
| HU5 | Mostrar meme y listar realidades | ✅ Completada |
| HU6 | Elegir dato real | ✅ Completada |
| HU7 | Mostrar marcador | ✅ Completada |
| HU8 | Mostrar puntuación final | ✅ Completada |
| HU9 | Top 3 | ⏳ Pendiente |
| HU10 | Mostrar mejores puntuaciones | ⏳ Pendiente |

---

## 👥 Autores

- **Víctor** 
- **Alejandro** 
- **Javier** 

---

## 📄 Licencia

Proyecto académico desarrollado para **DAW1**. Sin licencia comercial.
