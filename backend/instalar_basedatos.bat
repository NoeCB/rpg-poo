@echo off
color 0b
echo ========================================================
echo INSTALADOR EXPRESS: BASE DE DATOS DEAD BY DAYLIGHT RPG
echo ========================================================
echo.
echo ATENCION: Antes de continuar, verifica que:
echo 1. Tienes el servidor XAMPP, WAMP o MySQL corriendo.
echo 2. El puerto 3306 de MySQL esta en verde/activo.
echo.
pause

echo.
echo Ejecutando el archivo script_bbdd.sql en tu MySQL local (Usuario: root, sin clave)...
mysql -u root < script_bbdd.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    color 0a
    echo ========================================================
    echo EXITO: La base de datos "dbd_rpg" se creo perfectamente.
    echo ========================================================
) else (
    echo.
    color 0c
    echo ERROR CRITICO: No se pudo conectar a MySQL para inyectar los datos.
    echo Posibles causas:
    echo - MySQL no esta encendido en tu panel de control de XAMPP.
    echo - 'mysql' no se reconoce como un comando interno (No esta en tu PATH).
    echo.
    echo SOLUCION MANUAL:
    echo Abre phpMyAdmin (http://localhost/phpmyadmin), presiona en la pestana "SQL"
    echo y copia/pega el contenido del archivo "script_bbdd.sql", luego dale a Ejecutar.
)
echo.
pause
