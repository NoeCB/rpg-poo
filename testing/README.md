# Entorno de Pruebas Automatizadas (JUnit 5 & Selenium) - Dead by Daylight RPG

Este módulo de pruebas contiene la suite completa para la validación automática, funcional y de seguridad de la arquitectura del proyecto RPG.

## Componentes de la Suite de Pruebas

1.  **Pruebas Unitarias (JUnit 5):**
    *   Valida los algoritmos de combate, cálculo de modificadores de daño y turnos en `MotorTrialTest`.
2.  **Pruebas de Integración (JUnit 5):**
    *   Valida el ciclo de vida del juego, asignación de personajes y escalado de dificultades extremas en `TrialServiceTest`.
3.  **Pruebas de Interfaz de Usuario End-to-End (Selenium Web Driver):**
    *   Simula **5 jugadores reales** de forma concurrente/secuencial.
    *   Cada jugador realiza de forma automática:
        1.  Registro de cuenta nueva con credenciales únicas generadas dinámicamente.
        2.  Autenticación en la pantalla de Login y almacenamiento seguro de la sesión (cookie HttpOnly).
        3.  Creación de una nueva partida en la Ranura 1.
        4.  Selección aleatoria de supervivientes y asesinos en La Hoguera.
        5.  Inicio de la prueba en modo de combate automático.
        6.  Guardado persistente del estado de la partida en MariaDB.
        7.  Cierre seguro de la sesión para volver a la pantalla de login.

---

## Requisitos de Ejecución

Para ejecutar la suite completa de Selenium, asegúrate de cumplir con los siguientes prerrequisitos:

1.  **Base de Datos Activa:**
    *   MariaDB o MySQL ejecutándose en el puerto `3306`.
    *   Nombre de base de datos: `dbd_rpg`.
    *   Usuario: `root`, Contraseña: vacía `""` (o según tu configuración local).
2.  **Navegador de Internet:**
    *   Microsoft Edge (recomendado) o Google Chrome instalados en el sistema operativo.
    *   El motor Selenium Manager se encargará de resolver el driver correspondiente automáticamente de manera transparente.
3.  **Servidores Encendidos (Requerido para Selenium):**
    *   **Backend:** Corriendo en `http://localhost:8080` (`cd rpg-poo/backend && mvnw spring-boot:run` o similar).
    *   **Frontend:** Corriendo en `http://localhost:3000` (`cd rpg-poo/frontend && pnpm dev` or `npm run dev`).
    *   *Nota:* Si ejecutas los tests con `mvn clean test` sin los servidores encendidos, las pruebas unitarias y de integración JUnit pasarán correctamente, y la prueba de Selenium se **omitirá elegantemente** de forma segura para no provocar fallos de build innecesarios.

---

## Instrucciones de Ejecución Automática

Hemos preparado un script automatizado `run_all_tests.bat` en esta carpeta. Para usarlo:

1.  Asegúrate de que tu base de datos esté encendida.
2.  Haz doble clic o ejecuta en terminal `run_all_tests.bat`.
3.  El script iniciará el backend y frontend en segundo plano, ejecutará todas las pruebas unitarias, de integración y la suite completa de Selenium de 5 usuarios, y finalmente detendrá los servidores de forma ordenada para dejar tu sistema limpio.
