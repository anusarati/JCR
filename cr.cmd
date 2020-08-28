@ECHO off
SETLOCAL
IF "%1"=="" (
ECHO error: please specify the source file
EXIT /B 1
)
SET jsrc=%1
IF NOT "%jsrc:~-5%"==".java" SET jsrc=%1.java
IF NOT EXIST "%jsrc%" (
ECHO error: did not find file named %jsrc%
EXIT /B 1
)
javac %jsrc%
java %jsrc%
