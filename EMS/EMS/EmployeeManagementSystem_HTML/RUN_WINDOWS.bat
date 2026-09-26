@echo off
cd /d "%~dp0"
if not exist "lib\mysql-connector-j-9.6.0.jar" (
  echo MySQL JDBC JAR not found in lib folder.
  pause
  exit /b 1
)
if not exist "out" mkdir out
javac -cp "lib\mysql-connector-j-9.6.0.jar" -d out src\main\java\*.java
if errorlevel 1 (
  echo.
  echo Compilation failed. Make sure JDK 17 is installed and JAVA_HOME/PATH is configured.
  pause
  exit /b 1
)
java -cp "out;lib\mysql-connector-j-9.6.0.jar" MainServer
pause
