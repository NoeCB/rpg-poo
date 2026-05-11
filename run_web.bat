@echo off
setlocal
cd /d "%~dp0"

echo ========================================================
echo   Dead by Daylight RPG - Lanzador Web y Backend
echo ========================================================

echo.
echo [INFO] Iniciando el Servidor Spring Boot usando Maven Wrapper...
echo [INFO] La primera vez tardara un poco en descargar dependencias de Internet.
echo [INFO] Cuando ponga Started RpgApplication abre tu navegador en: http://localhost:8080
echo.

call mvnw.cmd spring-boot:run

pause
