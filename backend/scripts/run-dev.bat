@echo off

echo ================================
echo Iniciando Sistema de Vendas
echo ================================

REM Mude as variaveis de acordo com seu banco de dados.
set DB_URL=jdbc:postgresql://localhost:5432/pedidos_otica
set DB_USERNAME=postgres
set DB_PASSWORD=postgres
set SPRING_PROFILE_ACTIVE=prod

mvn spring-boot:run

pause