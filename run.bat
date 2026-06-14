@echo off
setlocal
cd /d "%~dp0"
echo Menjalankan MABAR.CU JavaFX + Spring Boot + H2...
echo Jika pernah error database, jalankan reset-db.bat dulu.
mvn clean javafx:run
pause
