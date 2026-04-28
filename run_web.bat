@echo off
setlocal
cd /d "%~dp0"

echo ========================================================
echo   Dead by Daylight RPG - Lanzador Web y Backend
echo ========================================================

set "MAVEN_DIR=apache-maven-3.9.6"
set "MAVEN_ZIP=maven.zip"

if not exist "%MAVEN_DIR%" (
    echo [INFO] Maven no encontrado. Descargando Maven Portable solo ocupara unos MB...
    powershell -Command "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.6/apache-maven-3.9.6-bin.zip' -OutFile '%MAVEN_ZIP%'"
    
    echo [INFO] Extrayendo Maven...
    powershell -Command "Expand-Archive -Path '%MAVEN_ZIP%' -DestinationPath '.'"
    
    echo [INFO] Limpiando...
    del "%MAVEN_ZIP%"
    echo [INFO] Maven instalado correctamente.
)

set "PATH=%CD%\%MAVEN_DIR%\bin;%PATH%"

echo.
echo [INFO] Iniciando el Servidor Spring Boot...
echo [INFO] La primera vez tardara un poco en descargar dependencias de Internet.
echo [INFO] Cuando ponga Started RpgApplication abre tu navegador en: http://localhost:8080
echo.

call mvn spring-boot:run

pause
