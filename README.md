# 🩸 Dead by Daylight RPG - Web Edition

![Dead by Daylight RPG Banner](./readme_assets/banner.png)

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.4-brightgreen?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-16-black?style=for-the-badge&logo=next.js)](https://nextjs.org/)
[![MariaDB](https://img.shields.io/badge/MariaDB-10.11-blue?style=for-the-badge&logo=mariadb)](https://mariadb.org/)
[![pnpm](https://img.shields.io/badge/pnpm-9.0-yellow?style=for-the-badge&logo=pnpm)](https://pnpm.io/)

Una experiencia RPG táctica basada en el universo de **Dead by Daylight**. Enfréntate a asesinos legendarios o sobrevive como uno de los icónicos personajes en este sistema de combate por turnos desarrollado con tecnologías modernas de stack completo.

---

## ✨ Características Principales

- ⚔️ **Combate por Turnos**: Sistema de batalla táctico con habilidades únicas por personaje.
- 👤 **Gestión de Usuarios**: Autenticación segura mediante JWT y perfiles persistentes.
- 🏆 **Hall of Fame**: Clasificación global basada en estadísticas de partidas reales.
- 🔄 **Real-Time Updates**: Comunicación bidireccional mediante WebSockets para una experiencia fluida.
- 🗃️ **Persistencia Avanzada**: Base de datos MariaDB con migraciones automáticas vía Flyway.
- 🎨 **Interfaz Premium**: UI moderna construida con Next.js y Tailwind CSS 4.

---

## 🛠️ Stack Tecnológico

### Backend
- **Core:** Java 17 + Spring Boot 3.2.4
- **Seguridad:** Spring Security + Auth0 JWT
- **Datos:** Spring Data JPA + MariaDB
- **Migraciones:** Flyway
- **Real-time:** Spring WebSockets + STOMP

### Frontend
- **Framework:** Next.js 16 (App Router)
- **Estado:** Zustand
- **Estilos:** Tailwind CSS 4
- **Lenguaje:** TypeScript

---

## 🚀 Instalación y Uso

Este proyecto utiliza una estructura de **Monorepo**. Para facilitar el arranque, se ha incluido un script de automatización.

### Requisitos Previos
- **Java 17** o superior.
- **Node.js** (v18+) y **pnpm**.
- **MariaDB/MySQL** corriendo localmente.

### Pasos para iniciar

1. **Configurar la Base de Datos**:
   Ejecuta el script SQL incluido en `backend/script_bbdd.sql` o usa el asistente:
   ```bash
   cd backend
   call instalar_basedatos.bat
   ```

2. **Arrancar todo el entorno**:
   Desde la raíz del proyecto, simplemente ejecuta:
   ```bash
   ./start_all.bat
   ```

El script iniciará automáticamente:
- El servidor backend en `http://localhost:8080`
- El cliente frontend en `http://localhost:3000`
- Abrirá tu navegador predeterminado.

---

## 📂 Estructura del Proyecto

```text
rpg-poo/
├── backend/          # API REST & Lógica de Negocio (Spring Boot)
├── frontend/         # Interfaz de Usuario (Next.js)
├── bin/              # Binarios y ejecutables compilados
├── doc/              # Documentación técnica y diagramas
├── lib/              # Librerías externas
└── start_all.bat     # Script de inicio rápido
```

---

## 📸 Vista Previa

| Selección de Personaje | Combate en Tiempo Real |
|:---:|:---:|
| ![Placeholder](https://via.placeholder.com/400x225?text=Character+Selection) | ![Placeholder](https://via.placeholder.com/400x225?text=Combat+UI) |

---

## 🤝 Contribuciones

Si deseas contribuir:
1. Haz un **Fork** del proyecto.
2. Crea una rama para tu característica (`git checkout -b feature/nueva-mejora`).
3. Haz **Commit** de tus cambios (`git commit -m 'Añade nueva funcionalidad'`).
4. Haz **Push** a la rama (`git push origin feature/nueva-mejora`).
5. Abre un **Pull Request**.

---

## ⚖️ Aviso Legal

Este es un proyecto académico/fan-made y no está afiliado, asociado, autorizado, respaldado ni conectado oficialmente de ninguna manera con Behaviour Interactive Inc. o los propietarios originales de la franquicia Dead by Daylight.

---

## 📄 Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

Desarrollado con ☕ y 💀 por el equipo de **Proyecto Intermodular**.
