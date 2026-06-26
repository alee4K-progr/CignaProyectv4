@echo off
setlocal

set "ROOT=%~dp0"

echo Iniciando Eureka primero...
call :StartService "Discovery Server" "%ROOT%discovery-server\discovery-server"

call :StartService "API Gateway" "%ROOT%api-gateway\api-gateway"
call :StartService "Servicio Service" "%ROOT%servicio-service\servicio-service"
call :StartService "Tratamiento Service" "%ROOT%tratamiento-service"
call :StartService "Usuario Service" "%ROOT%usuario-service\usuario-service"
call :StartService "Agenda Service" "%ROOT%agenda-service\agenda-service"
call :StartService "Reserva Service" "%ROOT%reserva-service\reserva-service"

echo.
echo Todos los microservicios fueron lanzados.
endlocal
goto :eof

:StartService
start "%~1" cmd /k "cd /d ""%~2"" && call mvnw spring-boot:run"
timeout /t 15 /nobreak >nul
goto :eof