@echo off
cd %~dp0

echo Iniciando Dead by Daylight RPG (Versión Web con Spring Boot)...
echo ================================================================
echo Asegúrate de tener MySQL ejecutándose y la base de datos dbd_rpg creada.
echo La aplicación estará disponible en http://localhost:8080/index.html
echo ================================================================

call mvn spring-boot:run

pause
