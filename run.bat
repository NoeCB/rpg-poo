@echo off
cd %~dp0

echo [1/2] Compilando el codigo de Dead by Daylight...
:: Buscamos todos los archivos .java en src y guardamos la lista en sources.txt
dir /s /B src\*.java > sources.txt

:: Compilamos usando esa lista y metemos el resultado en la carpeta bin
javac -d bin @sources.txt

echo [2/2] Iniciando el juego...
echo =========================================
:: Ahora si, ejecutamos el juego desde la carpeta bin
java -cp bin com.dbd.main.Main

pause