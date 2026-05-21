@echo off
chcp 65001 > nul
title DBD RPG - Suite de Testing Automatizada
color 0A

echo ======================================================================
echo           INICIANDO ENTORNO DE PRUEBAS DBD-RPG (JUNIT + SELENIUM)
echo ======================================================================
echo.

rem Volver al directorio raíz
cd /d "%~dp0\.."

echo [1/5] Compilando Backend Spring Boot...
cd backend
call mvnw.cmd clean compile
if %ERRORLEVEL% neq 0 (
    color 0C
    echo [ERROR] Error al compilar el Backend. Abortando pruebas.
    pause
    exit /b %ERRORLEVEL%
)
cd ..

echo [2/5] Iniciando Backend en segundo plano (Puerto 8080)...
cd backend
start "DBD-RPG-BACKEND" /min cmd /c mvnw.cmd spring-boot:run
cd ..

echo [3/5] Iniciando Frontend Next.js en segundo plano (Puerto 3000)...
cd frontend
start "DBD-RPG-FRONTEND" /min cmd /c pnpm dev
if %ERRORLEVEL% neq 0 (
    echo Intento fallido con pnpm. Probando con npm...
    start "DBD-RPG-FRONTEND" /min cmd /c npm run dev
)
cd ..

echo Esperando 15 segundos para que los servidores se inicialicen completamente...
timeout /t 15 /nobreak

echo [4/5] Ejecutando Pruebas Unitarias, Integración y Selenium...
cd backend
call mvnw.cmd test -Dtest=*
set TEST_RESULT=%ERRORLEVEL%
cd ..

echo [5/5] Finalizando servidores en segundo plano de forma ordenada...
echo Liberando puerto 8080 (Spring Boot)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":8080" ^| findstr "LISTENING"') do (
    taskkill /f /pid %%a >nul 2>&1
)

echo Liberando puerto 3000 (Next.js)...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr ":3000" ^| findstr "LISTENING"') do (
    taskkill /f /pid %%a >nul 2>&1
)

echo.
echo ======================================================================
if %TEST_RESULT% equ 0 (
    color 0A
    echo           ¡PRUEBAS COMPLETADAS EXITOSAMENTE! RESULTADO: OK
) else (
    color 0C
    echo           ¡ALGO FALLÓ EN LA EJECUCIÓN! RESULTADO: ERROR (%TEST_RESULT%)
)
echo ======================================================================
echo.
pause
