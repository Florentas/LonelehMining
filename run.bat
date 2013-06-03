@echo off
for /f %%i in ('dir /b "%CD%\RSBot*.jar" 2^>nul') do set RSBot="%%i"
if "%RSBot%"=="" (
echo [INFO] RSBot not found.
pause
exit
)
set Xms=-Xms512m
set Xmx=-Xmx3g
set PermSize=-XX:PermSize=128m
set MaxPermSize=-XX:MaxPermSize=256m
echo [BAT] Letting RSBot handle memory management
java -jar %RSBot%
pause