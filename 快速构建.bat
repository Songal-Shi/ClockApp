@echo off
chcp 65001 >nul
echo ========================================
echo Android时钟App - 一键构建
echo ========================================
echo.

powershell -ExecutionPolicy Bypass -File "%~dp0setup_and_build.ps1"

pause
