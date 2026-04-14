@echo off
title Presentacion RPG POO - React
echo =======================================================
echo    INICIANDO EL SERVIDOR DE LA PRESENTACION (VITE)
echo =======================================================
echo.
echo Por favor, NO CIERRES esta ventana negra mientras estes haciendo la presentacion.
echo Cargando modulo, abriendo el navegador en breve...
echo.

REM Cambia al subdirectorio donde esta el proyecto Vite
cd /d "%~dp0presentacion-web"

REM Ejecuta el servidor de desarrollo y abre la pestana automaticamente
call npm run dev -- --open
