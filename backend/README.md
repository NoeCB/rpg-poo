# 💀 Dead by Daylight: The Board Game (RPG Engine)

![Java](https://img.shields.io/badge/Language-Java-orange?style=flat-square)
![Type](https://img.shields.io/badge/Project-University-blue?style=flat-square)
![Status](https://img.shields.io/badge/Status-Development-green?style=flat-square)

> **Un motor de combate RPG por turnos basado en las reglas del juego de mesa oficial de Dead by Daylight.**
>
> *Proyecto académico para la asignatura de Programación Orientada a Objetos.*

---

## 📖 Descripción del Proyecto

Este proyecto implementa un motor de combate **3 contra 3** simulando una "Prueba" (Trial) del Ente. A diferencia de un RPG tradicional donde el objetivo es simplemente eliminar al enemigo, aquí adaptamos las mecánicas de **Supervivencia y Reparación** del juego de mesa oficial.

El sistema está construido siguiendo estrictos principios de **POO (Programación Orientada a Objetos)**, destacando el uso de herencia, polimorfismo y gestión de estados persistentes.

---

## ⚙️ Arquitectura y Diseño Técnico

El núcleo del proyecto se basa en una jerarquía de clases robusta que separa la lógica del motor de las entidades concretas.

### 1. Jerarquía de Personajes (`Personaje`)
Todos los combatientes heredan de una clase base abstracta, permitiendo al motor tratarlos de forma polimórfica.

* **🛡️ Equipo Superviviente (Héroes):**
    * **Líder (Dwight):** Especialista en objetivos (Generadores). Rol equivalente a *Guerrero/Tanque*. Su función es "tanquear" los Skill Checks.
    * **Corredor (Meg):** Especialista en movilidad y evasión. Rol equivalente a *Mago/Pícaro*. Usa cartas de movimiento para evitar daño.
    * **Botánico (Claudette):** Especialista en curación y altruismo. Rol equivalente a *Sacerdote/Soporte*.
* **🔪 La Oposición (Enemigos):**
    * **El Asesino (The Trapper):** Daño físico directo y control de zona mediante trampas.
    * **El Ente (The Entity):** Enemigo ambiental que bloquea caminos y aplica estados negativos.
    * **Tótem de Maleficio:** Unidad estática que aplica debuffs globales (ej. Ruina) hasta ser purificada.

### 2. Sistema de Objetos y Armas (`Arma`)
Adaptación del requisito de armas al contexto de DbD:
* **Cuerpo a Cuerpo:** Cajas de Herramientas (Daño a generadores) y Armas de Asesino.
* **A Distancia:** Linternas (Cegadora) y Botiquines (Proyectiles de curación).

### 3. Motor de Estados Persistentes (`Estado`)
Implementación de efectos que perduran varios turnos y se procesan automáticamente al final de cada ronda:
* 🩸 **Herida (Hemorragia):** Daño por turno (DoT).
* 💉 **Adrenalina (Regeneración):** Curación por turno (HoT).
* 🧠 **Locura:** Alteración de estadísticas (Debuff del Doctor).

---

## 🎲 Mecánicas de Juego (Adaptación Board Game)

Hemos trasladado las reglas del tablero físico a lógica de consola en Java:

### 🔧 Reparación vs Sacrificio
* **Victoria Superviviente:** Deben reducir los "Puntos de Reparación" de los Generadores a 0.
* **Victoria Asesino:** Debe reducir la vida de los Supervivientes a 0 (Sacrificio).

### 🎲 Skill Checks (Chequeos de Habilidad)
Implementación de la mecánica de dados del juego de mesa mediante la clase `DadoSkillCheck`:
* **Fallo (1):** El generador explota (Daño al jugador y regresión del generador).
* **Éxito (2-5):** Progreso normal de reparación.
* **Gran Éxito (6):** Doble progreso / Crítico.

### 🏃 Cartas de Movimiento como Posturas
Cada turno, el jugador elige una "Carta de Movimiento" que actúa como una postura defensiva:
* **Sprint:** Aumenta ataque/reparación, reduce defensa.
* **Sigilo (Crouch):** Aumenta evasión, reduce velocidad de acción.

---

## 📂 Estructura del Proyecto

```bash
src/
├── com.dbd.core        # Motor del juego (Bucle de combate, Rondas)
├── com.dbd.entidades   # Clases Personaje, Superviviente, Asesino
├── com.dbd.objetos     # Jerarquía de Armas (Items) y Herramientas
├── com.dbd.efectos     # Lógica de Estados (DoT, HoT, Buffs)
└── com.dbd.mechanics   # Dados, SkillChecks y Lógica de Tablero
