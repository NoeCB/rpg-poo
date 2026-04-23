@echo off
cd %~dp0

echo [1/2] Compilando el codigo de Dead by Daylight...
:: Buscamos todos los archivos .java en src y guardamos la lista en sources.txt
dir /s /B src\*.java > sources.txt

:: Compilamos usando esa lista y metemos el resultado en la carpeta bin
javac -encoding UTF-8 -cp "lib\mysql-connector-j-9.6.0.jar" -d bin @sources.txt

echo [2/2] Iniciando el juego...
echo =========================================
:: Ahora si, ejecutamos el juego desde la carpeta bin con el driver incluido
java -cp "bin;lib\mysql-connector-j-9.6.0.jar" com.dbd.main.Main

pause