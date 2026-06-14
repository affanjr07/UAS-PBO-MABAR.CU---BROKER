@echo off
cd /d %~dp0
rmdir /s /q data
mvn clean javafx:run
pause
