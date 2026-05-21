# 🩸 Dead by Daylight RPG - Web Edition

![Dead by Daylight RPG Banner](./readme_assets/banner.png)

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.4-brightgreen?style=for-the-badge&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![Next.js](https://img.shields.io/badge/Next.js-16-black?style=for-the-badge&logo=next.js)](https://nextjs.org/)
[![MariaDB](https://img.shields.io/badge/MariaDB-10.11-blue?style=for-the-badge&logo=mariadb)](https://mariadb.org/)
[![Selenium](https://img.shields.io/badge/Selenium-4.x-green?style=for-the-badge&logo=selenium)](https://www.selenium.dev/)
[![Seguridad](https://img.shields.io/badge/JWT-HttpOnly_Cookies-red?style=for-the-badge)](https://jwt.io/)

Una experiencia RPG táctica basada en el universo de **Dead by Daylight**. Enfréntate a asesinos legendarios o sobrevive como uno de los icónicos personajes en este sistema de combate por turnos desarrollado con tecnologías modernas de stack completo, diseñado bajo criterios estrictos de seguridad, velocidad y automatización.

> [!TIP]
> 📑 **Documentación Oficial en PDF:** Hemos compilado una documentación técnica exhaustiva de 6 páginas con modelo Entidad-Relación, paso a tablas, queries nativas y la guía paso a paso de AWS en: [documentacion_proyecto.pdf](./documentacion_proyecto.pdf).

---

## ✨ Características Principales

- ⚔️ **Combate por Turnos Reactivo**: Batallas en tiempo real con habilidades personalizadas por personaje, sincronizadas bidireccionalmente mediante **WebSockets / STOMP**.
- 👤 **Autenticación Blindada (JWT HttpOnly)**: Inicio de sesión protegido. Los tokens se almacenan en cookies del servidor no legibles por JavaScript, neutralizando ataques XSS.
- 🗃️ **Slots de Guardado Multi-usuario**: Cada usuario dispone de exactamente 3 slots independientes gestionados mediante transacciones manuales JDBC para evitar estados corruptos.
- 🏆 **Hall of Fame y Logros**: 20 logros interactivos desbloqueables y estadísticas de partidas globales en tiempo real.
- ⚡ **Rendimiento UI Excepcional (INP < 40ms)**: Optimización del retardo de interacción web mediante aceleración GPU con `will-change` y eliminación de fondos con scroll estático.
- 🧪 **Suite Selenium de Estrés**: Simulación E2E que automatiza a 5 usuarios reales registrándose, jugando en modo automático y guardando partida simultáneamente.

---

## 🛠️ Stack Tecnológico

### Backend
- **Core:** Java 17 + Spring Boot 3.2.4
- **Seguridad:** Spring Security + JWT (codificado en cookies HttpOnly con directiva `SameSite=Lax`)
- **Persistencia Híbrida:** JPA/Hibernate (entidades principales) + JDBC Nativo (`GestorPersistencia.java` para transacciones manuales)
- **Real-time:** Spring WebSockets + STOMP (transmisión en vivo de batallas automáticas)
- **Base de Datos:** MariaDB 10.11

### Frontend
- **Framework:** Next.js 16 (App Router) + TypeScript
- **Estado Global:** Zustand
- **Estilos y Transiciones:** Tailwind CSS 4 + Micro-animaciones aceleradas por hardware

---

## 🚀 Instalación y Uso Rápido

Este monorepo incluye scripts de automatización para arrancar el entorno completo en local rápidamente.

### Requisitos Previos
- **Java 17** o superior instalado en el PATH.
- **Node.js** (v18+) y gestor de paquetes **pnpm** / **npm**.
- **MariaDB** ejecutándose localmente en el puerto `3306`.

### Pasos para Arrancar

1. **Configurar la Base de Datos**:
   Ejecuta el script SQL en tu gestor de base de datos o ejecuta el asistente automático:
   ```bash
   cd backend
   call instalar_basedatos.bat
   ```

2. **Arrancar el Entorno**:
   Desde la raíz del monorepo, ejecuta el script unificado:
   ```bash
   ./start_all.bat
   ```
   *Esto iniciará el backend en el puerto `8080`, el frontend en el puerto `3000` y abrirá la aplicación en tu navegador.*

---

## 🧪 Pruebas de Calidad (Testing Automatizado)

Para comprobar el rendimiento del motor de combate y el sistema de concurrencia, disponemos de una robusta suite de pruebas integradas:

* **Pruebas Unitarias (JUnit 5):** Pruebas directas de la lógica interna de daño, estados alterados y lógica de turnos en `MotorTrialTest`.
* **Pruebas E2E (Selenium WebDriver):** El script `RpgSeleniumTest.java` levanta un navegador automatizado sin cabeza (headless), registra secuencialmente a **5 usuarios reales**, selecciona una ranura, inicializa y corre una partida en modo automático y guarda partida en la base de datos de manera atómica, comprobando que las sesiones e identidades HttpOnly están perfectamente aisladas.

Para ejecutar los tests, usa el comando:
```powershell
cd backend
./mvnw.cmd test -Dtest=RpgSeleniumTest
```

---

## 📂 Estructura del Proyecto

```text
rpg-poo/
├── backend/                  # Servidor API REST & WebSockets (Spring Boot 3.2)
│   ├── src/main/java/com/dbd/
│   │   ├── dao/              # GestorPersistencia (JDBC SQL) y repositorios JPA
│   │   ├── security/         # JwtFilter (HttpOnly handshake) y configs
│   │   ├── core/             # Motor de combate táctico (MotorTrial)
│   │   └── entidades/        # Supervivientes y Asesinos de Dead by Daylight
│   └── src/test/             # Selenium E2E & JUnit tests
├── frontend/                 # Interfaz de Usuario Reactiva (Next.js 16)
├── doc/                      # Javadocs y especificación técnica
├── documentacion_proyecto.pdf # Documentación maestra de 6 páginas en PDF
└── start_all.bat             # Lanzador automático local
```

---

## ☁️ Arquitectura de Despliegue en AWS

El sistema está diseñado para escalar de forma nativa hacia la nube de **Amazon Web Services (AWS)** con el siguiente esquema de seguridad:

```text
       [ Navegador del Usuario ]
                  │
                  ▼ (HTTPS: Puerto 443)
       [ Proxy Inverso: Nginx ] (EC2 Ubuntu Instance)
                  │
       ┌──────────┴──────────┐
       ▼ (Puerto 3000)       ▼ (Puerto 8080)
 [ Frontend Next.js ]   [ Backend Spring Boot ]
                             │
                             ▼ (JDBC: Puerto 3306)
                        [ AWS RDS MariaDB ] (Aislada en Red Privada)
```

### Componentes de AWS:
1. **Red Segura (VPC):** Grupos de seguridad restringidos. RDS no admite conexiones externas, únicamente atiende peticiones originadas en la IP de la instancia EC2.
2. **Base de Datos (Amazon RDS):** Instancia administrada MariaDB en la capa gratuita que almacena partidas, usuarios y logros.
3. **Servidor de Aplicaciones (Amazon EC2):** Instancia Ubuntu Server que ejecuta Next.js (administrado por `pm2` para evitar caídas) y Spring Boot (como servicio de segundo plano `systemd`).
4. **Nginx & Let's Encrypt:** Nginx enruta las llamadas de manera transparente y encripta todas las comunicaciones con certificados SSL gratuitos gestionados por Certbot.

---

## ⚖️ Aviso Legal

Este es un proyecto puramente académico/fan-made sin fines lucrativos y no está afiliado, asociado, autorizado ni conectado oficialmente con Behaviour Interactive Inc. o los propietarios originales de la franquicia Dead by Daylight.

Desarrollado con ☕ y 💀 por el equipo de **Proyecto Intermodular**.
