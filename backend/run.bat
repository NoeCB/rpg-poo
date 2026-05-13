@echo off
setlocal
cd /d "%~dp0"

echo ========================================================
echo   Dead by Daylight RPG - Consola Clasica
echo ========================================================

echo.
echo [INFO] Compilando e Iniciando la Consola usando Maven Wrapper...
echo.

call mvnw.cmd spring-boot:run -Dspring-boot.run.arguments=--console

pause
