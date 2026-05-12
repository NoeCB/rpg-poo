@echo off
echo ========================================================
echo   Dead by Daylight RPG - Iniciando Entorno Monorepo
echo ========================================================
echo.

echo [INFO] Iniciando el Backend (Spring Boot)...
start "Backend DBD RPG" cmd /k "cd backend && call mvnw.cmd spring-boot:run"

echo [INFO] Iniciando el Frontend (Next.js)...
start "Frontend DBD RPG" cmd /k "cd frontend && pnpm run dev"

echo [INFO] Esperando 10 segundos a que arranquen los servidores...
timeout /t 10 /nobreak >nul

echo [INFO] Abriendo navegador web...
start http://localhost:3000

echo.
echo [INFO] ¡Todo en marcha! 
echo [INFO] Puedes cerrar esta ventana negra, pero manten abiertas las dos ventanas nuevas de Backend y Frontend.
pause
