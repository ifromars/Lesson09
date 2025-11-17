@echo off
setlocal EnableExtensions

REM Если нужно: добавить SDK в PATH
REM set ANDROID_SDK_ROOT=C:\Users\from-\AppData\Local\Android\Sdk
REM set PATH=%ANDROID_SDK_ROOT%\platform-tools;%ANDROID_SDK_ROOT%\emulator;%PATH%

adb kill-server
adb start-server

start "" emulator.exe -avd Pixel_9 -accel on -no-boot-anim

echo Waiting for device...
adb -e wait-for-device

echo Waiting for boot complete...
for /l %%i in (1,1,300) do (
  adb -e shell getprop sys.boot_completed 2>nul | findstr /rxc:"1" >nul
  if not errorlevel 1 goto :booted
  timeout /t 2 /nobreak >nul
)

echo ERROR: Timeout waiting for boot
exit /b 1

:booted
echo Emulator booted and ready.
exit /b 0