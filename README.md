# 💀 Dead by Daylight: The Board Game (RPG Engine)

![Java](https://img.shields.io/badge/Language-Java-orange)
![Type](https://img.shields.io/badge/Project-University-blue)
![Status](https://img.shields.io/badge/Status-Development-green)

> **Un motor de combate RPG por turnos basado en las reglas del juego de mesa oficial de Dead by Daylight.**
>
> *Proyecto académico para la asignatura de Programación Orientada a Objetos.*

---

## 📖 Descripción del Proyecto

Este proyecto implementa un motor de combate **3 contra 3** simulando una "Prueba" (Trial) del Ente. [cite_start]A diferencia de un RPG tradicional donde el objetivo es simplemente eliminar al enemigo, aquí adaptamos las mecánicas de **Supervivencia y Reparación** del juego de mesa oficial[cite: 469, 488].

[cite_start]El sistema está construido siguiendo estrictos principios de **POO (Programación Orientada a Objetos)**, destacando el uso de herencia, polimorfismo y gestión de estados persistentes[cite: 3, 20].

---

## ⚙️ Arquitectura y Diseño Técnico

El núcleo del proyecto se basa en una jerarquía de clases robusta que separa la lógica del motor de las entidades concretas.

### 1. Jerarquía de Personajes (`Personaje`)
[cite_start]Todos los combatientes heredan de una clase base abstracta, permitiendo al motor tratarlos de forma polimórfica[cite: 99, 102].

* **🛡️ Supervivientes (Héroes):**
    * [cite_start]**Líder (Dwight):** Especialista en objetivos (Generadores)[cite: 543]. Rol equivalente a *Guerrero/Tanque*.
    * [cite_start]**Corredor (Meg):** Especialista en movilidad y evasión[cite: 657]. Rol equivalente a *Mago/Pícaro*.
    * [cite_start]**Botánico (Claudette):** Especialista en curación y altruismo[cite: 672]. Rol equivalente a *Sacerdote/Soporte*.
* **🔪 La Oposición (Enemigos):**
    * [cite_start]**El Asesino (The Trapper):** Daño físico directo y control de zona[cite: 507].
    * **El Ente (The Entity):** Enemigo ambiental que bloquea caminos y aplica estados.
    * **Tótem de Maleficio:** Unidad estática que aplica debuffs globales.

### 2. Sistema de Objetos y Armas (`Arma`)
[cite_start]Adaptación del requisito de armas [cite: 214] al contexto de DbD:
* **Cuerpo a Cuerpo:** Cajas de Herramientas (Daño a generadores) y Armas de Asesino.
* [cite_start]**A Distancia:** Linternas (Cegadora) y Botiquines (Proyectiles de curación)[cite: 579].

### 3. Motor de Estados Persistentes (`Estado`)
[cite_start]Implementación de efectos que perduran varios turnos[cite: 9, 356]:
* 🩸 **Herida (Hemorragia):** Daño por turno (DoT).
* 💉 **Adrenalina (Regeneración):** Curación por turno (HoT).
* [cite_start]🧠 **Locura:** Alteración de estadísticas (Debuff del Doctor)[cite: 523].

---

## 🎲 Mecánicas de Juego (Adaptación Board Game)

Hemos trasladado las reglas del tablero físico a lógica de consola:

### 🔧 Reparación vs Sacrificio
* Los Supervivientes ganan si reducen los "Puntos de Reparación" de los Generadores a 0.
* [cite_start]El Asesino gana si reduce la vida de los Supervivientes a 0 (Sacrificio)[cite: 613, 615].

### 🎲 Skill Checks (Chequeos de Habilidad)
[cite_start]Implementación de la mecánica de dados del juego de mesa[cite: 526, 900]:
* **Fallo (1):** El generador explota (Daño al jugador).
* **Éxito (2-5):** Progreso normal.
* **Gran Éxito (6):** Doble progreso / Crítico.

### 🏃 Cartas de Movimiento como Posturas
[cite_start]Cada turno, el jugador elige una "Carta de Movimiento" que actúa como una postura defensiva[cite: 588]:
* **Sprint:** Aumenta ataque/reparación, reduce defensa.
* **Sigilo (Crouch):** Aumenta evasión, reduce velocidad.

---

## 📂 Estructura del Proyecto

```bash
src/
├── com.dbd.core        # Motor del juego (Bucle de combate, Rondas)
├── com.dbd.entidades   # Clases Personaje, Superviviente, Asesino
├── com.dbd.objetos     # Jerarquía de Armas (Items) y Herramientas
├── com.dbd.efectos     # Lógica de Estados (DoT, HoT, Buffs)
└── com.dbd.mechanics   # Dados, SkillChecks y Lógica de Tablero
