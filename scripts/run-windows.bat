@echo off
setlocal
cd /d "%~dp0\.."

if not exist out mkdir out

echo Compiling SmartHospital...
javac -encoding UTF-8 -d out src\SmartHospital.java
if errorlevel 1 (
    echo Compilation failed. Check the error above.
    pause
    exit /b 1
)

echo.
echo Patient records will be read from and saved to:
echo %CD%\data\patients.csv
echo.
java -cp out SmartHospital
pause
endlocal
